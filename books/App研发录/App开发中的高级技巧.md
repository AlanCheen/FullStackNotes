## App开发中的高级技巧

同上一章节,关于Crash竟然没写Android具体应该怎么做,却写了一堆数据库代码,真是醉了~  
然后分析Crash,没什么软用,crash什么的碰到一次,你就知道了,提前看也没什么作用,而且,很多作者自己也根本没弄懂,呵呵.  

持续集成章节基于Eclipse,Ant,这能看?(反正我是不用Eclipse也不用Ant的直接跳过了)  
单元测试差不多就是讲了有这么东西,然而并不教你怎么做.  

诶~忍不住又吐槽了~   

1. 通过实现**UncaughtExceptionHandler**去捕获异常
2. 异常分析时出现**Unknown Source**,丢失了文件名和行号,这比较蛋疼,可以在ProGuard文件中增加`-keepattributes SourceFile,LineNumberTable`(umong分析是上传mapping文件,这个方法还有待验证,扩展阅读->[bugly](http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=244) and [精神哥](http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=26))    