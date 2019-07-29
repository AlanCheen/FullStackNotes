# 【AAC 系列四】深入理解架构组件：ViewModel

[TOC]

### 0. 前言



> 本文是深入理解「Android Architecture Components」系列文章第四篇
> 源码基于 AAC 1.1.1 版本



在上一篇 `LiveData`  原理分析一文中，我们提到了 `ViewModel` ，它跟 LiveData 配合能够把价值发挥到最大。

这一篇，我们就来深入浅出一下 ViewModel ，来讲讲 ViewModel 的使用方式、生命周期、以及它的实现原理。



### 1. ViewModel 概述



在深入讲解 ViewModel 之前，先来简单了解下 ViewModel：



> The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html) class is designed to store and manage UI-related data in a lifecycle conscious way. The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html)class allows data to survive configuration changes such as screen rotations.



ViewModel 被设计来管理跟 UI 相关的数据， 并且能够感知生命周期；另外 ViewModel 能够在`配置改变`的情况下让数据得以保留。ViewModel 重在`以感知生命周期的方式` 管理界面相关的数据。



我们知道类似旋转屏幕等配置项改变会导致我们的 Activity 被销毁并重建，此时 Activity 持有的数据就会跟随着丢失，而` ViewModel 则并不会被销毁`，从而能够帮助我们在这个过程中保存数据，而不是在 Activity 重建后重新去获取。并且 ViewModel 能够让我们`不必去担心潜在的内存泄露问题`，同时 ViewModel 相比于用`onSaveInstanceState()` 方法更有优势，比如存储相对大的数据，并且不需要序列化以及反序列化。



总之 ViewModel，优点多多，接下去我们介绍下 ViewModel 的基本使用。



### 2. ViewModel 的基本使用



ViewModel 的使用也非常简单，Android 提供了一个 ViewModel 类让我们去继承，并且提供了 `ViewModelProviders` 来帮助我们实例化 ViewModel。



搬运一个官网例子如下：

**a）**：先添加一下依赖：

```groovy
def lifecycle_version = "1.1.1"
implementation "android.arch.lifecycle:extensions:$lifecycle_version"
```



**b）**：自定义一个`MyViewModel` 继承自`ViewModel`，并且包含了一个 `LiveData`：

```java
public class MyViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
```



**c）**：在 Activity 中借助 `ViewModelProviders` 获得 ViewModel 的实例，并借助 LiveData 订阅 users 的变化通知：

```java
public class MyActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getUsers().observe(this, users -> {
            // update UI
        });
    }
}
```



就这样简单的步骤，我们就使用上了 ViewModel，即便 MyActivity 重新创建，MyActivity 拿到的 MyViewModel 都会是一个实例。

那么问题来了， `ViewModel 的生命周期到底是怎么样的呢？`



它背后蕴藏什么原理呢？咱们接下来看看。



### 3. ViewModel 的生命周期



我们在前面提到过，ViewModel 并不会因为 Activity 的配置改变销毁而一起销毁，那么 ViewModel 的生命周期到底是怎么样的呢？



看一张官网给的图：

<img width="522" height="543" src="https://cdn.nlark.com/yuque/0/2019/png/138547/1553846610367-effa7d60-5934-4152-9940-888da1262773.png">



可以看到 ViewModel 在 Activity 的重建时依然存活。

`Why`？

### 4. ViewModel 的实现原理



回顾下我们之前使用 ViewModel 的代码：

```java
MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
```

这里先从 `ViewModelProviders.of` 方法入手看看：

```java
//ViewModelProviders
public static ViewModelProvider of(@NonNull FragmentActivity activity) {
  //传入了 null
  return of(activity, null);
}

@NonNull
@MainThread
public static ViewModelProvider of(@NonNull Fragment fragment, @Nullable Factory factory) {
  //检查合法性
  Application application = checkApplication(checkActivity(fragment));
  if (factory == null) {
    //走到这里，返回了 AndroidViewModelFactory 的单例
    factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
  }
  return new ViewModelProvider(ViewModelStores.of(fragment), factory);
}
```



`ViewModelProviders.of()` 方法帮我们在默认情况下构建了一个 `AndroidViewModelFactory` 工厂类，来帮助创建 ViewModel，并且返回了一个在当前 Activity 生命周期内的 `ViewModelProvider`。



看一下 `AndroidViewModelFactory `: 

