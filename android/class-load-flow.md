# Android 加载类的流程分析

[TOC]

### 前言

 

学习一下Android 中加载类的过程，在 [Android ClassLoader](./android-classloader.md) 中学到了BaseDexClassLoader 有个成员变量 DexPathList ，它代理了 BaseClassLoader 的所有方法，来了解下。



### DexPathList



> A pair of lists of entries, associated with a {@code ClassLoader}.
> One of the lists is a dex/resource path &mdash; typically referred
> to as a "class path" &mdash; list, and the other names directories
> containing native code libraries. Class path entries may be any of:
> a {@code .jar} or {@code .zip} file containing an optional
> top-level {@code classes.dex} file as well as arbitrary resources,
> or a plain {@code .dex} file (with no possibility of associated
> resources).
>
> This class also contains methods to use these lists to look up
> classes and resources.



看一下类的定义：

```java

final class DexPathList {

private final ClassLoader definingContext;

private Element[] dexElements;

public DexPathList(ClassLoader definingContext, String dexPath,
        String librarySearchPath, File optimizedDirectory) {
    this(definingContext, dexPath, librarySearchPath, optimizedDirectory, false);
}

DexPathList(ClassLoader definingContext, String dexPath,
        String librarySearchPath, File optimizedDirectory, boolean isTrusted) {

    //...
    this.definingContext = definingContext;

    ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
    // save dexPath for BaseDexClassLoader
    this.dexElements = makeDexElements(splitDexPath(dexPath), optimizedDirectory,
                                       suppressedExceptions, definingContext, isTrusted);

		//...
    this.nativeLibraryPathElements = makePathElements(allNativeLibraryDirectories);
		//...
}
}
```



包级访问权限，我们不能实例化它。

成员变量：

- Element[] `dexElements`，dex/resource 元素列表

- ClassLoader `definingContext`，就是 BaseDexClassLoader 的具体实例；



着重看`    this.dexElements = makeDexElements();`方法，它把我们传递进来的 dexPath 解析出来，并生成了 Element。



### findClass(String name, List<Throwable> suppressed) 



`BaseDexClassLoader.findClass` 方法中调用了`pathList.findClass(name, suppressedExceptions)`



```java
//DexPathList
public Class<?> findClass(String name, List<Throwable> suppressed) {
    for (Element element : dexElements) {
        Class<?> clazz = element.findClass(name, definingContext, suppressed);
        if (clazz != null) {
            return clazz;
        }
    }

    if (dexElementsSuppressedExceptions != null) {
        suppressed.addAll(Arrays.asList(dexElementsSuppressedExceptions));
    }
    return null;
}
```



它又调用了 `Element.findClass` 。



### Element



Element  是 DexPathList 的一个内部类。

```java
/**
 * Element of the dex/resource path. Note: should be called DexElement, but apps reflect on
 * this.
 */
/*package*/ static class Element {
    /**
     * A file denoting a zip file (in case of a resource jar or a dex jar), or a directory
     * (only when dexFile is null).
     */
    private final File path;

    private final DexFile dexFile;

    private ClassPathURLStreamHandler urlHandler;
    private boolean initialized;

    /**
     * Element encapsulates a dex file. This may be a plain dex file (in which case dexZipPath
     * should be null), or a jar (in which case dexZipPath should denote the zip file).
     */
    public Element(DexFile dexFile, File dexZipPath) {
        this.dexFile = dexFile;
        this.path = dexZipPath;
    }

    public Element(DexFile dexFile) {
        this.dexFile = dexFile;
        this.path = null;
    }

    public Element(File path) {
      this.path = path;
      this.dexFile = null;
    }

    /**
     * Constructor for a bit of backwards compatibility. Some apps use reflection into
     * internal APIs. Warn, and emulate old behavior if we can. See b/33399341.
     *
     * @deprecated The Element class has been split. Use new Element constructors for
     *             classes and resources, and NativeLibraryElement for the library
     *             search path.
     */
    @Deprecated
    public Element(File dir, boolean isDirectory, File zip, DexFile dexFile) {
        System.err.println("Warning: Using deprecated Element constructor. Do not use internal"
                + " APIs, this constructor will be removed in the future.");
        if (dir != null && (zip != null || dexFile != null)) {
            throw new IllegalArgumentException("Using dir and zip|dexFile no longer"
                    + " supported.");
        }
        if (isDirectory && (zip != null || dexFile != null)) {
            throw new IllegalArgumentException("Unsupported argument combination.");
        }
        if (dir != null) {
            this.path = dir;
            this.dexFile = null;
        } else {
            this.path = zip;
            this.dexFile = dexFile;
        }
    }

    public Class<?> findClass(String name, ClassLoader definingContext,
            List<Throwable> suppressed) {
      	//交给dexFile
        return dexFile != null ? dexFile.loadClassBinaryName(name, definingContext, suppressed)
                : null;
    }

}
```



可以看到 findClass 方法里调用了 `dexFile.loadClassBinaryName` 方法。



### DexFile



> Loads DEX files. This class is meant for internal use and should not be used
> by applications.
>
> @deprecated This class should not be used directly by applications. It will hurt
>     performance in most cases and will lead to incorrect execution of bytecode in
>     the worst case. Applications should use one of the standard classloaders such
>     as {@link dalvik.system.PathClassLoader} instead. <b>This API will be removed
>     in a future Android release</b>.



DexFile 用来加载 `.dex`文件，但是 Android 推荐应用使用 PathClassLoader 。



```java

/**
 * See {@link #loadClass(String, ClassLoader)}.
 *
 * This takes a "binary" class name to better match ClassLoader semantics.
 *
 * @hide
 */
public Class loadClassBinaryName(String name, ClassLoader loader, List<Throwable> suppressed) {
    return defineClass(name, loader, mCookie, this, suppressed);
}

private static Class defineClass(String name, ClassLoader loader, Object cookie,
                                 DexFile dexFile, List<Throwable> suppressed) {
    Class result = null;
    try {
        result = defineClassNative(name, loader, cookie, dexFile);
    } catch (NoClassDefFoundError e) {
        if (suppressed != null) {
            suppressed.add(e);
        }
    } catch (ClassNotFoundException e) {
        if (suppressed != null) {
            suppressed.add(e);
        }
    }
    return result;
}

private static native Class defineClassNative(String name, ClassLoader loader, Object cookie,
                                              DexFile dexFile)
```



最终调用了一个 native 方法完成类加载。



### 类图

总结一下相关类的类图。



<img src="https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1559812538861-14d45d3b-d2a0-47d1-b468-7dd183dff9e3.jpeg" width="746" height="479">

### 总结



总体流程：

```java
ClassLoader.loadClass
->parent.loadClass

->bootstrap

->DexClassLoader/PathClassLoader.findClass()

->BaseDexClassLoader.findClass(String name)

->DexPathList.findClass(name, suppressedExceptions)

->Element.element.findClass(name, definingContext, suppressed)

->DexFile.loadClassBinaryName(String name, ClassLoader loader, List<Throwable> suppressed)

->DexFile.defineClass(String name, ClassLoader loader, Object cookie,
                                 DexFile dexFile, List<Throwable> suppressed)

->native defineClassNative
```



一层一层传递，最后是 DexFile 来调用 `defineClassNative`来完成类的加载。



正因为 DexPathList 的原理，它被各大热修复、插件化框架所使用，用来动态加载类，不学不行。搞下原理



### 资料

http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java

http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/DexFile.java#defineClassNative