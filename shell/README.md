# Shell 编程

基于 Mac。



### 编写可执行命令



1. 到`bin`目录，`cd /usr/local/bin`
2. 创建文件，`touch foo`
3. 编辑文件内容，`vim foo`
4. 文件内容以`#!/bin/sh`开头
5. 再输入具体命令内容，保存退出
6. 提权，`chmod 777 foo`
7. 完成



### Android



查看当前的 Activity 类名等信息。

```shell
#!/bin/sh
adb shell dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'
```



查看 SurfaceFlinger 信息

```shell
#!/bin/sh
adb shell dumpsys SurfaceFlinger
```



