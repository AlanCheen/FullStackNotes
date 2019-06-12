# ClassLoader

[TOC]

### 概述



ClassLoader ， 类加载器，负责加载类，给定一个`binary name` 就可以加载一个类。



ClassLoader 使用委托模式(delegation)来查找类，也就是常说的*"双亲委派"*。

每个 ClassLoader 都有一个关联的父 ClassLoader。当尝试去找一个 Class 时，会先去 parent 那边找，找不到再自己找。



一般来说 JVM 从平台的本地文件系统中加载类，但也有一些是从网络下载下来的，方法 defineClass 能将字节数组转换为 Class 的实例。



VM 内置的类加载器叫做"bootstrap class loader" ，它没有父加载器，但是可以作为别的加载器的 parent。



### Class name 的规范



class name 是有规范的，不能随便乱写，必须是一个`binary name`。

具体表现就是：一定要**写全类名**，如果是**内部类**则需要用`$`来表示。

符合规范的有如下几种：

- `java.lang.String` 
- `javax.swing.JSpinner$DefaultEditor`
- `java.security.KeyStore$Builder$FileBuilder$1`
- `java.net.URLClassLoader$3$1`



### loadClass 的过程



一个 class 的加载过程。

```java
//ClassLoader
protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            try {
                if (parent != null) {
                	  //从 parent load
                    c = parent.loadClass(name, false);
                } else {
                   //在 bootstrap 找
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                c = findClass(name);
            }
        }
        return c;
}

protected final Class<?> findLoadedClass(String name) {
    ClassLoader loader;
    if (this == BootClassLoader.getInstance())
        loader = null;
    else
        loader = this;
    return VMClassLoader.findLoadedClass(loader, name);
}
```



loadClass 的步骤：

1. 先查找是否已经加载过该 Class
2. 从父加载器找是否已经加载
3. 从bootstrap 找
4. 自己 findClass



总的来说就是：**有事先找爸爸，爸爸不行找爷爷，再不行找老祖先，实在没辙再自己出马**。



### 类加载的五大步骤



TODO 加载 分析 初始化。。。



### ClassLoader 隔离



*不同的 ClassLoader 加载的类是相互隔离的*。



是否是同一个类的判断条件为：类的名称、包名、ClassLoader 都相同才算是同一个类。



### 类加载的时机



一个类被加载是有条件的， 我们不可能一下子把程序的所有类都加载到内存。



那么加载类的时机是什么呢？换句话说，一个 Java 类何时会被加载？



1. 遇到 new getstatic putstatic 等指令时
2. 对类进行反射调用的时候
3. 初始化某各个类的子类的时候
4. 虚拟机启动时会先加载的程序主类
5. 使用 JDK1.7 的动态语言支持的时候



概括起来其实就是：*当运行过程中需要这个类的时候*。(好像是废话)



### 资料

https://www.atatech.org/articles/136718?flag_data_from=home_manual