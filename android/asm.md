# ASM

> 迁移语雀 2021-2-22


ASM是一个通用的 **Java 字节码操作和分析框架**。 它可以**直接以二进制形式用于修改现有类或动态生成类**。 ASM提供了一些常见的字节码转换和分析算法，可以从中构建定制的复杂转换和代码分析工具。 ASM提供与其他Java字节码框架类似的功能，但**侧重于性能**。 因为它的设计和实现是尽可能的小和尽可能快，所以它非常适合在动态系统中使用（但当然也可以以静态方式使用，例如在编译器中）。(官网介绍)



ASM 操作 class 文件，而在 Android 中其实是 dex 文件，不能直接操作 class 文件，所以如果要在 Android 中使用，得配合 gradle 插件以及 gradle 的 transform api 。









## 资料

1. [ASM](https://asm.ow2.io/index.html)
2. [AOP 的利器：ASM 3.0 介绍](https://www.ibm.com/developerworks/cn/java/j-lo-asm30/index.html)
3. [【Android】函数插桩（Gradle + ASM）](https://cloud.tencent.com/developer/article/1399805)
4. [Android全埋点解决方案之ASM](https://www.sensorsdata.cn/blog/20181206-9/)