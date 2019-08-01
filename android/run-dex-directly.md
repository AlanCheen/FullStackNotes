# 在手机直接运行 Dex



### 步骤

1. 编写 Java 文件
   1. vim Hello.java
2. 生成 class 文件
   1. javac Hello.java
3. 生成 dex 文件
   1. `$ANDROID_HOME/build-tools/28.0.3/dx --dex --output=classes.dex Hello.class`
4. push 到手机 sdcard
   1. adb push classes /sdcard/
5. 到 sdcard 执行 dex
   1. adb shell
   2. cd sdcard
   3. `dalvikvm -cp classes.dex [className]` ，-cp 是 classpath 的意思，className 是类名



注： dx 工具可以用来生成 dex 文件





### 实战记录



vim Hello.java ：

```java
public class Hello{
public static void main(String[]args){
System.out.println("Hello world!");
}}
```

```shell
javac Hello.java

$ANDROID_HOME/build-tools/28.0.3/dx --dex --output=classes.dex Hello.class
```



![image-20190801113216671](http://ww3.sinaimg.cn/large/006tNc79ly1g5k0rvqwalj309m03qjrc.jpg)





![image-20190801114208679](http://ww2.sinaimg.cn/large/006tNc79ly1g5k122jr2zj30c304eq3u.jpg)





如果故意写错 Dex 或者类路径，则会遇到这个错误：

![image-20190801114551245](http://ww1.sinaimg.cn/large/006tNc79ly1g5k15xqol0j30j90ckq7r.jpg)



令人熟悉的 BaseDexClassLoader，吼吼。

