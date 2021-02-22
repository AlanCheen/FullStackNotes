# Android 中的 ClassLoader

> 迁移语雀 2021-2-22

[TOC]

> 源码基于 Android 9.0



[类加载流程](./class-load-flow.md)

### 概述



插件化、热修复等高薪技术都涉及到了 ClassLoader 知识，不学一下不行了。



要注意的是，Android 运行在 DVM，跟 JVM 是不同的，**Android 平台上的 ClassLoader 跟 Java 的 CLassLoader 也是不一样的**，Android 上的 ClassLoader **只能识别 dex 文件**，而不能是 class 文件了， 不过双亲委派的机制思想还是一样的。



注：解压一个 Apk，我们可以看到一个或者多个 `classes.dex` 文件，Android 的 ClassLoader 就是加载的它们。



### Android 平台的 ClassLoader



Android 系统自带 ClassLoader，当然我们也可以自定义 ClassLoader。



Android 平台的**系统 ClassLoader** 有如下几种：

- `BootClassLoader`
- `DexClassLoader`

- `PathClassLoader`
- `BaseDexClassLoader`



注：都在 `/libcore/dalvik/src/main/java/dalvik/system/`目录下。



类图如下：

<img src="https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1559788402410-e2eccffc-5989-4c7f-889c-843d4cbbd4d1.jpeg" width="533" height="478"/>



#### BootClassLoader



ClassLoader 的一个内部类，是 Android 系统启动时用来预加载常用类的，是其他 ClassLoader 的老祖先，同时是个单例。



#### [DexClassLoader](http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/DexClassLoader.java)

> A class loader that loads classes from `.jar` and `.apk` files containing a `classes.dex` entry. This can be used to execute code not installed as part of an application.



DexClassLoader 可以从**应用外部**加载 `.dex` 文件，比如从 SD 卡的`.jar`跟`.apk`文件里加载`.dex` 文件，可以用来做**扩展**功能，可以执行未安装的代码。



API 26 起，它要求一个 application-private , writable 目录，用来缓存优化过的 classes，官方建议不要把 odex 放入外部存储卡，以避免外部注入攻击。

该目录可以通过`File dexOutputDir = context.getCodeCacheDir()` 来获取。



DexClassLoader 的代码只有构造器，并且调用了 super，得看父类。

```java
public class DexClassLoader extends BaseDexClassLoader {
    /**
     * Creates a {@code DexClassLoader} that finds interpreted and native
     * code.  Interpreted classes are found in a set of DEX files contained
     * in Jar or APK files.
     */
    public DexClassLoader(String dexPath, String optimizedDirectory,
            String librarySearchPath, ClassLoader parent) {
        super(dexPath, null, librarySearchPath, parent);
    }
}
```



注意到 optimizedDirectory 虽然传递过来了，但是没有使用，而是传递了 null 给父类。



#### [PathClassLoader](http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/PathClassLoader.java)

> Provides a simple `ClassLoader` implementation that operates on a list of files and directories in the local file system, but does not attempt to load classes from the network. Android uses this class for its system class loader and for its application class loader(s).



PathClassLoader 继承自 BaseDexClassLoader ，是一个简单的类加载实现，它的设计本意是**用来加载本地文件系统的类，但是并没有意图去加载从网络加载类**。



在 Android 上它被用来作为「**系统类加载器**」(system class loader)，用来加载应用的类，比如 Activity 就是它加载的，它的 parent 是 BootClassLoader。

在 Android 里通过 `ClassLoader.getSystemClassLoader()` 方法获取的就是 PathClassLoader。



它还可以用来加载已经安装的 Apk，也就是`/data/app/package` 下的apk文件，也可以加载`/vendor/lib`、`/system/lib`下的 nativeLibrary 。



PathClassLoader 也只有构造方法，不过它有两个，它的实现也都在 BaseDexClassLoader，代码如下。

```java
public class PathClassLoader extends BaseDexClassLoader {
    /**
     * Creates a {@code PathClassLoader} that operates on a given list of files
     * and directories. This method is equivalent to calling
     * {@link #PathClassLoader(String, String, ClassLoader)} with a
     * {@code null} value for the second argument (see description there).
     */
    public PathClassLoader(String dexPath, ClassLoader parent) {
        super(dexPath, null, null, parent);
    }

    /**
     * Creates a {@code PathClassLoader} that operates on two given
     * lists of files and directories. The entries of the first list
     * should be one of the following:
     *
     * <ul>
     * <li>JAR/ZIP/APK files, possibly containing a "classes.dex" file as
     * well as arbitrary resources.
     * <li>Raw ".dex" files (not inside a zip file).
     * </ul>
     *
     * The entries of the second list should be directories containing
     * native library files.
     */
    public PathClassLoader(String dexPath, String librarySearchPath, ClassLoader parent) {
        super(dexPath, null, librarySearchPath, parent);
    }
}
```



