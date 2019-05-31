# 【AAC 系列四】深入理解架构组件：ViewModel



### 0. 前言



> 本文是深入理解「Android Architecture Components」系列文章第四篇，ViewModel
> 源码基于 AAC 1.1.1 版本



在上一篇 LiveData 原理分析一文中，我们提到了 ViewModel，它跟 LiveData 配合能够把价值发挥到最大，这一篇，我们就来讲讲 ViewModel 的实现原理。



先来简单了解下 ViewModel：

> The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html) class is designed to store and manage UI-related data in a lifecycle conscious way. The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html)class allows data to survive configuration changes such as screen rotations.



ViewModel 被设计来管理跟 UI 相关的数据， 并且能够感知生命周期；另外 ViewModel 能够在配置改变的情况下让数据得以保留。

实际上 ViewModel 不仅可以管理数据，而且还可以存放业务逻辑处理的代码，另外还能够方便 Activity 中的 不同Fragment 之间的通信，这个解决了以往我们 Fragment 之间通信的一个大问题。



### 重点类

- ViewModel，抽象类，用来负责准备和管理 Activity/Fragment 的数据，并且还能处理 Activity/Fragment 跟外界的通信，通常还存放业务逻辑，类似 Presenter；ViewModel 通常会暴露 LiveData 给 Activity/Fragment；并且 Activity 配置改变并不会导致 ViewModel 回收；
- ViewModelStore，
- ViewModelStoreOwner , ViewModelStore 的拥有者，类似 LifecycleOwner 拥有 Lifecycle 一样；





ViewModel 的使用注意事项：

1. ViewModel 不会因为 Activity 配置改变而被销毁，所以绝对不要持有那些跟 Activity 相关的类，比如Activity 里的某个 View，让 ViewModel 持有 Activity 会导致内存泄露，还要注意的是连 Lifecycle 也不行；
2. ViewModel 应该只负责管理数据，不能去访问 UI，更不能持有它；









### 参考与推荐

1. https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54

