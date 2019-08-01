# ConstraintLayout



约束布局。



性能提升：10%~45%，一般可认为在 30% 左右。



```groovy
dependencies {
  compile 'com.android.support.constraint:constraint-layout:1.1.3'
}
```



### 属性分析



- app:layout_constraintBaseline_toBaselineOf，文本 baseline 对齐，比如 TextView；
- app:layout_constraintHorizontal_bias="0.1"，水平偏移；
- app:layout_constraintVertical_biass="0.1"，垂直偏移；
- layout_goneMarginXXX，不可见时的 margin，有时候 A 在 B 左边，在 B 不见时依然要保留 margin 时可用；
- app:layout_constraintDimensionRatio="2:1"，宽高比；



### Guideline



Guideline 本身不可见，用做辅助线。



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

Group 组，用来把多个 View 组成一个组，方便一次性控制多个 View 的可见性。（只能是可见性）



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



- `app:constraint_referenced_ids="facing,lighting,back"`，ids 填写要组成一个组的 View 的 id，多个用`，`隔开。



### Circular Position

<img src="http://ww1.sinaimg.cn/large/98900c07ly1g4lump150qj20jz08kmx6.jpg"/>

### Placeholder





### 实战



#### 子 View 设置最大高度



约束布局里 maxHeight 是不起作用的，需要做如下设置：

```xml
android:layout_height="wrap_content"
app:layout_constraintHeight_max="300dp"
app:layout_constrainedHeight="true"
```











