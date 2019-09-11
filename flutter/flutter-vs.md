# VS 配置 Flutter 



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



![image-20190911165203855](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vof6itszj314k0qp7n1.jpg)



到此 VS 的 Flutter 基本开发环境算是配置好了。



试着修改了下 lib/main.dart 文件，然后在终端输入 r ，可以看到可以立马更新到模拟器上，很是快捷。



### 资料

[编辑工具设定](https://flutter.cn/docs/get-started/editor?tab=vscode)



