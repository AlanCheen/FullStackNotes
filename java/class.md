# Class



Class，类，Class 实例表示 Java 中的类和接口。

枚举、数组、基础类型、关键字`void` 都表示 **Class 对象**，注解表示**接口**。



Class 没有公开的构造方法，**Class 对象**是由 JVM 调用 ClassLoader (比如 BootstrapClassLoader) 的 `definClass()` 方法来构建的。



Class 对象也是一个对象，比如 `View.class` ，是一个**特殊对象**，表示这个类，某些场景下也能代表这个类的所有实例。



### 反射

通常我们会在使用反射的时候用到 Class 。



比如我们要获取一个 Class 对象：

```java
Class clazz = Class.forName("java.lang.Thread")
```



- getDeclaredMethod
- ...



### 并发

并发的某些场景下，我们会锁 Class 对象。

