# Kotlin 基本语法



###  包的声明与导入



Kotlin 的包的声明以及导入都放在源文件的顶部，跟 Java 类似。

```kotlin
package me.yifeiyuan.kotlin

import java.util.*
```



不过跟 Java 不一样的是，目录跟包结构不必匹配，不过我建议还是匹配比较好些。



注： Kotlin 的**一个文件可以声明多个同级的类**，而不像 Java 一个文件一个。



举个例子：

```kotlin
package me.yifeiyuan.learnkotlin

class Foo223{}

class Foo224{}
```



另外，Kotlin 文件名用首字母大写的驼峰，扩展名是`.kt`。





### Kotlin 程序入口 main



kotlin 程序入口也是个 `main` 方法：

```kotlin
fun main(args: Array<String>) {

    println("Hello 程序亦非猿")

}
```



注：在 IDEA 里输入 `main` 就会有提示，不用自己全部输入完。



可以看到 Kotlin 的函数定义用的是 `fun` 关键字。



### 变量



Kotlin 用 `var`、 `val` 关键字来声明变量，var 表示可变，val 表示不可变，声明支持类型推导。

并且变量默认不可空，如果变量可能是空，则在声明的时候就需要**加个问号**。

Kotlin 实例化变量不需要跟 Java 一样写 new ，直接省略 new 即可，例如 Foo()。



```kotlin
var name = "程序亦非猿"
val age : Int = 18
var foo : Foo? //表示可以为空
foo = Foo()//实例化 foo 
foo?.doSth()//如果不为 null 则调用 doSth()
```



### Unit 、Any



在 Java 中方法返回无意义的话用 void ，而 kotlin 用的是 Unit，并且它作为返回值的时候是可以省略的，**个人推荐省略**。



下面两个方法声明的含义是一样的：

```kotlin
fun helloWorld():Unit{
    
}

fun helloWorld(){

}
```



在 Java 中所有的类都有一个超类叫 Object，而在 Kotlin 中是 `Any`。



### Kotlin 的注释



kotlin 也支持单行跟多行注释。

```kotlin
//单行注释

/**
 * 多行注释
 * */
```



### 字符串模板



Kotlin 拥有比 Java 更好用的字符串模板，可以通过 `$` 跟 `${}` 来使用，后者还能支持表达式。



```kotin
fun greeting(name: String){
    println("Hi ${name} !")
}
```



### 条件表达式 & 循环



kotlin 中有 `if` ,`for`，`while` ，少了 `switch case` , 不过有更好用的 `when` , `range`，另外还缺少了三目运算符，可能跟 kotlin 中的冒号有特殊的意思有关吧。



`if` 可以用来做**条件判断**，还可以用来**做表达式**：

```kotlin
if (age>=18){

}else{

}

//赋值
var adult = if (age>=18) "成年人" else "未成年人"

```



`for` 循环：

```kotlin
//for 循环
var lan = listOf<String>("Android","iOS","HTML")

for (l in lan) {
  println(l)
}

//index 来循环
for (index in lan.indices){
  println("第 $index 个元素是 ${lan[index]}")
}
```



`when` 表达式：



```kotlin
fun saySth(obj:Any):String=
        when (obj){
            1 -> "数字 1 啊"
            "Hello" -> "一个字符串"
            is Long -> "是个 Long 类型"
            !is String -> "不是个 String"
            else -> "啥玩意"
        }

saySth(1).also { println(it) }			 //数字 1 啊
saySth("Hello").also { println(it) } //一个字符串
saySth(1L).also { println(it) }			 //是个 Long 类型
saySth(123).also { println(it) } 		 //不是个 String
```



when 表达式可以理解为加强版的 switch case ，



`range` 使用区间：

  

区间只用于数组，可以定义区间，遍历区间，利用区间判断 1 个数字是否在区间内。

还有 `step`、`until`、`downTo` 等用法。

```kotlin
//定义个区间
var foo = 1..4
//遍历
for (f in foo){
    println(f)
}

// 1 3 5 7 9  注意 step 为 2
for (x in 1..10 step 2){
  println(x)
}

// 判断是否在某个区间
val a = 3

if(a !in 1..5){
  println("$a 不在区间内")
}

//更多用法
for(i in 1..100){} //闭区间，包含 100
for(i in 1 until 100){} //半开区间，不包含 100
for(i in 1..100 step 3){} //指定步长为 3，每次增长 3
for(i in 100 downTo 1){}
if(i in 1..100){} //if 表达式
```



