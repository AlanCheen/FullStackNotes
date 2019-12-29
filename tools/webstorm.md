# WebStorm


> 最后同步时间，2019-12-29







###  授权未认证端访问网页（requested without authorization）



想用 Android 写个 Demo 用 WebView 加载一下在 WebStorm 里写的网页，发现打不开，报错 404。



![image-20190822115321133](https://tva1.sinaimg.cn/large/006tNbRwly1gadvidnhnfj309c0g80tb.jpg)

并且 WebStorm 提示

`xxxx  requested without authorization, you can copy URL and open it in browser to trust it.`



这是因为 Webstorm 默认不允许未经认证的端访问网页。

可以在设置中开启，build -> allow unsigned requests。



![image-20190822114852207](https://tva1.sinaimg.cn/large/006tNbRwly1gadviiru0cj310i0u0n8b.jpg)



然后在 Android 端的 WebView 就可以加载了。