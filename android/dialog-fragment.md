# DialogFragment
> 迁移语雀 2021-2-22



相比于 AlertDialog ，Google 更加推荐 DialogFragment ，因为它能更好地处理生命周期、屏幕旋转等 Activity 重建等情况。



DialogFragment 可以完全当成一个 Fragment 来用，重写 onCreateView 然后使用即可。





- onCreateView
- onCreateDialog



### 设置透明背景



默认情况下 DialogFragment 里的 View 会有个背景（可能是白色）。

凡是设置透明度，都是通过 Window 来设置，DialogFragment 本身也依赖于 Window 所以此路亦通。



**方法一，设置 theme**：

```xml
    <style name="MyTheme" parent="@android:style/Theme.Dialog">
        <item name="android:windowFrame">@null</item>
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:windowIsFloating">true</item>
        <item name="android:windowContentOverlay">@null</item>
        <item name="android:windowTitleStyle">@null</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:backgroundDimEnabled">false</item>
    </style>
```



```java
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyTheme);
    }
```



**方法二**，

在 `onStart()` 方法获取 window，然后调用 `window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));`。

```java
@Override
public void onStart() {
  super.onStart();
  final Dialog dialog = getDialog();
  if (dialog != null) {
    final Window window = dialog.getWindow();
    if (window != null) {
      window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }
}
```

好像并不管用？？？？



### BottomSheetDialogFragment



BottomSheetDialogFragment 是 BottomSheet 样式的实现，唯一不同的是它用的 BottomSheetDialog。

```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
  return new BottomSheetDialog(getContext(), getTheme());
}
```

