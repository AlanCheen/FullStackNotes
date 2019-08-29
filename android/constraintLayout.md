# ConstraintLayout



约束布局。



性能提升：10%~45%，一般可认为在 30% 左右。



```groovy
dependencies {
  compile 'com.android.support.constraint:constraint-layout:1.1.3'
}
```



### 常用属性分析



- `app:layout_constraintBaseline_toBaselineOf`，文本 baseline 对齐，比如 TextView；
- `app:layout_constraintHorizontal_bias="0.1"`，水平偏移；
- `app:layout_constraintVertical_biass="0.1"`，垂直偏移；
- `layout_goneMarginXXX`，不可见时的 margin，有时候 A 在 B 左边，在 B 不见时依然要保留 margin 时可用；
- `app:layout_constraintDimensionRatio="2:1"`，宽高比，可以填写设计稿上的宽高来做适配，例如`750:270`；



### Guideline



Guideline 本身不可见，可以用做辅助线。



```xml
<android.support.constraint.Guideline
    android:id="@+id/gl_bottom"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    app:layout_constraintGuide_end="39dp"/>
```



- `android:orientation="vertical"`，水平 or 垂直；
- `app:layout_constraintGuide_end="39dp"`，距离 end 多少距离，end 在`horizontal`的情况下指的是 bottom，在 `vertical`的情况下指的是 bottom；
- `app:layout_constraintGuide_begin="20dp"`，距离 begin 多少距离，方向跟 begin 相反；
- `app:layout_constraintGuide_percent="0.055555556"`，按百分比计算位置；



### Barriers



屏障，用来控制 View，不能超过界限。

比如 C 在 A 和 B 的右边，有时候 A 更宽，有时候 B 更宽，这个时候用 A 或者 B 约束 C 都不行，就需要用到 Barriers 了。



### Group

Group 组，用来把多个 View 组成一个组，方便`一次性控制多个 View 的可见性`。（只能是可见性）



有时候某个操作需要隐藏/展示多个 View，一个个设置很麻烦，使用 Group 就只需要操作 Group 即可。



```xml
<android.support.constraint.Group
    android:id="@+id/top_group"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:constraint_referenced_ids="facing,lighting,back"
    />
```



```java
Group topGroup = findViewById(R.id.top_group);
topGroup.setVisibility(View.VISIBLE);
```



- `app:constraint_referenced_ids="facing,lighting,back"`，ids 填写要组成一个组的 View 的 id，多个 id 用`，`隔开。



`多个 Group 可以引用同个 View，在 XML 排最后的 Group 拥有最终决定 View 可见性的话语权。`





### Circular Position

<img src="http://ww1.sinaimg.cn/large/98900c07ly1g4lump150qj20jz08kmx6.jpg"/>

使用 circular position 需要指定如下约束：

- `app:layout_constraintCircle`, id，跟其他约束类似，需要指定 circle 约束
- `app:layout_constraintCircleRadius`，数值，半径
- `app:layout_constraintCircleAngle`，数值，角度，0-360 应该



**注意：**常规的宽高约束将会失效。



例如这种布局：



![image-20190827165620400](/Users/mingjue/Library/Application Support/typora-user-images/image-20190827165620400.png)



删除按钮距离背后黑色的背景某个距离，可以用它来实现效果。



```xml
<ImageView
    android:id="@+id/videoCover"
    android:layout_width="78dp"
    android:layout_height="78dp"
    android:layout_marginLeft="12dp"
    tools:background="#000000"
    app:layout_constraintBottom_toBottomOf="@id/uploadBlock"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toTopOf="@id/uploadBlock"
    />

<ImageView
    android:id="@+id/deleteIcon"
    android:layout_width="15dp"
    android:layout_height="15dp"
    android:src="@drawable/vp_video_delete"
    app:layout_constraintCircleAngle="45"
    app:layout_constraintCircle="@id/videoCover"
    app:layout_constraintCircleRadius="52dp"
    />
```





### Placeholder





### 实战-文档里没写的



#### 子 View 设置最大高度



约束布局里 maxHeight 是不起作用的，需要做如下设置：

```xml
android:layout_height="wrap_content"
app:layout_constraintHeight_max="300dp"
app:layout_constrainedHeight="true"
```











