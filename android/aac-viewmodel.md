# 【AAC 系列四】深入理解架构组件：ViewModel



### 0. 前言



> 本文是深入理解「Android Architecture Components」系列文章第四篇，ViewModel
> 源码基于 AAC 1.1.1 版本



在上一篇 `LiveData`  原理分析一文中，我们提到了 `ViewModel` ，它跟 LiveData 配合能够把价值发挥到最大。

这一篇，我们就来深入浅出一下 ViewModel ，来讲讲 ViewModel 的使用方式、生命周期、以及它的实现原理。



### 1. ViewModel 概述



在深入讲解 ViewModel 之前，先来简单了解下 ViewModel：



> The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html) class is designed to store and manage UI-related data in a lifecycle conscious way. The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html)class allows data to survive configuration changes such as screen rotations.



ViewModel 被设计来管理跟 UI 相关的数据， 并且能够感知生命周期；另外 ViewModel 能够在`配置改变`的情况下让数据得以保留。ViewModel 重在`以感知生命周期的方式` 管理界面相关的数据。



我们知道类似旋转屏幕等配置项改变会导致我们的 Activity 被销毁并重建，此时 Activity 持有的数据就会随着丢失，而` ViewModel 则并不会被销毁`，从而能够帮助我们在这个过程中保存数据，而不是在 Activity 重建后重新去获取。并且 ViewModel 能够让我们`不必去担心潜在的内存泄露问题`，同时 ViewModel 相比于用`onSaveInstanceState()` 方法更有优势，比如存储相对大的数据，并且不需要序列化以及反序列化。



总之 ViewModel，优点多多，接下去我们介绍下 ViewModel 的基本使用。



### 2. ViewModel 的基本使用



ViewModel 的使用也非常简单，Android 提供了一个 ViewModel 类让我们去继承，并且提供了 `ViewModelProviders` 来帮助我们实例化 ViewModel。



搬运一个官网例子如下：



**1）**自定义一个`MyViewModel` 继承自`ViewModel`，并且包含了一个 LiveData：

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



**2）**在 Activity 中借助 `ViewModelProviders` 获得 ViewModel 的实例，并借助 LiveData 订阅 users 的变化通知：

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



### 3. ViewModel 的生命周期



我们在前面提到过，ViewModel 并不会因为 Activity 的配置改变销毁而一起销毁，那么 ViewModel 的生命周期到底是怎么样的呢？



![viewmodel-lifecycle.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1553846610367-effa7d60-5934-4152-9940-888da1262773.png#align=left&display=inline&height=543&name=viewmodel-lifecycle.png&originHeight=543&originWidth=522&size=31523&status=done&width=522)



### 4. ViewModel 的实现原理



### 5. 知识点梳理和汇总



**重点类讲解**：

- `ViewModel` ，抽象类，用来负责准备和管理 Activity/Fragment 的数据，并且还能处理 Activity/Fragment 跟外界的通信，通常还存放业务逻辑，类似 Presenter；ViewModel 通常会暴露 LiveData 给 Activity/Fragment；并且 Activity 配置改变并不会导致 ViewModel 回收；
- `AndroidViewModel`，一个会持有 `Application`  的 ViewModel；
- `ViewModelStore` ，负责存储 ViewModel 的类，并且还负责在 ViewModel 被清除之前通知它，也即调用 ` ViewModel.onCleared()`;
- `ViewModelStoreOwner`  , ViewModelStore 的拥有者，类似 LifecycleOwner 的角色；
- `HolderFragment`，
- `HolderFragmentManager` ，



**ViewModel 的使用注意事项**：

1. `不要持有 Activity` ：ViewModel 不会因为 Activity 配置改变而被销毁，所以绝对不要持有那些跟 Activity 相关的类，比如Activity 里的某个 View，让 ViewModel 持有 Activity 会导致内存泄露，还要注意的是连 Lifecycle 也不行；
2. `不能访问 UI` ：ViewModel 应该**只负责管理数据**，不能去访问 UI，更不能持有它；



### 6. 总结



ViewModel 提供给我们一个方式在特定的生命周期内去管理跟 UI 相关的数据。

ViewModel 能够帮助我们把数据管理的逻辑从 Activity/Fragment 中剥离开。



实际上 ViewModel 不仅可以管理数据，而且还可以存放业务逻辑处理的代码，另外还能够方便 Activity 中的 不同Fragment 之间的通信，这个解决了以往我们 Fragment 之间通信的一个大问题。





### 7. 参考与推荐

0. https://developer.android.com/topic/libraries/architecture/viewmodel#java

1. https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54

