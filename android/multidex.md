# Multidex

> 迁移语雀 2021-2-22


App 代码膨胀，可能会遇到问题：



```java
Conversion to Dalvik format failed:
Unable to execute dex: method ID not in [0, 0xffff]: 65536
```



```
trouble writing output:
Too many field references: 131000; max is 65536.
You may try using --multi-dex option.
```



`65536`，它代表的是单个 Dalvik Executable (DEX) 字节码文件内的代码可调用的*引用总数*。



### 关于 64K 引用限制

Android 应用 (APK) 文件包含 [Dalvik](https://source.android.com/devices/tech/dalvik/?hl=zh-cn) Executable (DEX) 文件形式的可执行字节码文件，其中包含用来运行您的应用的已编译代码。Dalvik Executable 规范将可在单个 DEX 文件内可引用的方法总数限制在 65,536，其中包括 Android 框架方法、库方法以及您自己代码中的方法。在计算机科学领域内，术语[*千（简称 K）*](https://en.wikipedia.org/wiki/Kilo-)表示 1024（或 2^10）。由于 65,536 等于 64 X 1024，因此这一限制也称为“64K 引用限制”。



```groovy
android {
    defaultConfig {
        ...
        minSdkVersion 21 
        targetSdkVersion 28
        multiDexEnabled true
    }
    ...
}

dependencies {
  compile 'com.android.support:multidex:1.0.3'
}
```



```java
public class App extends Application {

    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        //install
        MultiDex.install(this);
    }
}
```



### 资料

https://developer.android.com/studio/build/multidex?hl=zh-cn