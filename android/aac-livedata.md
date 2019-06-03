# 【AAC 系列三】深入理解架构组件：LiveData

![mj-tangonan-1540675-unsplash.jpg](https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1557131045935-3b37e4fb-658b-4d27-956b-eff115f51352.jpeg#align=left&display=inline&height=853&name=mj-tangonan-1540675-unsplash.jpg&originHeight=853&originWidth=1280&size=397107&status=done&width=1280)

<a name="9cf27a82"></a>
### 0. 前言

> 本文是深入理解「Android Architecture Components」系列文章第三篇
> 源码基于 android.arch.lifecycle:livedata-core:1.1.1



在之前我们深入研究了 Lifecycle 的实现原理，并在文末提到了LiveData 以及 ViewModel，这次我们来讲讲 LiveData。



LiveData 是 Android Architecture Components 中的一员，先看下官方是如何介绍的：



> [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html) is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state. [见 9.1]
> 
> This class is designed to hold individual data fields of [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html), but can also be used for sharing data between different modules in your application in a decoupled fashion. [见 9.2]



简单讲 `LiveData 是一个能够感知生命周期、可观察的数据持有类` ，它被设计成 ViewModel 的一个成员变量；可以以一个 `更解耦` 的方式来**共享数据**。

实际使用下来发现 LiveData 有几个**特性**：

1. LiveData 的实现基于**观察者**模式；
1. LiveData 跟 LifecycleOwner 绑定，能**感知生命周期变化**，并且只会在 LifecycleOwner 处于 `Active` 状态（STARTED/RESUMED）下通知数据改变；
1. LiveData 会自动在 DESTROYED 的状态下移除 Observer ，取消订阅，所以**不用担心内存泄露**；

那么 LiveData 上述特性的**原理**是怎么样的呢？<br />使用 LiveData 又需要注意些什么呢？

本文将围绕此展开。

<a name="d7f29183"></a>
### 1. LiveData 的基本使用

虽然 LiveData 通常跟 ViewModel 配合使用，不过也可以单独使用，为了简单起见，这里不配合 ViewModel。

以下是一个简单的例子：

```java
MutableLiveData<String> liveString = new MutableLiveData<>();
liveString.observe(this, new Observer<String>() {
  @Override
  public void onChanged(@Nullable final String s) {
    Log.d(TAG, "onChanged() called with: s = [" + s + "]");
  }
});

liveString.postValue("程序亦非猿");
```

运行后可以看到日志输出：`onChanged() called with: s = [程序亦非猿]` 。

释义：<br />定义一个 MutableLiveData （LiveData 的一个常用子类），通过 observe 方法可以订阅修改数据的通知，通过 `postValue()`  或者 `setValue()`  方法可以更新数据，已经订阅的 Observer 能够得到数据更改的通知，也即回调 `onChanged()` 方法。

这样就算是用上 LiveData 了。

接下来，上干货！

<a name="5f1eee0e"></a>
### 2. LiveData 的原理分析

在分析原理前，再明确一下我们的疑问：

1. LiveData 是如何跟 LifecycleOwner 进行绑定，做到**感知生命周期**的？
1. LiveData **只在 LifecycleOwner active 状态发送通知**，是怎么处理的？
1. LiveData 会**自动在 DESTROY 的状态下取消订阅**，是怎么处理的？
1. 通过 setValue()/postValue() **更新数据的处理流程**是如何？
1. **生命周期变化后**数据处理流程是怎么样的？

同时提前看下我整理的 LiveData UML 图，对 LiveData 有个整体的了解，后续的涉及到的类都在这里了，有助于理解。

![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1554969110238-9a1135e2-8298-4e08-85f9-c6e7153772c8.png#align=left&display=inline&height=359&name=image.png&originHeight=718&originWidth=817&size=285251&status=done&width=409)<br />(图1.LiveData 类图)

OK， here we go!

<a name="fd594c2d"></a>
#### 2.1 LiveData.observe()

LiveData 的使用流程从 `observe()` 开始，咱们尝试从 `observe()` 方法 开始分析：

```java
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        //如果是 DESTROYED 的状态则忽略
        if (owner.getLifecycle().getCurrentState() == DESTROYED) {
            // ignore
            return;
        }
        //把 Observer 用 LifecycleBoundObserver 包装起来
        LifecycleBoundObserver wrapper = new LifecycleBoundObserver(owner, observer);
        //缓存起来
        ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
        //如果已经 observe 过 并且两次的 owner 不同则报错
        if (existing != null && !existing.isAttachedTo(owner)) {
            throw new IllegalArgumentException("Cannot add the same observer"
                    + " with different lifecycles");
        }
        if (existing != null) {
            return;
        }
        //绑定 owner
        owner.getLifecycle().addObserver(wrapper);
    }
```

可以看到 observe 方法里把我们传递的 observer 用 `LifecycleBoundObserver`  包装了起来，并且存入了 `mObservers`  ，并且跟 owner 进行了关联。

并且做了两个特殊处理：

1. **忽视处于 DESTROYED 的 owner 的注册行为**；
1. **将一个 Observer 同时绑定两个 owner 的行为视为非法操作，也即一个 Observer 只能绑定一个 owner，而 owner 可以有多个 Observe**r；

这里出现了几个新的类 **LifecycleBoundObserver** 、**ObserverWrapper** 来看看。

<a name="8fee89a9"></a>
#### 2.2 LifecycleBoundObserver

```java
    class LifecycleBoundObserver extends ObserverWrapper implements GenericLifecycleObserver {
        @NonNull final LifecycleOwner mOwner;

        LifecycleBoundObserver(@NonNull LifecycleOwner owner, Observer<T> observer) {
            super(observer);
            mOwner = owner;
        }

        @Override
        boolean shouldBeActive() {
            // 判断 owner 当前的状态是否是至少 STARTED
            return mOwner.getLifecycle().getCurrentState().isAtLeast(STARTED);
        }

        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            //生命周期改变，如果是 DESTROYED 就自动解除
            if (mOwner.getLifecycle().getCurrentState() == DESTROYED) {
                removeObserver(mObserver);
                return;
            }
            //ObserverWrapper.activeStateChanged
            activeStateChanged(shouldBeActive());
        }

        @Override
        boolean isAttachedTo(LifecycleOwner owner) {
            return mOwner == owner;
        }

        @Override
        void detachObserver() {
            mOwner.getLifecycle().removeObserver(this);
        }
    }
```

**ObserverWrapper **:  

```java
    private abstract class ObserverWrapper {
        final Observer<T> mObserver;
        boolean mActive;
        int mLastVersion = START_VERSION;

        ObserverWrapper(Observer<T> observer) {
            mObserver = observer;
        }
				//是否是 active 状态
        abstract boolean shouldBeActive();

        boolean isAttachedTo(LifecycleOwner owner) {
            return false;
        }

        void detachObserver() {
        }

        void activeStateChanged(boolean newActive) {
            if (newActive == mActive) {
                return;
            }
            // immediately set active state, so we'd never dispatch anything to inactive
            // owner
            mActive = newActive;
            boolean wasInactive = LiveData.this.mActiveCount == 0;
            LiveData.this.mActiveCount += mActive ? 1 : -1;
            if (wasInactive && mActive) {
                onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !mActive) {
                onInactive();
            }
            //如果 active 状态下，则发送数据更新通知
            if (mActive) {
                dispatchingValue(this);
            }
        }
    }
```

仔细看下这两个类其实就能解答疑问了。

**LifecycleBoundObserver 是 抽象类 ObserverWrapper 的子类，重写了 shouldBeActive() 方法，在 owner 处于至少是 STARTED 的状态下认为是 active 状态；并且它也实现了 GenericLifecycleObserver 接口，可以监听 lifecycle 回调，并且在 onStateChanged() 方法里处理了生命周期改变的事件，当接收到 DESTROYED 的事件会自动解除跟 owner 的绑定，并且将下个流程交给了 activeStateChanged() 。**

到这里 【2.1】、【2.3】的问题已经有了答案：

**【2.1】答**：LifeData 在 observe 方法中用 LifecycleBoundObserver 包装了 observer ，并且通过它绑定了owner。<br />**【2.3】答**：LifecycleBoundObserver 在 onStateChanged() 方法里处理了生命周期改变的事件，当接收到 DESTROYED 的事件会自动解除跟 owner 的绑定。

**这里需要注意的是，当我们调用 observe() 注册后，由于绑定了 owner，所以在 active 的情况下，LiveData 如果有数据，则 Observer 会立马接受到该数据修改的通知。**

此时的流程是:

observe--><br />  onStateChanged--><br />    activeStateChanged--><br />     dispatchingValue--><br />       considerNotify--><br />          onChanged

可以称之为**生命周期改变触发的流程，另外还有一种流程是 postValue&setValue 触发的流程，共两种。**

<a name="73851cc0"></a>
#### 2.3 activeStateChanged(boolean) 

在 activeStateChanged() 方法里，处理了 onActive() 跟 onInactive() 回调的相关逻辑处理，并且调用了dispatchingValue(this) 。（MediatorLiveData 用到了 onActive() 跟 onInactive() 有兴趣自行了解，这里不展开）

接下去探索 `dispatchingValue` 。<br />

<a name="bc1af81b"></a>
#### 2.4 dispatchingValue(ObserverWrapper) 分析

```java
    private void dispatchingValue(@Nullable ObserverWrapper initiator) {
        //如果正在分发则直接返回
        if (mDispatchingValue) {
            //标记分发失效
            mDispatchInvalidated = true;
            return;
        }
        //标记分发开始
        mDispatchingValue = true;
        do {
            mDispatchInvalidated = false;
            //生命周期改变调用的方法 initiator 不为 null
            if (initiator != null) {
                considerNotify(initiator);
                initiator = null;
            } else {
                //postValue/setValue 方法调用 传递的 initiator 为 null
                for (Iterator<Map.Entry<Observer<T>, ObserverWrapper>> iterator =
                        mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
                    considerNotify(iterator.next().getValue());
                    if (mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (mDispatchInvalidated);
        //标记分发结束
        mDispatchingValue = false;
    }
```

`considerNotify(ObserverWrapper)`  方法:

```java
    private void considerNotify(ObserverWrapper observer) {
        //检查状态 确保不会分发给 inactive 的 observer
        if (!observer.mActive) {
            return;
        }
        // Check latest state b4 dispatch. Maybe it changed state but we didn't get the event yet.
        //
        // we still first check observer.active to keep it as the entrance for events. So even if
        // the observer moved to an active state, if we've not received that event, we better not
        // notify for a more predictable notification order.
        if (!observer.shouldBeActive()) {
            observer.activeStateChanged(false);
            return;
        }
        //setValue 会增加 version ,初始 version 为-1
        if (observer.mLastVersion >= mVersion) {
            return;
        }
        observer.mLastVersion = mVersion;
        //noinspection unchecked
        observer.mObserver.onChanged((T) mData);
    }
```


可以看到 dispatchingValue 正是分发事件逻辑的处理方法，而 considerNotify 方法则确保了**只将最新的数据分发给 active 状态下的 Observer** 。

另外也可以看到 LiveData 引入了**版本管理**来管理数据 （mData）以**确保发送的数据总是最新的**。（具体不多讲）

dispatchingValue 这里分两种情况：

1. **ObserverWrapper 不为 null**
1. **ObserverWrapper 为 null**

需要着重讲一下。

<a name="7e259814"></a>
##### 2.4.1 ObserverWrapper 不为 null 的情况

上面提到过，LifecycleBoundObserver.onStateChanged 方法里调用了 activeStateChanged ，而该方法调用dispatchingValue(this);传入了 this ，也就是 LifecycleBoundObserver ，这时候不为 null 。

**也就是说生命周期改变触发的流程就是这种情况，这种情况下，只会通知跟该 Owner 绑定的 Observer。**

<a name="7a435db2"></a>
##### 2.4.2 ObserverWrapper 为 null 的情况

上面我也提前说了，除了生命周期改变触发的流程外，还有 postValue&setValue 流程，来看下这俩方法。

```java
private final Runnable mPostValueRunnable = new Runnable() {
    @Override
    public void run() {
        Object newValue;
        synchronized (mDataLock) {
            newValue = mPendingData;
            mPendingData = NOT_SET;
        }
        //noinspection unchecked
        //调用 setValue
        setValue((T) newValue);
    }
};

protected void postValue(T value) {
    boolean postTask;
    synchronized (mDataLock) {
        postTask = mPendingData == NOT_SET;
        mPendingData = value;
    }
    if (!postTask) {
        return;
    }
    ArchTaskExecutor.getInstance().postToMainThread(mPostValueRunnable);
}

@MainThread
protected void setValue(T value) {
    //必须在主线程调用 否则会 crash
    assertMainThread("setValue");
    mVersion++;//增加版本号
    mData = value;
    //传入了 null
    dispatchingValue(null);
}
```

LiveData 的 postValue 方法其实就是把操作 post 到主线程，**最后调用的还是 setValue 方法**，注意 setValue 必须是在主线程调用。

并且可以看到** setValue 方法调用了 dispatchingValue 方法，并传入了 null ，这个时候的流程则会通知 active 的mObservers**。

到这里之前的剩下的所有疑问也都可以解答了。

LiveData 的两个流程都会走到 **dispatchingValue 处理分发通知逻辑，**并且在分发通知前会判断 owner 的状态，再加上 LiveData 本身内部的版本管理，确保了**只会发送最新的数据给 active 状态下的 Observer**。

**注意：**LiveData 对同时多次修改数据做了处理，如果同时多次修改，只会修改为最新的数据。

<a name="a43d4d52"></a>
### 3. 图解 LiveData

<a name="b932eca5"></a>
#### 3.1 LiveData 类图
再看一遍类图，回顾一下：<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1554969110238-9a1135e2-8298-4e08-85f9-c6e7153772c8.png#align=left&display=inline&height=359&name=image.png&originHeight=718&originWidth=817&size=285251&status=done&width=409)<br />(图2.LiveData 类图)
<a name="c979bac4"></a>
#### 3.2 LiveData 流程图

Lifecycle 改变触发流程：

![livedata-lifecycle-changes.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1555053650504-ffd5ea7a-4f49-4be4-89ad-760f065fe1cc.png#align=left&display=inline&height=317&name=livedata-lifecycle-changes.png&originHeight=486&originWidth=1143&size=62726&status=done&width=746)<br />(图3.Lifecycle 改变触发流程图)

Lifecycle postValue/setValue 触发流程：

![livedata-postvalue.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1555053700049-ad572729-ed17-4fb0-990e-382e543e5485.png#align=left&display=inline&height=317&name=livedata-postvalue.png&originHeight=486&originWidth=1143&size=56008&status=done&width=746)<br />(图4.setValue 改变触发流程图)


<a name="6becaec8"></a>
### 4. LiveData tips and recipes

<br />LiveData 还有很多其他相关知识，这里列举一些，更多实践可以看一下【7.6】。

<a name="2c5a5752"></a>
#### 4.1 Sticky Event

LiveData 被订阅时，如果之前已经更改过数据，并且当前 owner 为 active 的状态，activeStateChanged() 会被调用，也即会立马通知到 Observer ，这样其实就类似 EventBus 的 sticky event 的功能，需要注意的是，很多时候我们并不需要该功能。具体可以看一下【7.6】的处理。

<a name="873f2dd4"></a>
#### 4.2 AlwaysActiveObserver 

默认情况下，LiveData 会跟 LicycleOwner 绑定，只在 active 状态下更新，如若想要**不管在什么状态下都能接收到数据的更改通知**的话，怎么办？这时候需要使用 **AlwaysActiveObserver** ，改调用 observe 方法为调用 LiveData.observeForever(Observer) 方法即可。

<a name="4cea7239"></a>
#### 4.3 MediatorLiveData

LiveData 还有一个子类是 MediatorLiveData，它允许我们合并多个 LiveData，任何一个 LiveData 有更新就会发送通知。比如我们的数据来源有两个，一个数据库一个网络，这时候我们会有两个 DataSource，也就是两个 LiveData，这个时候我们可以使用 MediatorLiveData 来 merge 这两个 LiveData。

<a name="769af84f"></a>
#### 4.4 Transformations

Transformations 允许我们把一个 LiveData 进行处理，变化成另外一个 LiveData，目前支持 map 跟 switchMap 两个方法，跟 RxJava 的操作类似。

比如，用 map 把一个 String 类型的 LiveData 转换成 Integer 类型：

```java
Transformations.map(liveString, new Function<String, Integer>() {
  @Override
  public Integer apply(final String input) {
    return Integer.valueOf(input);
  }
}).observe(this, new Observer<Integer>() {
  @Override
  public void onChanged(@Nullable final Integer integer) {

  }
});
```

<a name="661cfeb8"></a>
#### 4.4 LiveDataBus

EventBus 基于观察者模式，LiveData 也是，所以 LiveData 可以被用来做成 LiveDataBus，有兴趣可以搜索。

<a name="719fecf9"></a>
### 5. 知识点梳理和汇总

1. LiveData 的实现基于观察者模式（reactive patterns）；
1. LiveData 跟 LifecycleOwner 绑定，能感知生命周期变化，并且只会在 LifecycleOwner 处于 Active 状态（STARTED/RESUMED）下通知数据改变；如果数据改变发生在非 active 状态，数据会变化，但是不发送通知，等 owner 回到 active 的状态下，再发送通知；
1. 如果想要一直收到通知，则需要用 `observeForever()` 方法；
1. LiveData 会自动在 DESTROYED 的状态下移除 Observer ，取消订阅，所以不用担心内存泄露；
1. 在 LifecycleOwner 处于 DESTROYED 的状态下，不能订阅；
1. postValue 方法其实最后调用了 setValue 只不过把操作放到主线程，适合在异步线程里调用，setValue 必须在主线程里调用；
1. 如果同时多次调用 postValue 或 setValue 修改数据，只会修改成最新的那个数据，也即只会收到一次通知（set post混合调用则不一定）；
1. 如果 LiveData 有数据，并且 owner 在 active 状态下，那么在订阅的时候，**会立马收到一次通知**；
1. 一个 Observer 实例，只能绑定一个 LifecycleOwner，而一个 owner 可以绑定多个 Observer 实例；
1. LiveData 利用版本管理、绑定 Lifecycle 确保了**只会发送最新的数据给 active 状态下的 Observer**；

<a name="aa5fbf5b"></a>

### 6. 总结

**LiveData 基于观察者模式，并且可以感知生命周期，这使得我们使用 LiveData 既可以享受观察者模式带来的隔离数据与 UI 等强大的解耦能力，还可以享受感知生命周期带来的巨大便利。并且还无需担心内存泄露这个令人头疼的问题。**

我们可以使用 LiveData 非常轻松地做到一些非常高效的操作，如仅在 active 的状态下刷新 UI，可以避免不必要的数据刷新。

显而易见 LiveData 本身的优秀特性有着巨大的价值，利用好绝对是架构设计中的一大利器，另外 LiveData 配合 ViewModel 可以发挥更大的价值，机智的你一定已经知道下一篇文章讲什么了。

<a name="b5b4ac7f"></a>

### 7. 参考与推荐

1. LiveData Overview : [https://developer.android.com/topic/libraries/architecture/livedata](https://developer.android.com/topic/libraries/architecture/livedata)
1. LiveData doc : [https://developer.android.com/reference/android/arch/lifecycle/LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData)
1. [https://developer.android.com/reference/android/arch/lifecycle/LiveData.html](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
1. [https://developer.android.com/reference/android/arch/lifecycle/Transformations.html](https://developer.android.com/reference/android/arch/lifecycle/Transformations.html)
1. [https://github.com/googlesamples/android-architecture-components](https://github.com/googlesamples/android-architecture-components)
1. [https://github.com/googlesamples/android-sunflower](https://github.com/googlesamples/android-sunflower)

