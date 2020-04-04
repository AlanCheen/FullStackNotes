# 第4章 ListView使用技巧


注：使用RecyclerView替代ListView吧！

1. ViewHolder模式 复用 Item
2. `android:divider`设置分割线
3. `android:scrollbars="none"`隐藏滚动条
4. `android:listSelector="#0000000"` 可以设置item选中状态
5. `ListView.setEmptyView()`处理空数据  

实现具有弹性的ListView,重写
`protected boolean overScrollBy(int,int,int,int,int,int,int ,int maxOverScrollY,boolean)`方法中的`maxOverScrollY`(单位为像素)值,将它设置不为0就可以实现