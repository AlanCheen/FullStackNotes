# 第12章-Bitmap的加载和Cache


### 高效加载

1. 加载所需尺寸。通过控制 inSampleSize，即可控制图片采样率，比如取值为2，则宽高都为原来的1/2,则为原图的1/4。

可以先将 BitmapFactory.Options.inJustDecodeBounds 设置为 true，获取宽高信息，再计算 inSampleSize，然后再将 inJustDecodeBounds 设置为 false，加载图片。

2. 缓存策略 
LruCache（Least Recently Used） 最近最少使用
DiskLruCache，分别常被用来做内存缓存，磁盘缓存。  

Lru 算法使用一个 LinkedHashMap 实现