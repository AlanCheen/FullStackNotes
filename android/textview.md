# TextView






### 让 TextView 的内容可以滚动

不需要添加 ScrollView 作为 Parent。

```java
TextView tv = findViewById(R.id.tv_);
tv.setMovementMethod(new ScrollingMovementMethod());
```
xml 可以设置 scrollbar 的样式：
```xml
<TextView
        android:id="@+id/tv_"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:scrollbarFadeDuration="200"
        android:scrollbarSize="1dp"
        android:scrollbarStyle="insideOverlay"
        android:scrollbars="vertical"
        android:text="PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder PlaceHolder Pla ceHolder PlaceH older P lace H older Pla ceHo  lder Pla ceH  old er "
        android:textColor="@android:color/black"
        />
```