# ProgressBar







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

