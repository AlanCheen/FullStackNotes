# 模块化 App 架构

Modular Android App Architecture



### 为什么要模块化？



1. 更好的扩展性（scale） 
2. 更好的维护性（maintainability）
3. 更快的编译速度（faster compilation）
4. 更快地持续集成（faster CI）
5. 可以更快地搭建 App，就像玩积木，可以用多个模块快速构建起一个完整的 App；
6. App 能够多方面受益，例如减少包大小



当 App 还小，我们能够知道整个 App 的内容，当 App 增长，人员增长，就很难维护同一个工程。

如果能够建立独立的模块，那么每个模块都可以独立开发，那么就不需要拥挤在一个工程里了。

这样还能带来个好处就是更容易维护，每个人各司其职维护自己负责的代码即可，不会混乱。例如找代码资源等只需要在相关工程里找，明显更简单。

模块化还有个好处就是能够提高编译速度，在只有一个工程时，我们修改代码需要编译整个工程，而模块化后只需要编译修改过后的工程。

类似的，还能加速我们的 CI。



当我们把 App 做成模块化后，我们可以从现有的模块中选出几个快速构建起另外一个完整的 App。

配合动态下发（Google Player）的能力，App 还可以减少包大小。



### 如何做到模块化？

how should we modularize ?



可以按照`功能划分（feature）`或者`层级划分(layer)`。



#### 功能划分（Feature modularization）



1. `一个打包工程`，按类型算是 Application 工程，`只负责打包`，它依赖其他功能模块；

2. `多个功能模块工程`，每个工程是 library 类型，按照功能区分，例如登录功能、首页或商品详情等等；

   



#### 层级划分（Layer modularization）



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6t3nk5jvkj31cq0pgdv6.jpg" alt="image-20190909112222300" style="zoom: 33%;" />



![image-20190909112456443](https://tva1.sinaimg.cn/large/006y8mN6ly1g6t3q6oxfvj30js0cw0xh.jpg)





Feature modularization brings you encapsulation and possibility of on-demand delivery.

Modularization by layer brings you isolation,allows you to isolate your third-party dependencies or the layers in your app,and also brings structure to our app.



按层分的模块化更加简单，适合小的 App，按功能划分的更加复杂，很适合庞大的 App。

其实两者也可以结合，当 App 还小，layer modularization 可以用用，App 大了，再换成 feature modularization 更加合适。





<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6ta8xujbvj31060i0q5y.jpg" alt="image-20190909151031171" style="zoom:50%;" />





you should always compare your short-term costs with your long-term benefits and make a proper decision.



### 资料

[Build a Modular Android App Architecture (Google I/O'19)](https://www.youtube.com/watch?v=PZBg5DIzNww&feature=youtu.be)



# 