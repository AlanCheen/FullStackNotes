# WebStorm









###  授权未认证端访问网页（requested without authorization）



想用 Android 写个 Demo 用 WebView 加载一下在 WebStorm 里写的网页，发现打不开，报错 404。



![image-20190822115321133](/Users/mingjue/Library/Application Support/typora-user-images/image-20190822115321133.png)

并且 WebStorm 提示

`xxxx  requested without authorization, you can copy URL and open it in browser to trust it.`



这是因为 Webstorm 默认不允许未经认证的端访问网页。

可以在设置中开启，build -> allow unsigned requests。



![image-20190822114852207](/Users/mingjue/Library/Application Support/typora-user-images/image-20190822114852207.png)



然后在 Android 端的 WebView 就可以加载了。