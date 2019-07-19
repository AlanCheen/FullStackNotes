# PopupMenu

> A PopupMenu displays a `Menu` in a modal popup window anchored to a `View`. The popup will appear below the anchor view if there is room, or above it if there is not. If the IME is visible the popup will not overlap it until it is touched. Touching outside of the popup will dismiss it.



PopupMenu 可以以某个 View 为锚点，展示一个菜单栏。



会根据锚点、Gravity 以及空间展示，并且点击外部会消失。



### 基础使用教程



使用步骤：

1. `res`目录下 新建 menu 目录
2. `menu`目录下新建 foo.xml
3. 构建 PopupMenu，加载菜单
4. 设置监听
5. show() 展示



```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/report" android:title="Report"/>
    <item android:id="@+id/cancel" android:title="Cancel"/>
</menu>
```



```java
PopupMenu menu = new PopupMenu(this, more, Gravity.BOTTOM);
menu.inflate(R.menu.foo);
menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
  @Override
  public boolean onMenuItemClick(final MenuItem menuItem) {
    if (menuItem.getItemId() == R.id.report) {
      Toast.makeText(MainActivity.this,"Report", Toast.LENGTH_SHORT).show();
    }
    return false;
  }
});
menu.show();
```



### 拖拽（drag-to-open）



```java
 PopupMenu myPopup = new PopupMenu(context, myAnchor);
 myAnchor.setOnTouchListener(myPopup.getDragToOpenListener());
```



可以实现拖拽 anchor 弹出菜单。



### 修改背景颜色



修改背景色需要设置主题。



### 内部原理



实际上 PopupMenu 的实现依赖于Window（MenuPopupWindow 等）