# 《深入理解 Android 卷 1》


## 第3章-深入理解init

- init 进程

- - 是 Linux系统中用户空间(User Space)的第一个进程，也是 Android 系统中用户空间的第一个进程。(进程号是1)

  - 职责

  - - 创建系统关键进程(如 zygote)
    - 初始化并启动属性服务(property service)



- init 工作流程

- - 解析两个配置文件

  - - 一个系统配置文件init.rc
    - 一个是硬件平台相关的配置文件

  - 执行各个阶段的动作,  (在这里创建了 zygote)

  - 初始化属性相关的资源，启动属性服务

  - 进入无限循环，等待并处理来自 socket 和属性服务器的相关事情





启动 zygote



```
service zygote /system/bin/app_process -Xzygote /system/bin -zygote \ --start-system-server
```



<img src="http://ww1.sinaimg.cn/large/98900c07gw1fbftzcgcsuj20go05t3z8.jpg"/>




## 第4章-深入理解zygote

zygote 受精卵

system_server  


### zygote 分析


zygote 是**由 init进程根据 init.rc 文件中的配置项创建的**。

zygote 最初叫 app_process，通过 pctrl 系统将自己的名字换成了 zygote。



app_process 对应 `App_main.cpp`



App_main的 main 方法里调用了 `AppRuntime.start` (AppRuntime.cpp)。



该方法非常的关键，主要做了如下几件事，可以说是为Java 世界准备好了一切。



1. 创建虚拟机 startVm
2. 注册 JNI 函数 startReg  准备 Java 所需要的 native 方法
3. 调用 `ZygoteInit.main`进入 Java 世界



进入 Java 世界的代码 如下：



<img src="http://ww1.sinaimg.cn/large/98900c07gw1fbgxhkg9l0j20gp0b1q4s.jpg"/>

<img src="http://ww4.sinaimg.cn/large/98900c07gw1fbgxih1cwnj20dg09575n.jpg"/>



代码注释：找到 ZygoteInit 调用它的 main 方法，并传入`com.android.internal.os.ZygoteInit`和`true`。

可见 **Java 世界的入口**是 `com.android.internal.os.ZygoteInit`的`main`函数。



### ZygoteInit main流程分析



1. 注册 zygote 用的 socket (zygote 的 IPC 方式是UDS Unix Domain Socket)
2. 预加载类和资源(比如系统自带的类和资源)
3. 启动 system_server 进程(Java 世界系统 Service 的驻留进程，是 framework 的核心进程，如果它死了，会导致 zygote 自杀)
4. runSelectLoop
5. methodAndArgsCaller.run (实际上是 SystemServer.run )



#### 启动 system_server 进程

system_server进程是 Java世界系统 Service 的驻留进程，是 framework 的核心进程，如果它死了，会导致 zygote 自杀。

system_server的启动

1. 从zygote进程 fork 一个子进程(Zygote.forkSystemServer)，
2. handleSystemServerProcess  （system_server 进程的工作）

 PS：pid==0 则为子进程



#### runSelectLoop

zygote  在这里处理客户端的请求，客户用 ZygoteConnection 对象来表示

ZygoteConnection.runOnce来处理请求





### SystemServer 分析

system_server 进程为 zygote 第一个 fork 的进程。







<img src="http://ww2.sinaimg.cn/large/98900c07gw1fbljpte5t2j20eu0743zd.jpg"/>




### 小结



1. 创建第一个 Java 虚拟机
2. 创建 framework 核心 system_server 进程







