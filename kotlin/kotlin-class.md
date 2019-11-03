# Kotlin 里的类





## 类的构造



构造方法

init





## 类的继承与接口实现



默认情况下，一个 Kotlin 的类是不能被继承的：



如下的代码会报错：`Error:(12, 17) Kotlin: This type is final, so it cannot be inherited from`。

```kotlin
class Person{

}

class Student : Person(){

}
```

如果要一个类要可以被继承，则需要加上`open`关键字。

```kotlin
open class Person{}
```



## 类的可见性



## 内部类