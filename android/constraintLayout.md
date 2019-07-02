# ConstraintLayout



约束布局。







### Guideline







```xml
<android.support.constraint.Guideline
    android:id="@+id/gl_bottom"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    app:layout_constraintGuide_end="39dp"/>
```



### Groups



```xml
<android.support.constraint.Group
    android:id="@+id/top_group"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:constraint_referenced_ids="facing,lighting,back"
    />
```

