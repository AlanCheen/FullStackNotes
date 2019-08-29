# TouchDelegate



> Helper class to handle situations where you want a view to have a larger touch area than its
> actual view bounds. The view whose touch area is changed is called the delegate view. This
> class should be used by an ancestor of the delegate. To use a TouchDelegate, first create an
> instance that specifies the bounds that should be mapped to the delegate and the delegate
> view itself.



有时候我们需要让一个 View 的可点击区域比它本身的视图区域更大，通常我们会使用 padding 来扩大点击区域，其实 Android 给我们提供了 TouchDelegate 类，它可以用来处理这种情况。



也即扩展 View 的点击区域有两种方式：

1. 设置 Padding 来扩大 View 大小；
2. 设置 TouchDelegate 来扩大点击区域，此时 View 的实际大小并没有改变；



### 实战



例如，现在有一个 TextView，想扩展它的点击区域，可以这么写：



```java
final View parent = findViewById(R.id.parent);
final View text = findViewById(R.id.text);
parent.post(new Runnable() {
    @Override
    public void run() {
        Rect bounds = new Rect();
        text.getHitRect(bounds);
        bounds.left -= 100;
        bounds.top -= 100;
      	bounds.right += 100；
        bounds.bottom += 100;
                TouchDelegate delegate = new TouchDelegate(bounds, text);
        parent.setTouchDelegate(delegate);
    }
});
text.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(final View view) {
        Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
    }
});
```

注：该例子扩展了 View 四周 100 个像素的点击区域。



也可以抽成一个万能的方法，代码可以查看：[ViewUtils](https://github.com/AlanCheen/Pandora/commit/a3406e55732a76fd65fad48b6735eea089169478)



需要注意：

1. Rect 的四个点的处理有区别： left top 是`-=`，right bottom 是`+=`，这样才能扩大点击区域；
2. Rect 的四个值就是代表`离 Parent 左上角的距离`；
3. Rect 的 left、top 可以是负数，例如 left、top 的值为负数，其实跟 0 是一样的表现；
4. 当扩大的区域里包含了可点击的其他的 View，那么点击事件还是归那个 View 消费，也就是说不会吃掉别的 View 的事件。



### 原理



其实也很好理解，一个 View 的点击区域由它的 Parent 决定，修改 Rect 就能够修改它的点击范围。



其实 TouchDelegate 会干预 Parent 的点击事件`onTouchEvent`。



### 资料

https://developer.android.com/reference/android/view/TouchDelegate