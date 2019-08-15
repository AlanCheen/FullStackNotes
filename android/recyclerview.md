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





### 注意点



#### onBindViewHolder 里的 position 问题



在 onBind 方法里用到 position 类似这样：

```java
callback.onItemClicked(position);
```

Lint 扫描的时候提示：

> Do not treat position as fixed; only use immediately and call holder.getAdapterPosition() to look it up later less... 
>  RecyclerView will not call onBindViewHolder again when the position of the item changes in the data set unless the item itself is invalidated or the new position cannot be determined.  For this reason, you should only use the position parameter while acquiring the related data item inside this method, and should not keep a copy of it.  If you need the position of an item later on (e.g. in a click listener), use getAdapterPosition() which will have the updated adapter position.
>  Issue id: RecyclerView

需要改成这样：

```java
callback.onItemClicked(viewHolder.getAdapterPosition());
```



因为 position 可能会变，所以要用 position 一定要每次都用`viewHolder.getAdapterPosition()`来获取。

