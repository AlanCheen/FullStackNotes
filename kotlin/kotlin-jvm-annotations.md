# Kotlin Jvm Annotation


为了跟 Java 有更好的互操作性而定义的注解：

- JvmDefault
- JvmField
- JvmMultifileClass
- JvmName
- JvmOverloads
- JvmStatic
- ...


## JvmOverloads
> Instructs the Kotlin compiler to generate overloads for this function that substitute default parameter values.

If a method has N parameters and M of which have default values, M overloads are generated: the first one takes N-1 parameters (all but the last one that takes a default value), the second takes N-2 parameters, and so on.

指示 kotlin 编译器为带默认参数的函数生成重载函数。如果一个方法有N个参数，其中M个具有默认值，则会生成M个重载：第一个重载使用N-1个参数（除了最后一个具有默认值），第二个重载N-2个参数，依此类推。


适用于：
- 构造方法
- 一般方法
- 静态方法

不适用于：
- 抽象方法

因为抽象方法没有方法体。


需要注意，在实践过程中发现，该注解不能作用于抽象方法，所以不能用于接口里定义的方法，也不能作用于抽象类里的抽象方法。

针对 Java 调用 kotlin 的情况，只能在类里定义方法了。


## 资料

[Annotation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-annotation.html)