# 第15章-Android性能优化

Android性能优化主要包括布局优化、绘制优化、内存泄露优化、响应速度优化、ListView优化、Bitmap优化、线程优化以及一些优化建议.  

实际上每个面都可以扩展很多,书中讲的不太多(篇幅少),需要在实际中多去运用.  

后面还有MAT的基本使用,我没记,可以去搜,资料不少~  

## 布局优化

1. 减少布局文件的层级(测量/布局/绘制的时间减少):可以使用RelativeLayout来减少嵌套,从而达到减少层级的目的,另外在**相同层级**的情况下使用LinearLayout(相比于RelativeLayout更高效)  
2. 使用`include`标签复用,`merge`标签降低层级,`ViewStub`来实现懒加载,另外补充一个`Space`可以用来占位

## 绘制优化(onDraw)

主要是避免执行大量的操作.  

1. 不要创建新的局部对象,因为onDraw可能会被频繁调用,会在一瞬间产生大量的临时对象,会**导致占用过多内存,系统更加频繁的gc,降低执行效率**
2. 不要做耗时的任务

## 内存泄露优化

其实内存泄露有很多种情况,但是书中列举的比较少

1. 静态变量导致的内存泄露(比如静态的context,静态的view)
2. 单例模式持有Activity
3. 属性动画(repeatCount为无限模式)

## ListView和Bitmap优化

ListView使用viewholder模式,Bitmap在12章有讲,这里不重复.


## 线程优化

主要是采用线程池(11章有讲)  

1. 避免存在大量的Thread
2. 重用Thread,避免线程创建和销毁所带来的开销
3. 线程池还能控制最大并发数,避免大量线程因互相抢占系统资源从而导致阻塞  

## 其他的一些性能优化建议

1. 避免创建过多的对象
2. 不要过多使用枚举(枚举占用的内存空间要比整型的大)
3. 常量使用 `static final`来修饰
4. 使用一些Android特有的数据结构,比如`SparseArray`和`Pair`等,它们都具有更好的性能(注:减少了自动装箱和拆箱的消耗)
5. 适当使用软引用和弱引用
6. 尽量采用静态内部类(不会持有外部类的实例)

## 提高可维护性

1. 命名规范
2. 代码排版
3. 给非常关键的代码写注释
4. 代码要有层次性,可扩展  


## 其他的资料推荐  

[10-ways-to-improve-your-android-app](http://www.slideshare.net/seamaster29/10-ways-to-improve-your-android-app)  
[Android性能优化典范 - 第4季](http://hukai.me/android-performance-patterns-season-4/)   
[Android性能优化典范 - 第3季](http://hukai.me/android-performance-patterns-season-3/)  
[Android性能优化典范 - 第2季](http://hukai.me/android-performance-patterns-season-2/)  
[Android性能优化典范 - 第1季](http://hukai.me/android-performance-patterns/)   


