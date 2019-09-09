# Jetpack

[TOC]



### 什么是 Jetpack



Jetpack is a set of libraries and guidance of modern Android development.



![image.png](https://cdn.nlark.com/yuque/0/2019/png/138547/1554964996215-bb50f5c0-8073-49e0-b22e-9c95c38efb1c.png)





Jetpack 分为四个类目：

1. Architecture
2. UI
3. Behavior
4. Foundation





### Architecture



This architecture revolves around the following principles (围绕着以下几个原则):

- Separation of concerns，关注点分离
- Loose coupling，解耦
- Observer pattern，观察者模式
- Inversion of Control，控制反转



 

#### Room



Room Benefits

- Object Mapping
- Annotation to generate boilerplate
- Compile time error checking
- Observable queries,including RxJava folwables and LiveData
- List,Optional and Guava support
- Migrations support
- Testing support



#### WorkManager

WorkManager is a feature-rich API that compatibly schedules deferrable backgound work.



WorkManager Benefits

- Asynchronous one-off and periodic tasks
- Chaining with Input/Output 
- Constraints
- Handles compatibility
- System health best practices
- Guaranteed execution
- Query state to display in UI 



#### ViewModel 



ViewModels are objects that provide data for UI components and survive configuration changes. 



ViewModel 是一个很好的稳定的地方用来存放所有的 UI 数据。通常 ViewModel 会包含 LiveData。



#### LiveData

LiveData is built for easy communication between the UI and deeper layers of your app's architecture.

LiveData is an observable data holder. It is lifecycle aware.

假如 Activity 没展示，那么 LiveData 是不会触发更新的，如果 Activity 被销毁，那么订阅关系也会被自动解除。所以使用LiveData 就不会出现更新离屏的（offscreen）或已经销毁的页面



Lifecycle Benefits

- Avoid Lifecycle related UI state loss: ViewModel
- Observability for your UI: LiveData
- Avoid Lifecycle related memory leaks
- LiveData transformations
- UI-Database observation: LiveData/Room
- XML-ViewModel observation: Data Binding
- Querying and observe UI Lifecycles state



#### Paging



Paging efficiently and gradually loads information on-demand from local storage, network or both.

t avoids tricky SQLite cursor performance issues.



Paging Benefits

- Flexible; supports any data source
- Works out of the box with Room and RecyclerView
- Supports large and infinite length lists
- Loaded data shown automatically with LiveData
- Supports RxJava



#### Navigation 



Navigations Benefits

- Handles fragment transactions

- Implements proper up and back

- Deep linking support

- Easy animated transitions

- Common navigation pattern support

  

Destinations, specific screen you can go to.

 



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6tcewsvj4j319y0u0dyd.jpg" alt="image-20190909162529381" style="zoom:33%;" />





this is just scratching the surface of the proverbial iceberg that is the architecture components in Jetpack.





### 笔记



Android architecture components gives us a template to write production-ready Android code.

It helps us build robust code. We do not have to be lifecycle-aware.

So you could eliminate boilerplate code and focus on what makes your app great.



### Refs

[介绍Android Jetpack](https://www.youtube.com/watch?v=LmkKFCfmnhQ&list=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2)

[Android Jetpack: Improve Your App's Architecture](https://www.youtube.com/watch?v=7p22cSzniBM)