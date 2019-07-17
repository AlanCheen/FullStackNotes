# Android 常用的配置









### 配置 Java 8



```groovy
android {
  ...
  // Configure only for each module that uses Java 8
  // language features (either in its source code or
  // through dependencies).
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}

```

![img](http://ww2.sinaimg.cn/large/006tNc79ly1g50j1ds6xuj31140eoab4.jpg)



Android 中的 Java8 支持：

- Lambda 表达式
- 函数引用
- 类型注解
- 默认和静态接口函数
- 重复注解



https://developer.android.com/studio/write/java8-support?hl=zh-CN



### 配置 HttpClient



HttpClient 已经被废弃，但是一些老的库还是会有用，如果要开启则：

```groovy
android {
  useLibrary 'org.apache.http.legacy'
}
```

