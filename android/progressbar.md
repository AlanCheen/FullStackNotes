# ProgressBar

> 迁移语雀 2021-2-22




### 常用属性



- `style="@style/Widget.MaterialProgressBar.ProgressBar.Horizontal"`，设置水平还是转圈等；
- `android:progressDrawable="@drawable/xxx"`，设置进度条的样式；
- `android:progress="20"`，设置进度；
- `android:secondaryProgress=""`，设置次级进度；



### 自定义 ProgressBar 展示的样式



1. 新建 `layer-list` 类型的 drawable；
2. 按需添加 `item`：
   1. `android:id="@android:id/background"` , 背景
   2. `android:id="@android:id/secondaryProgress"`，
   3. `android:id="@android:id/progress"`
3. 给 progressbar 设置`android:progressDrawable`属性，如 `"@drawable/custom_progress"`;



```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:id="@android:id/background">
        <shape>
            <corners android:radius="2dp"/>
            <solid android:color="#979797"/>
        </shape>
    </item>

    <item android:id="@android:id/secondaryProgress">
        <clip>
            <shape>
                <corners android:radius="2dp"/>
                <solid android:color="#979797"/>
            </shape>
        </clip>
    </item>

    <item android:id="@android:id/progress">
        <clip>
            <shape>
                <corners android:radius="2dp"/>
                <solid android:color="#FF6D2B"/>
            </shape>
        </clip>
    </item>

</layer-list>
```



```xml
<ProgressBar
    android:id="@+id/progress_bar"
    style="@style/Widget.MaterialProgressBar.ProgressBar.Horizontal"
    android:layout_width="176dp"
    android:layout_height="3dp"
    android:layout_marginTop="3dp"
    android:progressDrawable="@drawable/custom_progress"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    tools:progress="30"
    />
```

