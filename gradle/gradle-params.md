# Gradle 传递 参数





- `-P`
- `-D`



通过命令行传递 String 类型的参数时，需要转义符号：

```shell
 ./gradlew assembleDebug -Dcv="\"字符串需要转义符号\""
```



在 build.gradle 里可以这样接受参数：

```groovy
String customValue = System.properties.getProperty("cv")
```





### BuildConfig 参数配置



```groovy
android {
    defaultConfig {
        buildConfigField("String", "Author", "\"程序亦非猿\"")
    }
}
```

在 BuildConfig 类中会生成一个属性：

```java
public static final String Author = "程序亦非猿";
```

