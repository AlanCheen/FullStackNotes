# 【AAC 系列二】深入理解架构组件的基石：Lifecycle

[TOC]

### 0. 前言

> 本文是深入理解「Android Architecture Components」系列文章第二篇
> 源码基于 1.1.1 版本



在前文，我就提到 Android Architecture Components （后简称为 AAC），是一个帮助开发者设计 `健壮` 、 `可测试` 且 `可维护` 的一系列库的集合。

`Lifecycle`  就是 AAC 中的一员，**它能够帮助我们方便的管理 Activity 以及 Fragment 的生命周期**。

本文带大家深入了解 Lifecycle 。

**注意：本文基于 Lifecycle 1.1.1 版本，Android API 26 ，依赖如下图。**<br />**<br />**![image.png](http://ww2.sinaimg.cn/large/006tNc79ly1g5cd7a3ocxj30py0auagf.jpg)**<br />**<br />**<br />**并假设读者对 Lifecycle 有基本的了解，我绘制了一个基本的类图，如果对于下面类图所涉及到的类都还算了解则可以继续阅读下去，如果完全不知道，建议阅读一些教程先。**<br />**<br />**![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1552990370952-664b74ca-86f5-47de-9706-ab40180b7e60.png)**

<a name="e2000022"></a>
### 1. Lifecycle 使用基础

在 AppCompatActivity 里我们可以通过 getLifecycle() 方法拿到 Lifecycle ,并添加 Observer 来实现对 Activity 生命周期的监听。

一个简单的使用例子如下：

```java
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testLifecycle();
    }

    private void testLifecycle() {
        getLifecycle().addObserver(new LifecycleObserver() {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            void onResume(){
                Log.d(TAG, "LifecycleObserver onResume() called");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}
```

启动 MainActivity 就可以看到如下日志：

```java
D/MainActivity: onResume: 
D/MainActivity: LifecycleObserver onResume() called
```


日志说明我们通过上述代码确实实现了监听生命周期的功能。

**那么问题来了，这是怎么做到的？**

我把这个问题拆分成了两块：

1. **生命周期的感知问题**： `是什么感知了Activity的生命周期` ？
1. **注解方法的调用问题**： `是什么调用了我们使用注解修饰的方法` ？

<a name="7f514a69"></a>

### 2. 感知生命周期的原理

<a name="610796fd"></a>
#### 2.1 初现端倪 ReportFragment

我通过调试堆栈发现了一个叫做 `ReportFragment`  的类，非常可疑，遂跟踪之。

**注意：Debug 查看堆栈是阅读源码手段中最常用最简单最好用最亲民的方法，没有之一，每个人都应该熟练掌握。**

来看看这个类都写了什么：

```java
public class ReportFragment extends Fragment {

    private static final String REPORT_FRAGMENT_TAG = "android.arch.lifecycle"
            + ".LifecycleDispatcher.report_fragment_tag";
		//注入 Fragment 的方法
    public static void injectIfNeededIn(Activity activity) {
        // ProcessLifecycleOwner should always correctly work and some activities may not extend
        // FragmentActivity from support lib, so we use framework fragments for activities
        android.app.FragmentManager manager = activity.getFragmentManager();
        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
            manager.beginTransaction().add(new ReportFragment(), REPORT_FRAGMENT_TAG).commit();
            // Hopefully, we are the first to make a transaction.
            manager.executePendingTransactions();
        }
    }
    //...
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dispatchCreate(mProcessListener);
        dispatch(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatchStart(mProcessListener);
        dispatch(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatchResume(mProcessListener);
        dispatch(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        dispatch(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatch(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatch(Lifecycle.Event.ON_DESTROY);
        // just want to be sure that we won't leak reference to an activity
        mProcessListener = null;
    }
		//分发生命周期事件给 LifecycleRegistryOwner 的 Lifecycle 或者 LifecycleRegistry
    private void dispatch(Lifecycle.Event event) {
        Activity activity = getActivity();
        if (activity instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event);
            return;
        }

        if (activity instanceof LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
            }
        }
    }
   //...
}
```


一看代码我们就知道了，它重写了生命周期回调的方法，确实是这个 ReportFragment 在发挥作用，**Lifecycle 利用了 Fragment 来实现监听生命周期，并在生命周期回调里调用了内部 `dispatch`  的方法来分发生命周期事件**。(怎么分发后面讲)

<a name="4bc29167"></a>

#### 2.2 幕后“黑手” SupportActivity

从方法来看注入 Fragment 的方法应该是调用 `injectIfNeededIn(Activity)`  的地方了。

在通过搜索 发现 `SupportActivity`  调用了该方法。（API 28 的版本是 ComponentActivity ，代码实现没什么差别）

```java
public class SupportActivity extends Activity implements LifecycleOwner, Component {

    //拥有一个 LifecycleRegistry
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      	//在 onCreate 里注入了 ReportFragment
        ReportFragment.injectIfNeededIn(this);
    }

    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        this.mLifecycleRegistry.markState(State.CREATED);
        super.onSaveInstanceState(outState);
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
```


可以看到 SupportActivity 内部包含了一个 `LifecycleRegistry` ，并实现了 `LifecycleOwner`  ， 并且在 onCreate 方法里 调用了 `ReportFragment.injectIfNeededIn(this);` **注入**了 `ReportFragment`  。

`LifecycleRegistry`  是 Lifecycle 的实现，并负责管理 `Observer` ，在上面【2】章节的 `dispatch`  方法中已经看到了该类的出现，它的 `handleLifecycEvent`  接受了生命周期的回调。

<a name="dceb7b2f"></a>

#### 2.3 Lifecycle 的生命周期事件与状态的定义

这小节补充一下 `Lifecycle`  的回调与 Activity 、Fragment 的生命周期对标相关知识，后面分析会出现。

Lifecycle  中定义了 `Event` : 表示生命周期事件， `State` : 表示当前状态。

<a name="dc278a7c"></a>

##### 2.3.1 Lifecycle.Event 

`Lifecycle`  定义的生命周期事件，与 Activity 生命周期类似。

```java
    public enum Event {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_ANY
    }
```

<a name="de88adb7"></a>
##### 2.3.2 Lifecycle.State 

`State`  表示当前组件的生命周期状态。

```java
    /**
 		* Lifecycle states. You can consider the states as the nodes in a graph and
 		* {@link Event}s as the edges between these nodes.
 		*/
		public enum State {
        DESTROYED,
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED;
        public boolean isAtLeast(@NonNull State state) {
            return compareTo(state) >= 0;
        }
    }
```


<a name="48978dd0"></a>
##### 2.3.3 Event 与 State 的关系：
![image.png](http://ww3.sinaimg.cn/large/006tNc79ly1g5cd6gu588j30jg0alwg9.jpg)

(图1.图来源见【8.2】)

<a name="cc12ac66"></a>
#### 2.4 小结

通过研究我们发现，**SupportActivity 在 onCreate 方法里注入了 ReportFragment ，通过 Fragment 的机制来实现生命周期的监听**。

**实际上利用 Fragment 监听 Activity 生命周期的功能在开源社区由来已久， Lifecycle 并非原创，Lifecycle 的出现算是把这个实现官方化了。**

**相比于第三方的实现，嵌入到 Android 源码中的实现对开发者来说是非常有好处的，即屏蔽了细节，又降低了使用难度。**<br />**
<a name="731a721b"></a>
### 3. 注解方法被调用的原理

`OnLifecycleEvent`  注解：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnLifecycleEvent {
    Lifecycle.Event value();
}
```

看到有 RetentionPolicy.RUNTIME 修饰，我就猜测它是靠**反射**来实现了，不过还是看下具体实现验证下吧。

之前在了解完生命周期监听的原理的同时，我们也看到了生命周期事件的接收者 **LifecycleRegistry** ，是它的 `handleLifecycleEvent()`   接收了事件，我们继续追踪。

```java
    /**
     * Sets the current state and notifies the observers.
     * Note that if the {@code currentState} is the same state as the last call to this method,
     * calling this method has no effect.
     */
    public void handleLifecycleEvent(Lifecycle.Event event) {
        mState = getStateAfter(event);
        if (mHandlingEvent || mAddingObserverCounter != 0) {
            mNewEventOccurred = true;
            // we will figure out what to do on upper level.
            return;
        }
        mHandlingEvent = true;
        sync();
        mHandlingEvent = false;
    }
```

其实从方法注释就能看出来了，就是它处理了状态并通知了 observer 。

看下 `getStateAfter()`  方法：

```java
   static State getStateAfter(Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return CREATED;
            case ON_START:
            case ON_PAUSE:
                return STARTED;
            case ON_RESUME:
                return RESUMED;
            case ON_DESTROY:
                return DESTROYED;
            case ON_ANY:
                break;
        }
        throw new IllegalArgumentException("Unexpected event value " + event);
    }
```

`getStateAfter()`  这个方法根据当前 Event 获取对应的 State  ，细看其实就是 【2.3.3】中那个图的代码实现。

接下去看 `sync()`  方法：

```java
    private void sync() {
        while (!isSynced()) {
            mNewEventOccurred = false;
            // no need to check eldest for nullability, because isSynced does it for us.
            if (mState.compareTo(mObserverMap.eldest().getValue().mState) < 0) {
                backwardPass();
            }
            Entry<LifecycleObserver, ObserverWithState> newest = mObserverMap.newest();
            if (!mNewEventOccurred && newest != null
                    && mState.compareTo(newest.getValue().mState) > 0) {
                forwardPass();
            }
        }
        mNewEventOccurred = false;
    }
```

 sync 方法里对比了当前 mState 以及上一个 State ，看是应该前移还是后退，这个对应了生命周期的前进跟后退，打个比方就是从 onResume -> onPause (forwardPass)，onPause -> onResume (backwardPass)，拿 backwardPass() 举例吧。（forwardPass方法处理类似）

```java
    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator<Entry<LifecycleObserver, ObserverWithState>> descendingIterator =
                mObserverMap.descendingIterator();
        while (descendingIterator.hasNext() && !mNewEventOccurred) {
            Entry<LifecycleObserver, ObserverWithState> entry = descendingIterator.next();
            ObserverWithState observer = entry.getValue();
            while ((observer.mState.compareTo(mState) > 0 && !mNewEventOccurred
                    && mObserverMap.contains(entry.getKey()))) {
                //调用 downEvent 获取更前面的 Event
                Event event = downEvent(observer.mState);
                pushParentState(getStateAfter(event));
                //分发 Event 
                observer.dispatchEvent(lifecycleOwner, event);
                popParentState();
            }
        }
    }
		
    private static Event downEvent(State state) {
        switch (state) {
            case INITIALIZED:
                throw new IllegalArgumentException();
            case CREATED:
                return ON_DESTROY;
            case STARTED:
                return ON_STOP;
            case RESUMED:
                return ON_PAUSE;
            case DESTROYED:
                throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException("Unexpected state value " + state);
    }
```

通过源码可以看到， `backwardPass()`  方法调用 `downEvent`  获取往回退的目标 Event。

可能比较抽象，举个例子，**在 onResume 的状态，我们按了 home，这个时候就是 RESUMED 的状态变到 STARTED 的状态，对应的要发送的 Event 是 ON_PAUSE，这个就是 backwardPass() 的逻辑了**。

如果前面的代码都是引子的话，我们最终看到了一丝分发的痕迹了—— `observer.dispatchEvent(lifecycleOwner, event)` 。

```java
    static class ObserverWithState {
        State mState;
        GenericLifecycleObserver mLifecycleObserver;

        ObserverWithState(LifecycleObserver observer, State initialState) {
            mLifecycleObserver = Lifecycling.getCallback(observer);
            mState = initialState;
        }

        void dispatchEvent(LifecycleOwner owner, Event event) {
            State newState = getStateAfter(event);
            mState = min(mState, newState);
            //这里
            mLifecycleObserver.onStateChanged(owner, event);
            mState = newState;
        }
    }
```

可以看到最后调用了 GenericLifecycleObserver.onStateChanged() 方法，再跟。

```java
class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    //mWrapped 是 我们的 Observer
    private final Object mWrapped;
    //反射 mWrapped 获取被注解了的方法
    private final CallbackInfo mInfo;
    @SuppressWarnings("WeakerAccess")
    static final Map<Class, CallbackInfo> sInfoCache = new HashMap<>();

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        mWrapped = wrapped;
        mInfo = getInfo(mWrapped.getClass());
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Event event) {
        invokeCallbacks(mInfo, source, event);
    }
    
    private void invokeCallbacks(CallbackInfo info, LifecycleOwner source, Event event) {
        invokeMethodsForEvent(info.mEventToHandlers.get(event), source, event);
        invokeMethodsForEvent(info.mEventToHandlers.get(Event.ON_ANY), source, event);
    }
  
    private void invokeMethodsForEvent(List<MethodReference> handlers, LifecycleOwner source,
            Event event) {
        if (handlers != null) {
            for (int i = handlers.size() - 1; i >= 0; i--) {
                MethodReference reference = handlers.get(i);
                invokeCallback(reference, source, event);
            }
        }
    }
    //最后走到 invokeCallback 这里
    private void invokeCallback(MethodReference reference, LifecycleOwner source, Event event) {
        //noinspection TryWithIdenticalCatches
        try {
            switch (reference.mCallType) {
                case CALL_TYPE_NO_ARG:
                    reference.mMethod.invoke(mWrapped);
                    break;
                case CALL_TYPE_PROVIDER:
                    reference.mMethod.invoke(mWrapped, source);
                    break;
                case CALL_TYPE_PROVIDER_WITH_EVENT:
                    reference.mMethod.invoke(mWrapped, source, event);
                    break;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to call observer method", e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
	
    private static CallbackInfo getInfo(Class klass) {
        CallbackInfo existing = sInfoCache.get(klass);
        if (existing != null) {
            return existing;
        }
        existing = createInfo(klass);
        return existing;
    }
    
    //通过反射获取 method 信息
    private static CallbackInfo createInfo(Class klass) {
        //...
        Method[] methods = klass.getDeclaredMethods();

        Class[] interfaces = klass.getInterfaces();
        for (Class intrfc : interfaces) {
            for (Entry<MethodReference, Event> entry : getInfo(intrfc).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(handlerToEvent, entry.getKey(), entry.getValue(), klass);
            }
        }

        for (Method method : methods) {
            OnLifecycleEvent annotation = method.getAnnotation(OnLifecycleEvent.class);
            if (annotation == null) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            int callType = CALL_TYPE_NO_ARG;
            if (params.length > 0) {
                callType = CALL_TYPE_PROVIDER;
                if (!params[0].isAssignableFrom(LifecycleOwner.class)) {
                    throw new IllegalArgumentException(
                            "invalid parameter type. Must be one and instanceof LifecycleOwner");
                }
            }
            Event event = annotation.value();
            //...
            MethodReference methodReference = new MethodReference(callType, method);
            verifyAndPutHandler(handlerToEvent, methodReference, event, klass);
        }
        CallbackInfo info = new CallbackInfo(handlerToEvent);
        sInfoCache.put(klass, info);
        return info;
    }

    @SuppressWarnings("WeakerAccess")
    static class CallbackInfo {
        final Map<Event, List<MethodReference>> mEventToHandlers;
        final Map<MethodReference, Event> mHandlerToEvent;

        CallbackInfo(Map<MethodReference, Event> handlerToEvent) {
            //...
        }
    }

    static class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int callType, Method method) {
            mCallType = callType;
            mMethod = method;
            mMethod.setAccessible(true);
        }
    }

    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
}
```


这个类的代码比较多，不过也不复杂。可以看到最后代码走到了` invokeCallback()` ，通过反射调用了方法。

而这个方法是 createInfo() 方法中反射遍历我们注册的 Observer 的方法找到的被 OnLifecycleEvent 注解修饰的方法，并且按 Event 类型存储到了 info.mEventToHandlers 里。

到这里整个链路就清晰了，**我们在 Observer 用注解修饰的方法，会被通过反射的方式获取，并保存下来，然后在生命周期发生改变的时候再找到对应 Event 的方法，通过反射来调用方法**。

**注意：**源码中还有一些细节比较繁琐，比如怎么获取的方法，怎么包装的 Observer ，State 的管理以及存储等，就不在这里展开了，有兴趣的自行了解。

<a name="9d59d5ab"></a>
### 4. 图解 Lifecycle

如果被代码绕晕了，也没关系，我画了类图以及时序图，帮助大家理解，配合着类图跟时序图看代码，会容易理解很多。

<a name="8870bf02"></a>

#### 4.1 Lifecycle 相关原理类的 UML 图

核心类 UML 图整理如下：

![Lifecycle-UML.jpg](http://ww4.sinaimg.cn/large/006tNc79ly1g5cd8iumhcj318d0u0gud.jpg)<br />(图2. Lifecycle-UML图)

<a name="1e15f81c"></a>

#### 4.1 Lifecycle 原理时序图

图中起始于 onCreate ，顺便利用 onCreate 描绘整个流程。（其他生命周期原理一样，不重复画了）

![image.png](http://ww2.sinaimg.cn/large/006tNc79ly1g5cd8bel9hj31gg0u0gvw.jpg)<br />(图3. Lifecycle 时序图)<br />

<a name="f4ac431e"></a>

#### 4.3 Lifecycle State 与 Event 的关系图

图展示了 State 与 Event 的关系，以及随着生命周期走向它们发生的变化。

![Lifecycle-Seq2.png](http://ww3.sinaimg.cn/large/006tNc79ly1g5cd86nu4tj313x0hz0v6.jpg)<br />(图4. State 与 Event 的关系图)

<a name="f34f38b9"></a>
### 5. Lifecycle 的实战应用

好了，重点的原理我们分析完毕了，如果看一遍没有理解，就多看几遍。


这个小节来讲讲 Lifecycle 的**实战**应用。

Lifecycle 的应用场景非常广泛，我们可以利用 Lifecycle 的机制来**帮助我们将一切跟生命周期有关的业务逻辑全都剥离出去**，进行完全解耦，比如视频的暂停与播放，Handler 的消息移除，网络请求的取消操作，Presenter 的 attach&detach View 等等，**并且可以以一个更加优雅的方式实现**，还我们一个更加干净可读的 Activity & Fragment。

下面举个简单的例子：

<a name="9e4c4589"></a>
#### 5.1 自动移除 Handler 的消息：LifecycleHandler

我们担心 Handler 会导致内存泄露，通常会在 onDestroy 里移除消息，写多了烦，但是结合 Lifecyc le ，我们可以写出一个 **lifecycle-aware** 的 Handler，自动在 onDestroy 里移除消息，不再需要写那行样板代码。

代码实现如下：

![image.png](http://ww4.sinaimg.cn/large/006tNc79ly1g5cd82685zj30yk0u0n7h.jpg)

该代码已经包含在我的开源库 Pandora 里了，可以访问：[https://github.com/AlanCheen/Pandora](https://github.com/AlanCheen/Pandora) ，直接依赖使用，欢迎 star。

<a name="95f1f640"></a>
#### 5.2 给 ViewHolder 添加 Lifecycle 的能力

有些 App 会有长列表的页面，里面塞了各种不用样式的 Item，通常会用 RecyclerView 来实现，有时候部分 Item 需要获知生命周期事件，比如包含播放器的 Item 需要感知生命周期来实现暂停/重播的功能，借助 Lifecycle 我们可以实现。

具体实现可以参考我的开源库 Flap：[https://github.com/AlanCheen/Flap](https://github.com/AlanCheen/Flap) 。

<a name="12eaa1dd"></a>
### 6. 知识点梳理和汇总

1. `Lifecycle`  库通过在 `SupportActivity`  的 `onCreate`  中注入 `ReportFragment`  来感知发生命周期；
1. `Lifecycle`  抽象类，是 `Lifecycle`  库的核心类之一，它是对生命周期的抽象，定义了生命周期事件以及状态，通过它我们可以获取当前的生命周期状态，同时它也奠定了观察者模式的基调；（我是党员你看出来了吗:-D）
1. `LifecycleOwner`  ，描述了一个拥有生命周期的组件，可以自己定义，不过通常我们不需要，直接使用 `AppCompatActivity`  等即可；
1. `LifecycleRegistry`  是 `Lifecycle`  的实现类，它负责接管生命周期事件，同时也负责 `Observer`  的注册以及通知；
1. `ObserverWithState` ，是 Observer 的一个封装类，是它最终 通过 `ReflectiveGenericLifecycleObserve` 调用了我们用注解修饰的方法；
1. `LifecycleObserver` ，Lifecycle 的观察者，利用它我们可以享受 Lifecycle 带来的能力；
1. `ReflectiveGenericLifecycleObserver`，它存储了我们在 Observer 里注解的方法，并在生命周期发生改变的时候最终通过反射的方式调用对应的方法。

<a name="7e4acf90"></a>

### 7. 总结

**Lifecycle 是一个专门用来处理生命周期的库，它能够帮助我们将 Acitivity、Framgent 的生命周期处理与业务逻辑处理进行完全解耦，让我们能够更加专注于业务；通过解耦让 Activity、Fragment 的代码更加可读可维护。**

可以这么说 **Lifecycle 的出现彻底解决了 Android 开发遇到的生命周期处理难题**，并且还给开发者带来了新的架构姿势，让我们可以设计出更加合理的架构。

**妈妈再也不用担心我遇到生命周期难题了！**

同时 `Lifecycle` 作为 AAC 的基石，为 `LiveData`、`ViewModel` 的登场打下坚实的基础。

那么，LiveData、ViewModel 的背后又是什么原理呢？

尽请期待下一篇！



### 8. 参考与推荐

1. [https://developer.android.com/topic/libraries/architecture](https://developer.android.com/topic/libraries/architecture)
1. [https://developer.android.com/topic/libraries/architecture/lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)

