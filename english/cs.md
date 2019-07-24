



Non-static inner class holds a reference to the containing class. When you declare AsyncTask as an inner class, it might live longer than the containing Activity class. This is because of the implicit reference to the containing class. This will prevent the activity from being garbage collected, hence the memory leak.



- `non-static`， 非静态的

- `inner calss`， 内部类
- `the containing class` ，外部类 或者说 `the outer class`
- `hold a reference`，持有一个引用
- `live logner than…`，比…存活更久
- `implicit reference`，隐式引用
- `prevent from [sth][doing sth]`，防止做某事
- `be garbage collected`，被垃圾回收
- `hence`，因此
- `hence the memory leak`，内存泄露

