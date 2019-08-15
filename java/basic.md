# Java 基础













### 基本类型



long int String short char boolean float double





Numeric overflow in expression

在表达某个数字时，我用了 `5 *1024 * 1024 *1024` ，提示是 numeric overflow ，因为超过 int 的范围了

，需要转成 long，可以改成：`5L *1024*1024*1024`，就OK啦。