```java
    public static class AndroidViewModelFactory extends ViewModelProvider.NewInstanceFactory {
				//单例模式
        private static AndroidViewModelFactory sInstance;
        @NonNull
        public static AndroidViewModelFactory getInstance(@NonNull Application application) {
            if (sInstance == null) {
                sInstance = new AndroidViewModelFactory(application);
            }
            return sInstance;
        }

        private Application mApplication;
        public AndroidViewModelFactory(@NonNull Application application) {
            mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (AndroidViewModel.class.isAssignableFrom(modelClass)) {
                //noinspection TryWithIdenticalCatches
                try {
                    return modelClass.getConstructor(Application.class).newInstance(mApplication);
                } catch (NoSuchMethodException e) {
                  //...
                }
            }
            return super.create(modelClass);
        }
    }
```



AndroidViewModelFactory 其实就是一个通过`反射`方法来构建 ViewModel 的工厂类，且是个单例。

看下来发现我们 ViewModel 的 class 是传给了 `ViewModelProvider.get()` 方法。

来看看 get 方法：

```java
//ViewModelProvider
@NonNull
@MainThread
public <T extends ViewModel> T get(@NonNull Class<T> modelClass) {
  String canonicalName = modelClass.getCanonicalName();
  if (canonicalName == null) {
    throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
  }
  //每个类都有一个唯一的 key
  return get(DEFAULT_KEY + ":" + canonicalName, modelClass);
}

@NonNull
@MainThread
public <T extends ViewModel> T get(@NonNull String key, @NonNull Class<T> modelClass) {
  //先从 store 中获取
  ViewModel viewModel = mViewModelStore.get(key);

  if (modelClass.isInstance(viewModel)) {
    //noinspection unchecked
    return (T) viewModel;
  } else {
    //noinspection StatementWithEmptyBody
    if (viewModel != null) {
      // TODO: log a warning.
    }
  }

  viewModel = mFactory.create(modelClass);
  mViewModelStore.put(key, viewModel);
  //noinspection unchecked
  return (T) viewModel;
}
```



解释下代码，发现它为每个 ViewModel 的 class 都构建了一个`唯一的 key` , 并通过这个 key 尝试去一个叫做 `ViewModelStore` 的类获取 ViewModel 的实例，如果为null ，那么会通过 Factory 去创建，并把新的实例存入到 ViewModelStore。



主要的流程似乎就跟我们平时做缓存一样，好像没什么特别的东西，**没有看到一丝跟生命周期相关的处理的代码**，这是怎么回事？



再仔细思考一下，get 方法会优先从这个 ViewModelStore 中去拿，那么理论上**只要保证 ViewModelStore 这个类在配置变化的过程中没有被销毁，那么就可以保证我们创建的 ViewModel 不会被销毁**，我们肯定漏掉了关于 ViewModelStore 的重要东西。



回过去再仔细看一下，ViewModelProvider 的构建好像并不简单：

```java
new ViewModelProvider(ViewModelStores.of(fragment), factory);
```



这里传入了一个 通过 ViewModelStores 类创建的 ViewModelStore，并且传入了 fragment，一定有蹊跷。



```java
//ViewModelStores
public static ViewModelStore of(@NonNull Fragment fragment) {
  if (fragment instanceof ViewModelStoreOwner) {
    return ((ViewModelStoreOwner) fragment).getViewModelStore();
  }
  return holderFragmentFor(fragment).getViewModelStore();
}
```



这里又多出来了几个类 `ViewModelStoreOwner`、`HolderFragment` ，让我们来追查一下。



```java
public interface ViewModelStoreOwner {
    @NonNull
    ViewModelStore getViewModelStore();
}
```



ViewModelStoreOwner 跟 LifecycleOwner 类似，只是个接口定义，重点来看看`holderFragmentFor(fragment)` 方法返回的 `HolderFragment`。

```java
//HolderFragment
public static HolderFragment holderFragmentFor(Fragment fragment) {
  return sHolderFragmentManager.holderFragmentFor(fragment);
}
```

方法又走到了 HolderFragmentManager 类，怎么又多了个 HolderFragmentManager ，神烦啊。



