# Activity 






### 启动模式





#### Flags

##### FLAG_ACTIVITY_REORDER_TO_FRONT

```java
Intent intent = new Intent(this, Foo.class);  
intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);   
startActivity(intent); 
```

如果 Activity 已经存在，则会被移动到栈顶，不会新启动一个 Activity。（onNewIntent 会被调用）

例如有 A、B、C 三个 Activity，再次启动 B 要变成：A C B 而不是 A B C B 的话，就可以使用它。

坑：透明的 Activity 不起作用， 得加上启动模式 singleTask or singleTask。


##### FLAG_ACTIVITY_CLEAR_TOP

```java
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
```

如果目标 Activity 存在，则会清除它顶部的所有 Activity，并将 Intent 传递给它，调用 onNewIntent() 。

A B C D ，启动 B ==> A B ， CD 都因为在 B 顶部所以被移除。

但是默认情况下这个 B 会被重启，如果不想，则需要加`FLAG_ACTIVITY_SINGLE_TOP` 到 Intent 上。

https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TOP