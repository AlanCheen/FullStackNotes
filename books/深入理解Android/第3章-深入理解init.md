# 第3章-深入理解init

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