第一个构造方法只支持加载 `dex` 文件 , 第二个则可以支持加载 `so` 文件，其实就相当于把功能拆分了一下而已。



native library 应该指的是 so 库。

dexPath 可以是：

- JAR/ZIP/APK 文件，里面可能包含了 `classes.dex` 
- 直接的`.dex`文件





我怎么看不出 DexClassLoader 跟 PathClassLoader 有什么差别？？？？



#### [BaseDexClassLoader](http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java)



 它是 DexClassLoader 和 PathClassLoader 的基类，实际上类加载的逻辑都写在了这个类里。



BaseDexClassLoader 源码如下：

```java
public class BaseDexClassLoader extends ClassLoader {

    private final DexPathList pathList;

    /**
     * Constructs an instance.
     * Note that all the *.jar and *.apk files from {@code dexPath} might be
     * first extracted in-memory before the code is loaded. This can be avoided
     * by passing raw dex files (*.dex) in the {@code dexPath}.
     *
     * @param dexPath the list of jar/apk files containing classes and
     * resources, delimited by {@code File.pathSeparator}, which
     * defaults to {@code ":"} on Android.
     * @param optimizedDirectory this parameter is deprecated and has no effect since API level 26.
     * @param librarySearchPath the list of directories containing native
     * libraries, delimited by {@code File.pathSeparator}; may be
     * {@code null}
     * @param parent the parent class loader
     */
    public BaseDexClassLoader(String dexPath, File optimizedDirectory,
            String librarySearchPath, ClassLoader parent) {
        this(dexPath, optimizedDirectory, librarySearchPath, parent, false);
    }

    /**
     * @hide
     */
    public BaseDexClassLoader(String dexPath, File optimizedDirectory,
            String librarySearchPath, ClassLoader parent, boolean isTrusted) {
        super(parent);
        //这里是重点 DexPathList
        this.pathList = new DexPathList(this, dexPath, librarySearchPath, null, isTrusted);

        if (reporter != null) {
            reportClassLoaderChain();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        List<Throwable> suppressedExceptions = new ArrayList<Throwable>();
        //会通过 pathList.findClass 去找一个类
        Class c = pathList.findClass(name, suppressedExceptions);
        if (c == null) {
            ClassNotFoundException cnfe = new ClassNotFoundException(
                    "Didn't find class \"" + name + "\" on path: " + pathList);
            for (Throwable t : suppressedExceptions) {
                cnfe.addSuppressed(t);
            }
            throw cnfe;
        }
        return c;
    }
  
    public void addDexPath(String dexPath) {
        addDexPath(dexPath, false /*isTrusted*/);
    }
  
    public void addDexPath(String dexPath, boolean isTrusted) {
        pathList.addDexPath(dexPath, null /*optimizedDirectory*/, isTrusted);
    }

    /**
     * Adds additional native paths for consideration in subsequent calls to
     * {@link #findLibrary(String)}
     * @hide
     */
    public void addNativePath(Collection<String> libPaths) {
        pathList.addNativePath(libPaths);
    }

    //...
}


```



`BaseDexClassLoader` 的构造器参数分析：



- `dexPath`，包含 classes 文件和资源文件的 `jar` 或者 `apk` 文件路径列表，多个路径用`File.pathSeparator`也即 `:` 隔开；
- `optimizedDirectory` , 这个参数的路径本来是用来存放 odex 的，dex 的加载过程涉及到一个 dex 优化变成 odex，缓存 odex 文件可以提升性能，但是 API 26 后就废弃了这个参数；

- `librarySearchPath` ，native libraries 的路径列表；
- `parent`，父类加载器。



另外可以看到构造方法里实例化了一个 `DexPathList` ，并且 `findClass(String name)`方法里也调用了 `DexPathList`的 `findClass` 方法，所以实际上，BaseDexClassLoader 的类加载处理其实交给了 `DexPathList`来处理，这里不展开了。



### Android 的 ClassLoader 和 JVM 的不同点

不支持 defineClass 

```java
//ClassLoader
protected final Class<?> defineClass(String name, java.nio.ByteBuffer b,
                                     ProtectionDomain protectionDomain)
    throws ClassFormatError
{
    throw new UnsupportedOperationException("can't load this type of class file");
}
```



### 总结



### 资料

https://developer.android.com/reference/dalvik/system/PathClassLoader

[http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/PathClassLoader.java](http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/PathClassLoader.java)

https://juejin.im/post/5a28e7e86fb9a045117105c3

http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java

http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/DexFile.java#defineClassNative

http://gityuan.com/2017/03/19/android-classloader/