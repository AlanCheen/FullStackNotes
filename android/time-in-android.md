# Android 里的时间



> 迁移语雀 2021-2-22




### 计算时间差



常见于计算时间耗时，可以考虑使用`SystemClock.uptimeMillis()`方法：



```java
long startTime = SystemClock.uptimeMillis();
...
long time = SystemClock.uptimeMillis();

long dTime = time - startTime;
```



### 资料

[SimpleDateFormat](../java/simpledateformat.md)