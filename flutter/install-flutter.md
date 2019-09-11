# 安装配置 Flutter

[TOC]



文基于 MacOS。



### 下载 Flutter SDK 并配置环境变量

去 https://flutter.cn/docs/development/tools/sdk/releases?tab=macos 下载最近的 sdk，放到某个目录。



然后解压，并配置环境变量。



```shell
 export PATH="$PATH:`pwd`/flutter/bin"
 source ~/.zshrc  #替换你自己的 shell 配置文件路径
```



Flutter 依赖一些其他的工具，可以运行 `flutter doctor` 命令来查看依赖是否完善。



拿我的电脑举例：

```shell
➜  flutter git:(stable) flutter doctor
Doctor summary (to see all details, run flutter doctor -v):
[✓] Flutter (Channel stable, v1.9.1+hotfix.2, on Mac OS X 10.14 18A391, locale
    zh-Hans-CN)

[!] Android toolchain - develop for Android devices (Android SDK version 28.0.3)
    ! Some Android licenses not accepted.  To resolve this, run: flutter doctor
      --android-licenses
[!] Xcode - develop for iOS and macOS (Xcode 10.1)
    ! CocoaPods out of date (1.6.0 is recommended).
        CocoaPods is used to retrieve the iOS and macOS platform side's plugin
        code that responds to your plugin usage on the Dart side.
        Without CocoaPods, plugins will not work on iOS or macOS.
        For more info, see https://flutter.dev/platform-plugins
      To upgrade:
        sudo gem install cocoapods
        pod setup
[!] Android Studio (version 3.5)
    ✗ Flutter plugin not installed; this adds Flutter specific functionality.
    ✗ Dart plugin not installed; this adds Dart specific functionality.
[✓] VS Code (version 1.33.0)
[!] Connected device
    ! No devices available

! Doctor found issues in 8 categories.
```



doctor 检查出了几个问题：



**问题 1**：Some Android licenses not accepted.  To resolve this, run:

```shell
flutter doctor --android-licenses
```

然后按提示输入 `y`，直到所有的 licenses 都被 ac，到最后展示成这样。

```shell
Accept? (y/N): y
All SDK package licenses accepted
```



**问题 2**：CocoaPods out of date (1.6.0 is recommended). To upgrade:

```shell
sudo gem install cocoapods
pod setup
```



第一步会安装 cocoapods  各种工具，第二步骤会 Setting up CocoaPods master repo。

耐心等待即可。

最后看到这个就算对了：

```shell
CocoaPods 1.8.0.beta.2 is available.
To update use: `sudo gem install cocoapods --pre`
[!] This is a test version we'd love you to try.

For more information, see https://blog.cocoapods.org and the CHANGELOG for this version at https://github.com/CocoaPods/CocoaPods/releases/tag/1.8.0.beta.2

Setup completed
```



**问题 3**：

✗ Flutter plugin not installed; this adds Flutter specific functionality.
✗ Dart plugin not installed; this adds Dart specific functionality.

解法，在 AS 安装一下 Flutter 插件。

![image-20190911153957300](https://tva1.sinaimg.cn/large/006y8mN6ly1g6vmc6nzeij30q00aetch.jpg)





再次验证：

```shell
➜  ~ flutter doctor
Doctor summary (to see all details, run flutter doctor -v):
[✓] Flutter (Channel stable, v1.9.1+hotfix.2, on Mac OS X 10.14 18A391, locale
    zh-Hans-CN)

[✓] Android toolchain - develop for Android devices (Android SDK version 28.0.3)
[✓] Xcode - develop for iOS and macOS (Xcode 10.1)
[✓] Android Studio (version 3.5)
[✓] VS Code (version 1.33.0)
[✓] Connected device (1 available)
```



必须的都已经 OK 了。



### 配置 iOS 开发环境



1. 安装 Xcode，在 AppStore 下载即可，要求 9.0 及以上
2. 配置 Xcode command-line tools :` sudo xcode-select --switch /Applications/Xcode.app/Contents/Developer`
3. 运行一次 Xocde 或者通过输入命令 `sudo xcodebuild -license` 来确保已经同意 Xcode 的许可协议。



如果一起下载过 Xcode 应该问题不大。



然后启动一个 iPhone5S 之后版本的模拟器，我选了 XS。

```shell
open -a Simulator
```



创建一个 Flutter 工程，并部署到模拟器。

```
flutter create helloflutter
cd helloflutter
flutter run
```

执行上面三个命令就可以创建并部署到模拟器。



运行日志：

```shell
➜  helloflutter flutter run
Launching lib/main.dart on iPhone XS in debug mode...
Running Xcode build...

 ├─Assembling Flutter resources...                           8.0s
 └─Compiling, linking and signing...                        15.2s
Xcode build done.                                           26.1s
Syncing files to device iPhone XS...
 2,387ms (!)

🔥  To hot reload changes while running, press "r". To hot restart (and rebuild
state), press "R".
An Observatory debugger and profiler on iPhone XS is available at:
http://127.0.0.1:49877/1KhXC2nzaFc=/
For a more detailed help message, press "h". To detach, press "d"; to quit,
press "q".
```





如图：

<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6vmpmsjaaj30b80m2wg6.jpg" alt="image-20190911155254932" style="zoom:50%;" />





**注意：**如果要部署到真机，还需要开发者账号，我没有就没试。



### 配置 Android 开发环境



Android 也有模拟器跟真机的选择，鉴于 Android 模拟器的性能，我选择了真机。

真机跟普通的 Android 开发差不多，开启开发者模式，允许 USB debugging 即可。

然后运行`flutter devices`命令确保 Flutter 可以识别我们的设备。

同 iOS，运行 flutter run 部署到 Android 手机上。



```shell
➜  helloflutter flutter run -d 91ab18e5
Launching lib/main.dart on MI NOTE LTE in debug mode...
Initializing gradle...                                             17.3s
Resolving dependencies...                                          81.4s
Running Gradle task 'assembleDebug'...
Running Gradle task 'assembleDebug'... Done                        43.2s
Built build/app/outputs/apk/debug/app-debug.apk.
Installing build/app/outputs/apk/app.apk...                        18.5s
Syncing files to device MI NOTE LTE...
 2,104ms (!)

🔥  To hot reload changes while running, press "r". To hot restart (and rebuild
state), press "R".
An Observatory debugger and profiler on MI NOTE LTE is available at:
http://127.0.0.1:50410/wvUWXqgNfBE=/
For a more detailed help message, press "h". To detach, press "d"; to quit,
press "q".
```



启动截图：

<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6vmzw9gnej30910g1aav.jpg" alt="image-20190911160247362" style="zoom:50%;" />





**注：**当电脑连着多个设备或模拟器时，flutter run 可以通过 -d deviceId 来指定要 run 到哪个设备，例如`flutter run -d 91ab18e5`，或者`flutter run -d all` 部署到所有的设备。



### 配置 Web 开发环境



处于技术预览期。



### 资料

https://flutter.cn/docs/get-started/install/macos