```java
static class HolderFragmentManager {
    private Map<Activity, HolderFragment> mNotCommittedActivityHolders = new HashMap<>();
    private Map<Fragment, HolderFragment> mNotCommittedFragmentHolders = new HashMap<>();

    private ActivityLifecycleCallbacks mActivityCallbacks =
            new EmptyActivityLifecycleCallbacks() {
                @Override
                public void onActivityDestroyed(Activity activity) {
                    HolderFragment fragment = mNotCommittedActivityHolders.remove(activity);
                    if (fragment != null) {
                        Log.e(LOG_TAG, "Failed to save a ViewModel for " + activity);
                    }
                }
            };

    private boolean mActivityCallbacksIsAdded = false;

    private FragmentLifecycleCallbacks mParentDestroyedCallback =
            new FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(FragmentManager fm, Fragment parentFragment) {
                    super.onFragmentDestroyed(fm, parentFragment);
                    HolderFragment fragment = mNotCommittedFragmentHolders.remove(
                            parentFragment);
                    if (fragment != null) {
                        Log.e(LOG_TAG, "Failed to save a ViewModel for " + parentFragment);
                    }
                }
            };

    void holderFragmentCreated(Fragment holderFragment) {
        Fragment parentFragment = holderFragment.getParentFragment();
        if (parentFragment != null) {
            mNotCommittedFragmentHolders.remove(parentFragment);
            parentFragment.getFragmentManager().unregisterFragmentLifecycleCallbacks(
                    mParentDestroyedCallback);
        } else {
            mNotCommittedActivityHolders.remove(holderFragment.getActivity());
        }
    }

    private static HolderFragment findHolderFragment(FragmentManager manager) {
        if (manager.isDestroyed()) {
            throw new IllegalStateException("Can't access ViewModels from onDestroy");
        }

        Fragment fragmentByTag = manager.findFragmentByTag(HOLDER_TAG);
        if (fragmentByTag != null && !(fragmentByTag instanceof HolderFragment)) {
            throw new IllegalStateException("Unexpected "
                    + "fragment instance was returned by HOLDER_TAG");
        }
        return (HolderFragment) fragmentByTag;
    }

    private static HolderFragment createHolderFragment(FragmentManager fragmentManager) {
        HolderFragment holder = new HolderFragment();
        fragmentManager.beginTransaction().add(holder, HOLDER_TAG).commitAllowingStateLoss();
        return holder;
    }

    HolderFragment holderFragmentFor(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        HolderFragment holder = findHolderFragment(fm);
        if (holder != null) {
            return holder;
        }
        holder = mNotCommittedActivityHolders.get(activity);
        if (holder != null) {
            return holder;
        }

        if (!mActivityCallbacksIsAdded) {
            mActivityCallbacksIsAdded = true;
            activity.getApplication().registerActivityLifecycleCallbacks(mActivityCallbacks);
        }
        holder = createHolderFragment(fm);
        mNotCommittedActivityHolders.put(activity, holder);
        return holder;
    }

    HolderFragment holderFragmentFor(Fragment parentFragment) {
        FragmentManager fm = parentFragment.getChildFragmentManager();
        HolderFragment holder = findHolderFragment(fm);
        if (holder != null) {
            return holder;
        }
        holder = mNotCommittedFragmentHolders.get(parentFragment);
        if (holder != null) {
            return holder;
        }

        parentFragment.getFragmentManager()
                .registerFragmentLifecycleCallbacks(mParentDestroyedCallback, false);
        holder = createHolderFragment(fm);
        mNotCommittedFragmentHolders.put(parentFragment, holder);
        return holder;
    }
}
```



从源码中我们可以获知 `HolderFragmentManager` 主要做这几件事：

1. 在我们想要获取 ViewModel 实例的时候，会先构建一个 `HolderFragment`，将它`添加我们的宿主（Activity/Fragment）中`，并将它缓存起来；

2. 同时通过注册回调来监听宿主的生命周期，Activity 对应 `ActivityLifecycleCallbacks` ，Fragment 对应 `FragmentLifecycleCallbacks` ，在宿主销毁的时候清理缓存；



类如其名，`HolderFragmentManager`负责管理 HolderFragment，看到它注入了 HolderFragment，接下去看看 HolderFragment。



`HolderFragment`  源码精简如下：

```java
public class HolderFragment extends Fragment implements ViewModelStoreOwner {
    private static final String LOG_TAG = "ViewModelStores";

    private static final HolderFragmentManager sHolderFragmentManager = new HolderFragmentManager();

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public static final String HOLDER_TAG =
            "android.arch.lifecycle.state.StateProviderHolderFragment";

    private ViewModelStore mViewModelStore = new ViewModelStore();
		//看这里看这里看这里
    public HolderFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sHolderFragmentManager.holderFragmentCreated(this);
    }
		...
    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModelStore.clear();
    }
}
```



