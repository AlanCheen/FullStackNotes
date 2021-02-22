# Shape Drawable


> 迁移语雀 2021-2-22


指 XML 里的 shape 类型。



- stroke
- soild
- 







### 虚线背景

![image-20190830162151831](/Users/mingjue/Library/Application Support/typora-user-images/image-20190830162151831.png)



```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">

    <stroke
        android:width="1dp"
        android:color="#999999"
        android:dashGap="4dp"
        android:dashWidth="2dp" />
</shape>
```



- dashGap，间隔
- dashWidth，宽度
- width，宽度