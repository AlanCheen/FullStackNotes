# 《Head First Java 》

[TOC]

> 本文 GitHub 地址：

### 1 基本概念

​	源代码
​	编译器
​	虚拟机

### 2 类与对象

​	

- 对象是靠类的模型塑造出来的
  		对象是已知的事物——实例变量
    		对象会执行的动作——方法
- 类是对象的蓝图
  		类不是对象，却是用来创建它们的模型 



### 3 primitive 主数据类型和引用 

​	

- 基本类型
-  引用类型，保存对象的引用
- ​	数组也是对象
- ​	对象放在堆里生存



### 4 方法操作实例变量

​	封装
​	*实例变量*声明在类内部（成员变量）
​	*局部变量*声明在方法内，使用前必须初始化



### 5 编写程序

### 6 认识 Java 的 API



### 7 继承与多态

​	在掌握多态之前，我们的薪水少的可怜。每天又得加班赶工。
​	继承概念下的 IS-A 是单向关系。

### 8 接口与抽象





### 9 构造器与垃圾收集器

​	

#### 内存的两种区域

​		堆 heap
​			对象
​		栈 stack
​			方法调用和局部变量
​	

#### 对象的生命周期

- 对象的引用没了，就会死亡
- 局部变量只会存活在声明的方法中
- 实例变量的寿命跟对象相同，对象活着它就还活着
  	

#### 释放对象引用的 3 个方式



1️⃣引用永久性的离开它的范围：

```java
void go(){
  Life z = new Life(); //z 会在方法结束时消失
}
```

z 会在方法结束时消失。



2️⃣引用被复制到其他的对象上：

```java
Life z = new Life();//第一个对象会在 z 被赋值到别处时挂掉
z = new Life();
```

第一个对象会在 z 被赋值到别处时挂掉。



3️⃣直接将引用设定为 null：

```java
Life z = new Life();//
z = null;
```

第一个对象会在 z 被赋值为 null 时击毙。



#### null 的真相

![image-20190617161804907](/Users/mingjue/self/FullStackNotes/books/assets/image-20190617161804907.png)



null 只是把引用清空了而已。

### 10 数字与静态



- **静态方法**
  		静态方法不能调用非静态变量
  		静态方法也不能调用非静态方法
- **静态变量**
  		静态变量的值对所有的实例来说都是相同的
  		静态变量是共享的，同一类所有的实例共享一份静态变量
  		静态变量是在类被加载时初始化的。
  		静态变量会在该类的任何静态方法执行之前就初始化。
- **静态的 final 变量是常数**
- **静态初始化程序**（`static initializer`）是一段在加载类时会执行的程序代码，它会在其他程序可以使用该类之前就执行，所以释放放静态 final 变量的起始程序。（static 代码块）



### 11 异常处理

### 12 图形用户接口

### 13 Swing

### 14 序列化和文件的输入/输出

​	`Serializable`
​	如果某实例变量不能或不应该被序列化，就把它标记为 `transient`（瞬时）



### 15 网络与线程



socket bufferreader

### 16 集合与泛型

- 数据结构
  - ArrayList 
  - TreeSet，以有序状态保持并可防止重复 
  - HashMap，可用成对的 name/value 来保存与取出 
  - LinkedList，针对经常插入或删除中间元素所设计的高效率集合。 
  - HashSet，防止重复的集合，可快速地找寻相符的元素 
  - LinkedHashMap，类似 HashMap，但可记住元素插入的元素，也可以设定成依照元素上次存取的先后来排序。
- 分类
  - List 知道索引
  - Set 不允许重复
  - Map 使用键值对，key 不能重复
- 泛型
  		更好的类型安全性



### 17 包、jar存档文件和部署



```shell
//将 java 文件编译后的 class 文件放到指定目录
javac -d directoryName javaName

javac -d class HelloWorld.java

//执行 .class 文件(不需要后缀)
java HelloWorld
```



#### 构建一个可执行的 jar 文件

**基本步骤**：

1. 新建一个清单文件 `manifest.txt` ；
2. 在清单文件里添加一行 `Main-Class:[类名]`，告诉 JVM 入口，比如`Main-Class:HelloWorld`, 这里要添加的类是拥有`main`方法的类；
3. 把 class 文件跟清单文件一起打包成 jar `jar -cvmf manifest.txt [jarName] [class]`
4. 执行 jar，`java -jar jarName`

举例：

![image-20190619113825785](/Users/mingjue/self/FullStackNotes/books/assets/image-20190619113825785.png)



JVM 能从 jar 里找到 `main`方法，然后执行它。



##### 有包名的类，编译成 Jar

如果是有包名的，则需要指定包名，举个例子：

Greeting.java

```java
package com.fyf;

public class Greeting{
	public static void main(String[]args){
		System.out.println("Greeting");
	}
}
```



manifest.txt，需要加上包名

```
Main-Class: com.fyf.Greeting
```



编译成 class 文件：

```java
javac -d class Greeting.java
```



打包 jar，也需要注意包名:

```shell
jar -cvmf manifest.txt gr.jar ./com/fyf/Greeting.class
```



运行 jar :

```
➜  class java -jar gr.jar
Greeting
```



class 目录下的产物：

```
➜  class ll
total 16
drwxr-xr-x  3 mingjue  staff   96  6 19 11:48 com
-rw-r--r--  1 mingjue  staff  794  6 19 11:50 gr.jar
-rw-r--r--  1 mingjue  staff   29  6 19 11:50 manifest.txt
```



#### 条列解压的 jar 命令

`-tf` （table file），能够列出文件的列表。



拿上面的 greeting 举例：

```shell
➜  class jar -tf gr.jar
META-INF/
META-INF/MANIFEST.MF
com/fyf/Greeting.class
```



`META-INF` 代表 meta informatin 存放元数据，我们新建的 manifest 文件**不会**被带进 jar，但是它的内容会放进 `META-INF/MANIFEST.MF` 文件中。



`-xf`  类似 unzip 命令，能够提取 META-INF 文件

```
jar -xf gr.jar
```



`META-INF/MANIFEST.MF` 的信息：

```
Manifest-Version: 1.0
Created-By: 1.8.0_121 (Oracle Corporation)
Main-Class: com.fyf.Greeting
```



### 18 远程部署的 RMI



`RMI`：远程调用程序，Remote Method Invocation。



一般来说对象方法的调用都是在相同的 JVM 上进行的，不同的JVM 不能互相获取对方堆上的对象，这使得不能直接互相使用。



#### 如何调用不同机器上的对象的方法呢？

由于不在同一个 JVM 上，所以不能取得堆上的引用。

so how?



- **服务器**：server 是个远程服务，它带有客户端会调用的方法的对象；
- **客户端**：client 客户端
- **服务器辅助设施**：Skeleton， 一个代理对象
- **客户端辅助设施**：Stub， 一个代理对象，假装成远程的服务对象，

![image-20190619232514235](/Users/mingjue/self/FullStackNotes/books/assets/image-20190619232514235.png)

代理会**处理客户端和服务器的底层网络输入输出细节**，让你的客户端和程序好像在处理本机调用一样。它实际上是个**执行通信的对象**。

代理对象会伪装成服务器对象，让客户端以为它调用的是远程的服务。

客户端通过 RMI registry 获取 Stub 对象。

原理跟 Binder 实现 RPC 非常相似啊~



谁用到了 RMI ？：

-  Servlet
- EJB（Enterprise JavaBeans），J2EE
  		适合并行远程服务
- Jini
- ...

