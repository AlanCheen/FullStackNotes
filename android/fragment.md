# Fragment



### 概述



Android 在 Android 3.0（API 级别 11）中引入了 Fragment，主要是为了给大屏幕（如平板电脑）上更加动态和灵活的 UI 设计提供支持。由于平板电脑的屏幕比手机屏幕大得多，因此可用于组合和交换 UI 组件的空间更大。利用片段实现此类设计时，您无需管理对视图层次结构的复杂更改。 通过将 Activity 布局分成片段，您可以在运行时修改 Activity 的外观，并在由 Activity 管理的返回栈中保留这些更改。



#### 静态添加



静态添加：在 layout 文件中添加 fragment ，在运行中不能修改。



例子：

```xml
<fragment
    android:id="@+id/camera"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:name="me.yifeiyuan.CameraFragment"
    />
```



注意事项：

- fragment 需要一定*唯一*的 id，方法有三：
  - 指定 `android:id`  属性
  - 指定 `android:tag` 属性
  - 如果上述两个都没有用，则需要给 parent 设置 id，否则会报错 `Must specify unique android:id , android:tag, or have a parent with an id .`

- `android:name`：name 指定 Fragment 全限定的名称。



#### 动态添加



可以在运行时通过 FragmentTransaction API 添加到 Activity。



```java
FragmentManager fragmentManager = getFragmentManager();
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
ExampleFragment fragment = new ExampleFragment();
fragmentTransaction.add(R.id.fragment_container, fragment);
fragmentTransaction.commit();
```



#### 添加没有 UI 的 Fragment

可以通过 `add(Fragment,String)`方法添加 non-ui fragment。

这样的 fragment 可以用作工作线程，也可以有类似 Lifecycle、ViewModel 那种妙用。



Non-ui fragment 不会回调`onCreateView` 。

### 资料

https://developer.android.com/guide/components/fragments?hl=zh-CN

https://developer.android.com/reference/android/app/Fragment