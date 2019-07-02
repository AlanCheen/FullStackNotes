# Android 里的时间







### 计算时间差



常见于计算时间耗时，可以考虑使用`SystemClock.uptimeMillis()`方法：

```java
long startTime = SystemClock.uptimeMillis();
...
long time = SystemClock.uptimeMillis();

long dTime = time - startTime;
```

