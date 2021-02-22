# Google IO 2019

> 迁移语雀 2021-2-22

[TOC]

### ViewBinding

<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6t513ycpkj30ti0fotej.jpg" alt="image-20190909121001851" style="zoom:50%;" />



AS 3.6 会上。



### Lifecycles

A way to observe lifecycle events of Fragments and Activities.

ViewModel- state retained across config changes.

LiveData - lifecycle-aware observable





#### **ViewModel & Saved State**



**Saved State**

![image-20190909121059996](https://tva1.sinaimg.cn/large/006y8mN6ly1g6t5240vmtj30re0j0dkw.jpg)



**ViewModel**

![image-20190909120558552](https://tva1.sinaimg.cn/large/006y8mN6ly1g6t4wvtom9j319k0fq0yx.jpg)



两者对比

![](https://tva1.sinaimg.cn/large/006y8mN6ly1g6t8gagv5fj312g0cm75u.jpg)





ViewModel 跟 SavedState 并不是对立的，两者作用不一样。





### WorkManager

WorkManager is a background processing library for work that is specifically deferrable,meaning that it doesn't have to be executed right away.



- deferrable
- persistent
- constraint-based
- Backwards compatible



### Room

Room is SQLite object mapping library.



协程支持



### Paging



Paging is a library to load large lists into RecyclerView lazily.



Error handling...



### Navigation

A library to manager in app UI flows.





Arch Components

### Ref

[What's New in Architecture Components (Google I/O'19)](https://www.youtube.com/watch?v=Qxj2eBmXLHg&feature=youtu.be)



