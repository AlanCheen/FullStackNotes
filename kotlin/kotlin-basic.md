# Kotlin 语法基础





## 变量



### 变量声明

Kotlin 使用两个关键字定义变量：

1. `var`：变量，variable ，可变，可以多次赋值；
2. `val`：值，value，不可变，不可以重新赋值，类似 Java 的 final；

不管哪个关键字，变量的类型的声明写在变量名字后，并且用`：`连接。

```kotlin
var name : String = "程序亦非猿"
name = "Fitz"

val age : Int = 18

var cName = "路飞"
```



另外 Kotlin 变量声明的时候可以根据条件声明：

```kotlin
var age = 17
var adult : String = if(age >=18){
    "成年人"
}else{
    "未成年人"
}
println(adult)
```

conditional expressions，除了 if 还可以使用 `when` 等。

```kotlin
var age = 30

var result = when{
    age >= 60 -> "老年人"
    age >=30 -> "中年人"
    age >=18 -> "成年人"
    else -> "未成年人"
}
println(result)
```



**延迟初始化**

Kotlin 里的变量是需要在初始化赋值的，如果在一开始不能赋值，则需要用`lateinit` 关键字，例如 View 需要在 onCreate 里初始化。

```kotlin
lateinit var text: TextView

onCreate(){
  text = find...
}
```



**Initializer block**，变量的初始化也可以在`init`区块里写：

```kotlin
class LoginFragment : Fragment() {
    val index: Int

    init {
        index = 12
    }
}
```





### 变量的可见性



另外变量的默认可见性是 public 的。

1. 
2. private
3. public



### Null

另外 Kotlin 的变量没有默认值，默认也不会是 null，如果要声明一个可以为 null 的变量需要用`？`。

```kotlin
var name : String? = null

if(name != null){
  //...
}
```



`?`还有很多使用的方式，例如`?.`、`as?`。

```kotlin

name?.trim()//如果不为 null 则调用 trim()

(name as? String)?.trim()

name!!.trim()//如果 name 为 null 则报空指针
```



## 类型

Kotlin 是**静态类型**，运行时不可变。

```kotlin
var name : String = "Foo"
name = 18 //不可以！
```



### 常用类型



1. String
2. Int
3. Float
4. Double
5. Long
6. Short
7. Byte
8. Boolean



### 数组

跟 Java 的数组不一样，kotlin 的数组用以下类表示， 并且实例化也用类似 `intArrayOf()` 的方法，数组的长度用`.size`获取。



1. IntArray
2. DoubleArray
3. FloatArray
4. LongArray
5. CharArray
6. ShortArray
7. ByteArray
8. BooleanArray



并且提供以下属性方法：

1. get
2. set
3. size



```kotlin
var intArray: IntArray = intArrayOf(1, 2, 3)

var floatArray: FloatArray = floatArrayOf(1.0f, 2.0f)

println(intArray.size)
```



拿 IntArray 举个例子，看下源码：

```kotlin
public class IntArray(size: Int) { 
    public inline constructor(size: Int, init: (Int) -> Int)
 
    public operator fun get(index: Int): Int 
    public operator fun set(index: Int, value: Int): Unit
 
    public val size: Int
 
    public operator fun iterator(): IntIterator
}
```



### 类型推断

Kotlin 支持类型推断，使得在声明变量时不写变量的类型也行。

```kotlin
var name = "程序亦非猿" //name 是个 String
```



### 类型判断

Java 中用 instanceof ，而 Kotlin 中用的是`is`。

```kotlin
if (foo is Foo){
  foo.foo()
}
```



### **类型强转**

类型强转用`as`关键字，没有 (Foo)foo 这种语法了。

```kotlin
var name = foo as TextView
```



## 函数



关键字`fun`来声明一个函数（function），跟 Java 的 method 的含义差不多。

```kotlin
fun greeting(name: String){
  
}
```



Kotlin 中没有返回值用 `Unit`，类似 Java 中的 `void` ，并且 Unit 可以省略。

```kotlin
//等同于
fun greeting(name: String): Unit {}
```



## 日志打印

kotlin	里打印日志不是 `Log.d` 了，而是`println`等方法。

并且可以通过`${variable}`来直接引用变量的值，很方便。

```kotlin
var name = "程序亦非猿"
println("Hello ${name}")
```







## Kotlin 特性要点



`：`，kotlin 里表达继承、实现，不用`extends`和 `implement` 而是`：`。

```kotlin
class LoginFragment : Fragment()，FooInterface {}
```



`？`，默认情况下，kotlin 的变量都是**不可以为空的**，如果一个变量可能为空，则需要加上`？`才行。

```kotlin
var name:String ? = null
```



`!!` ，会认为左边的表达式为**非空**，如果实际结果为空，则会抛出空指针异常，要慎用。

```kotlin
account.name!!.trim()
```



`?.`，如果左边表达式不为空，则会执行右边的操作，如果为空则忽略，不会抛空异常。

```kotlin
account.name?.trim()
```



**延迟初始化**

kotlin 申明变量的同时就需要初始化它，如果要延迟初始化，则需要使用`lateinit` ，它允许我们不在一个对象被构造时完成初始化。

如果在初始化前访问变量，则会抛出 UninitializedPropertyAccessException 异常。

```kotlin
private lateinit var titlte : TextView
```



**SAM conversion** (Single Abstract Method conversion)

SAM 指类似 OnClickListener 这种只有一个抽象方法，它的实现可以被替换为一个匿名函数，也叫 SAM conversion。

```kotlin
button.setOnClickListener{
  //...
}
```



**Companion objects**

kotlin 中没有 Java 的 `static`关键字 ，而是用一个**伴生对象**来实现类似 static 的功能。

```kotlin
class Foo{
  
  companion object{
    private const val TAG="Foo"
  }
}
```

