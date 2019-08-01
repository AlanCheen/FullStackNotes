# RecyclerView

[TOC]





- [ItemDecoration](./rv-item-decoration.md)









### 实战

#### 切换数据源后依然保存滚动的位置

有时候 RecyclerView 切换数据源的时候会导致原来的滚动位置丢之，可能会自动滚动到顶部。

```java
//获取保存的信息
Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
//切换数据源
adapter.setModels(data);
//restore recyclerView scroll position
recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
```

好像有效？



#### 解决ViewHolder 中有 ScrollView 时 ScrollView 无法滚动的问题



```java
//recyclerview
recyclerView.setNestedScrollingEnabled(false);

//scrollview
scrollView.setOnTouchListener(new OnTouchListener() {
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }
});
```



#### ScrollView 中 RecyclerView 一次性加载所有 Item 的问题

无解？尽量避免这种问题，选择用一个或多个 RecyclerView 来实现。