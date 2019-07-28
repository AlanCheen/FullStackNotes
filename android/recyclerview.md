# RecyclerView





- [ItemDecoration](./rv-item-decoration.md)











### 保存滚动的位置

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





