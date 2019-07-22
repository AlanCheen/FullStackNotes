# ItemDecoration











### DividerItemDecoration



ItemDecoration 的一种实现，可以用于实现一般的 Item 分隔效果，比如 Item 之间间隔 N dp。



使用很简单，可以用 xml 写一个 shape 的drawable，然后设置给 DividerItemDecoration。



1）XML ：

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    //配置颜色
    <solid android:color="@color/transparent"/>
    //配置宽高
    <size
        android:width="12dp"
        android:height="12dp"/>
</shape>
```

2）JAVA：

```java
DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.foo));
recyclerView.addItemDecoration(decoration);
```

注意下：

DividerItemDecoration.HORIZONTAL 方向需要配置 width , VERTICAL 方向需要配置 height ;