HolderFragment 内部持有了一个`ViewModelStore`，并且实现了我们之前提到的 `ViewModelStoreOwner` 接口，并且最为主要的是这段代码：

```java
public HolderFragment() {
  setRetainInstance(true);
}
```



`Fragment.setRetainInstance(true)`  方法可以实现的效果为，在 Activity 配置改变后依然保存。



到这里 ViewModel 实现的原理就清晰了：**通过注入一个 retainInstance 为 true 的 HolderFragment ，利用 Fragment 的特性来保证在 Activity 配置改变后依然能够存活一下，并且保证了 HolderFragment 内部的 ViewModelStore 的存活，最终保证了 ViewModelStore 内部储存的 ViewModel 缓存存活，从而实现了 ViewModel 的生命周期这个特点功能**。(又是 Fragment！)



### 5. 图解 ViewModel



ViewModel 重点类类图：



<img width="899" height="598" src="https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1559610522523-d6d13063-c7a2-4b00-a3bd-de50765ed8f6.jpeg">

ViewModel原理实现序列图：



<img width="2996" height="1642" src="https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1559610501807-ff84cb4a-a848-447d-8704-d2a3109564a2.jpeg">

### 6. 知识点梳理和汇总



**重点类讲解**：

- `ViewModel` ，抽象类，用来负责准备和管理 Activity/Fragment 的数据，并且还能处理 Activity/Fragment 跟外界的通信，通常还存放业务逻辑，类似 Presenter；ViewModel 通常会暴露 LiveData 给 Activity/Fragment；并且 Activity 配置改变并不会导致 ViewModel 回收；
- `AndroidViewModel`，一个会持有 `Application`  的 ViewModel；
- `ViewModelStore` ，负责存储 ViewModel 的类，并且还负责在 ViewModel 被清除之前通知它，也即调用 ` ViewModel.onCleared()`;
- `ViewModelStoreOwner`  , 是抽象 “ViewModelStore 的拥有者” 的接口定义，类似 LifecycleOwner 的角色，实现了它的有 HolderFragment、FragmentActivity；
- `HolderFragment`，一个 `retainInstance`属性为`true` 并实现了 `ViewModelStoreOwner` 的 Fragment，用来保存 `ViewModelStore`，并保证它在配置修改时不被销毁；
- `HolderFragmentManager` ，负责创建、注入、缓存等管理 HolderFragment 的工作；



**ViewModel 原理总结**：

通过注入一个 `retainInstance` 为` true` 的 HolderFragment ，利用 Fragment 的特性来保证在 Activity 配置改变后依然能够存活下来，并且保证了 HolderFragment 内部的 ViewModelStore 的存活，最终保证了 ViewModelStore 内部储存的 ViewModel 缓存存活，从而实现 ViewModel 在 Activity 配置改变的情况下不销毁的功能。



**ViewModel 的使用注意事项**：

1. `不要持有 Activity` ：ViewModel 不会因为 Activity 配置改变而被销毁，所以绝对不要持有那些跟 Activity 相关的类，比如Activity 里的某个 View，让 ViewModel 持有 Activity 会导致内存泄露，还要注意的是连 Lifecycle 也不行；
2. `不能访问 UI` ：ViewModel 应该**只负责管理数据**，不能去访问 UI，更不能持有它；



### 7. 总结



ViewModel 利用 Fragment 的特性，提供给我们一个方式在特定的生命周期内去管理跟 UI 相关的数据；能够帮助我们把数据管理的逻辑从 Activity/Fragment 中剥离开。



实际上 ViewModel 不仅可以管理数据，而且还可以存放业务逻辑处理的代码，另外还能够方便 Activity 中的不同Fragment 之间互相通信，这个解决了以往我们 Fragment 之间通信的一个大问题。



深入了解完 Lifecycle、LiveData、ViewModel 之后，可以发现它们确实非常强大，能够切实的帮助我们解决实际开发过程中遇到的问题。



强烈推荐大家赶紧上车体验，再晚就买不到票了啊。

### 8. 参考与推荐

0. https://developer.android.com/topic/libraries/architecture/viewmodel#java

1. https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54

