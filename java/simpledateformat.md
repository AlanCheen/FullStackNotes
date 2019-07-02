# SimpleDateFormat



```java
String pattern = "xxx";
SimpleDateFormat format = new SimpleDateFormat(String pattern);
String time = format.format(new Date());
```





- `G` 年代标志符
- `y` 年
- `M` 月
- `d` 日
- `h` 时 在上午或下午 (1~12)
- `H` 时 在一天中 (0~23)
- `m` 分
- `s` 秒
- `S` 毫秒
- `E` 星期
- `D` 一年中的第几天
- `F` 一月中第几个星期几
- `w` 一年中第几个星期
- `W` 一月中第几个星期
- `a` 上午 / 下午 标记符 
- `k` 时 在一天中 (1~24)
- `K` 时 在上午或下午 (0~11)
- `z` 时区



常见的 `pattern`：

- `yyyy-MM-dd HH:mm:ss`
- `yyyy年MM月dd日 HH时mm分ss秒`
- `yyyy年MM月dd日 HH时mm分ss秒 E `



