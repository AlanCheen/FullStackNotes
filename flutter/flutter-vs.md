# 使用 VS  体验 Flutter 

[TOC]



Flutter 目前支持 Android Studio/Intellij/Visual Studio Code/终端&文本编辑器 多种方式来编写， AS 我已经非常熟悉了，为了挑战下自己，学一下 VS Code，指不定以后写前端还用得着，所以综合考虑下我选择了使用 VS Code 来学习 Flutter 开发。



本文分享使用 VS Code 开发 Flutter 的初体验。



### 安装 Flutter 和 Dart 插件。



1. View->Command Palette..
2. Install -> install extensions...
3. 搜索 Flutter 并安装
4. 重启 VS 



如图：

![image-20190911161411730](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vnbrrf7bj30ps09hn47.jpg)



运行 flutter doctor:

打开 Command Palette 输入 doctor , 选择 Run Flutter Doctor :

![image-20190911161503414](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vncnujuzj30ht02sq3c.jpg)



我遇到个问题：

![image-20190911161820463](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vng37b72j30ck033myc.jpg)





这个比较奇怪，我明明配置了环境变量但是还是提示我找不到，看了下 Show Log 发现跟我配置的不太对，东西少了很多，想了想可能是因为我没有配置VS 的终端，它默认用的是 bash 不是 zsh，所以没找到我配置在 zsh 的 flutter。



于是我尝试把 flutter 配置到 bash_profile ，然后发现就行了，我真 TM 是个机智鬼。

```shell
vim .bash_profile 
source .bash_profile
```



再执行上面的操作，运行的结果跟在终端运行 flutter doctor 是一样的。



打开最开始创建的 helloflutter 项目，执行 flutter run 就能运行起来啦！



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6vof6itszj314k0qp7n1.jpg" alt="image-20190911165203855" style="zoom:50%;" />



到此 VS 的 Flutter 基本开发环境算是配置好了。



试着修改了下 lib/main.dart 文件，然后在终端输入 `r` ，可以看到可以立马更新到模拟器上，很是快捷。



### 开发初体验



#### 创建 App

不用之前创建的工程，用 VS Code 也是可以创建工程的。



在 command palette 输入 flutter , 找到 `New Project` 并执行，输入工程名后会自动创建工程。



![image-20190911233033048](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vzxt8lbsj30xq0juwn9.jpg)



这里工程名我输入了 fluttervscode ，自动生成的项目：

<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6w01xq145j31400u07wh.jpg" alt="image-20190911233430666" style="zoom:50%;" />



然后切换到终端，执行 `flutter run` ，就能跑起项目来啦！（我开着 iOS 模拟器）



在 VS 右下角有个状态栏，展示了一些基本信息，也可以切换设备。

如图：

![image-20190911234656908](https://tva1.sinaimg.cn/large/006y8mN6ly1g6w0ev80gtj316601oq4h.jpg)



#### 调试模式

菜单栏 Debug -> Start Debugging 可开启 Debug，初次使用会让你激活一些插件，开启即可。



![image-20190911234522760](https://tva1.sinaimg.cn/large/006y8mN6ly1g6w0d8o6r4j30pc02oaav.jpg)



当一切就绪，可以通过底部打开一个`Dart DevTools`的网页，展示一些 App 的信息：



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6w0hinzlhj31ok0u0tg2.jpg" alt="image-20190911234929824" style="zoom:50%;" />



可以看到展示了类似视图结构等信息，具体等以后深入了解，这里就不多写了。



此外 VSCode 还会出现个 debug 的工具栏，以帮助我们开发测试：

![image-20190911235103332](https://tva1.sinaimg.cn/large/006y8mN6ly1g6w0j5tmbmj318s03kwgt.jpg)



#### Hot reload



尝试修改 Demo 里的 Text 组件，command + S 保存, 这修改就能直接表现到模拟器上。（或者点击那个闪电）

![a-2019-09-11 23_56_37](https://tva1.sinaimg.cn/large/006y8mN6ly1g6w0puzre0g30ec092gmx.gif)



至此，VS Code 开发 Flutter 初体验完毕。



### 资料

[编辑工具设定](https://flutter.cn/docs/get-started/editor?tab=vscode)



