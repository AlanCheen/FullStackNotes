# 【AAC 系列一】Android 应用架构新时代来临！

![spencer-davis-1533814-unsplash.jpg](https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1557130315511-95ece967-4f15-44f0-ad03-d11d6e73c15c.jpeg#align=left&display=inline&height=853&name=spencer-davis-1533814-unsplash.jpg&originHeight=853&originWidth=1280&size=280562&status=done&width=1280)

<a name="5wLAg"></a>
### 0. 前言

> 本文是深入理解 Android Archicture Components(AAC) 系列文章的开篇。


在 2017 年，Android 推出了 Android Jetpack，它是新一代组件、工具和架构指导，旨在加快 Android 应用开发速度。

Android Jetpack 分为四大块：Architecture、UI、Foundationy 以及 Behavior，随着时间的增加，Android 团队在 Jetpack 又增添了许多组件，目前最新的版图如下。

> 注意：2019 I/O 之后 Jetpack 又多了 CameraX 等，图中还未包含。


![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1554964996215-bb50f5c0-8073-49e0-b22e-9c95c38efb1c.png#align=left&display=inline&height=808&name=image.png&originHeight=808&originWidth=1600&size=250365&status=done&width=1600)

(图1-Android Jetpack)<br />
<br />

<a name="nd7tO"></a>
### 1. Jetpack 架构组件

Jetppack 意在帮助我们开发者加快开发速度，能够让我们专注于自己的业务而不是花费大量时间去做一些兼容等琐碎的工作。<br />
<br />在我个人的体验角度而言， `Architecture` （架构组件） 对我的实际开发工作的帮助非常大。<br />
<br />Architecture 模块有着良好的兼容性，并且架构组件里的每个组件能够帮助我们应对某一类 `难题/痛点` ：<br />

1. `Lifecycle`  ：能够帮我们轻松的应对 `Activity/Fragment`  的生命周期问题，能够让我们以一种更加解耦的方式处理生命周期的变化问题，以及轻松避免内存泄露；
1. `LiveData`  ：基于观察者模式、并且感知生命周期的数据持有类，能够帮助我们更好地解耦与处理数据；
1. `ViewModel` + `Data Binding` ：为我们在 Android 平台上实现 MVVM 架构提供了非常有效而强大的支持；
1. `Room` ：提供了一种更加友好高效的数据库持久化的功能；
1. `WorkManager` ：为我们执行后台任务提供了一站式解决方案；
1. `Navigation` ：能够帮助我们更加方便地构建单 Activity 应用；
1. `Paging` ：能够帮助我们应对加载大数据问题；


<br />

<a name="w4Zjc"></a>
### 2. 官方推荐的 Android 应用新架构

Android 在推出 架构组件 的同时，还推荐了一个适合 Android 应用的架构，各个组件组织起来，如下图：<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1557128632565-fca2ae3d-b53f-4828-83dc-f2d2b439c7a5.png#align=left&display=inline&height=360&name=image.png&originHeight=720&originWidth=960&size=50496&status=done&width=480)<br />(图 2-Android 应用新架构)<br />
<br />
<br />每个组件都关注自己的事情，互不干扰，让我们的应用更加解耦且职责清晰。<br />
<br />**为什么我说 Android 应用架构新时代来临？**<br />
<br />因为，这似乎是 Android 团队第一次官方推荐一种 Android 应用架构，在这之前，Android 应用开发一直没有什么官方主导推荐的架构。我们做开发，也一直都是比较随意的，比如 MVC、MVP 甚至是 前端的 Flux 类型的架构都有在 Android 上被应用。而这一次一下子这么多牛逼的架构组件，再加上官方架构指导，可以称得上 `Android 应用架构新时代来临`了！<br />

<a name="QkAZg"></a>
### 3. 小结

很显然了，架构组件对于我们来说具有非常大的实战价值，我们必须去了解并学会它们！

架构组件 如此NB，我们应该如何更好地使用它们呢？<br />
<br />它们背后的原理是什么样子的呢？<br />
<br />**不要着急，船长后续会写一些列文章，来带大家一起学习一下这 NB 的架构组件！**<br />
<br />记得关注我，**加个星标**，不要错过了喔！<br />
<br />Have a nice weekend，下周见。<br />

<a name="C9VFi"></a>
### 4. 参考与推荐

jetpack官网：[https://developer.android.com/jetpack](https://developer.android.com/jetpack)<br />YouTube 系列：[https://www.youtube.com/playlist?list=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2](https://www.youtube.com/playlist?list=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2)<br />Jetpack 发布介绍：[https://googledeveloperschina.blogspot.com/2018/05/android-jetpack.html](https://googledeveloperschina.blogspot.com/2018/05/android-jetpack.html)<br />架构指南：[https://developer.android.com/jetpack/docs/guide](https://developer.android.com/jetpack/docs/guide)<br />App实战指南：[https://github.com/googlesamples/android-sunflower](https://github.com/googlesamples/android-sunflower)
