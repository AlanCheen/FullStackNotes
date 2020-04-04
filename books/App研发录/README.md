# App研发录

书的title都取得很高端,但是内容比较一般,特别是讲开发的,知识点分散,又不具体,不深入,差不多就是告诉你有XX东西,很有必要,但是又不教你怎么去做,所以几乎等于没讲啊。（很失望）作者可能在开发领域有挺长的时间,积累了很多开发和管理经验,但是这书的内容不太行,我表示很失望.（针对Android方面，针对个人而言）  

这里记录一些 **我还觉得还算有用的** 知识点.          

## 高效App框架设计与重构

0. 自定义一些基类(如BaseActivity)
1. 采取一定的方式在客户端模拟API返回数据,用于测试  
2. 启用Cookie
3. Http数据要加密
2. 公共参数可以存放到Header里
3. 时间校准(返回服务器时间与本地时间比较(非常有必要))  
4. HTTP请求开启**gzip压缩**来减少传输量(省流量,加快速度),如果用Okhttp,gzip挺简单的,不过需要服务端的支持  
5. 根据网络状况去下载不同尺寸的图片
6. 急速模式(比如不显示图片,提供开关)
7. 城市列表**增量更新**(本地保留版本号,与服务器对照,下发需要修改的数据,CURD进行操作)  
8. H5跳转Activity,可以传递一个String,按照约定的协议去解析,再跳转(一般传递Activity的全称,再 key-value来传递参数)
9. 命名规范和代码规范(我推荐checkstyle)  

## App开发中的高级技巧

同上一章节,关于Crash竟然没写Android具体应该怎么做,却写了一堆数据库代码,真是醉了~  
然后分析Crash,没什么软用,crash什么的碰到一次,你就知道了,提前看也没什么作用,而且,很多作者自己也根本没弄懂,呵呵.  

持续集成章节基于Eclipse,Ant,这能看?(反正我是不用Eclipse也不用Ant的直接跳过了)  
单元测试差不多就是讲了有这么东西,然而并不教你怎么做.  

诶~忍不住又吐槽了~   

1. 通过实现**UncaughtExceptionHandler**去捕获异常
2. 异常分析时出现**Unknown Source**,丢失了文件名和行号,这比较蛋疼,可以在ProGuard文件中增加`-keepattributes SourceFile,LineNumberTable`(umong分析是上传mapping文件,这个方法还有待验证,扩展阅读->[bugly](http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=244) and [精神哥](http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=26))    


## App竞品技术分析


利用 **AXMLPrinter2.jar**可以还原被压缩过后的xml (这个对反编译有帮助,暂时还没有尝试)    
```
java -jar AXMLPrinter2.jar AndroidManifest.xml
```

0. 获取最佳服务器
1. 数据采集
2. ABTest
3. Android打包META-INF目录的妙用
4. 拆分classes.dex
5. 模块化 热修复 插件化

## 项目管理和团队建设  

这章我差不多就浏览了一遍,内容很日常化,也就不想多记录什么了,自有体会.  

1. 不能没有测试团队(赞同!我司没有,深感痛苦 自测测出的bug很少,而且会占用开发大量的时间)
2. 模块化分工
3. 每天例会
4. ...  











