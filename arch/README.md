# 架构




## 设计原则

design principle


### 分离关注点

[分离关注点（SoC，Separation of concerns）](https://en.wikipedia.org/wiki/Separation_of_concerns)

> In computer science, separation of concerns (SoC) is a design principle for separating a computer program into distinct sections, so that each section addresses a separate concern. A concern is a set of information that affects the code of a computer program. A concern can be as general as the details of the hardware the code is being optimized for, or as specific as the name of a class to instantiate. A program that embodies SoC well is called a modular[1] program. Modularity, and hence separation of concerns, is achieved by encapsulating information inside a section of code that has a well-defined interface. Encapsulation is a means of information hiding.[2] Layered designs in information systems are another embodiment of separation of concerns (e.g., presentation layer, business logic layer, data access layer, persistence layer).[3]

Separation of concerns results in higher degrees of freedom for some aspect of the program's design, deployment, or usage. Common among these is higher degrees of freedom for simplification and maintenance of code. When concerns are well-separated, there are higher degrees of freedom for module reuse as well as independent development and upgrade. Hiding the implementation details of modules behind an interface enables improving or modifying a single concern's section of code without having to know the details of other sections, and without having to make corresponding changes to those sections. Modules can also expose different versions of an interface, which increases the freedom to upgrade a complex system in piecemeal fashion without interim loss of functionality.

Separation of concerns is a form of abstraction. As with most abstractions, interfaces must be added and there is generally more net code to be executed. So despite the many benefits of well separated concerns, there is often an associated execution penalty.







### 模型驱动界面

通过模型驱动界面（最好是持久性模型）。

模型是负责处理应用数据的组件，它们独立于应用中的`View`对象和应用组件，因此不受应用的生命周期以及相关的关注点的影响。

持久性是理想之选，原因如下：

1. 如果 Android 系统销毁应用以释放资源，用户不会丢失数据；
2. 当网络连接不稳定或不可用时，应用会继续工作；

应用所基于的模型类应明确定义数据管理职责，这样将使应用更可测试且更一致。



### 






## 资料

1. 应用架构指南 ：https://developer.android.com/jetpack/docs/guide#connect-viewmodel-repository