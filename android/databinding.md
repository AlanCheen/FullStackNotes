# DataBinding

> Android 4.0 (API level 14) or higher

### 概述

> The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.



DataBinding 是一个可以允许使用声明式（declarative）让我们在 layout 里把 UI 控件跟数据源绑定的库。 



DataBinding 的优势：

- 在 layout 里做绑定可以省去很多代码，提升可维护性；
- 性能更好，防止空指针——why?TODO



### 实战例子



大概的步骤：

1. 开启 DataBinding；
2. 新建 Activity & XML，添加 layout variable， 写绑定逻辑；
3. 通过 DataBindingUtil 给 Activity 设置布局；
4. 给自动生成的 Binding 设置变量；
5. Run App !



#### 1. DataBinding 环境配置

环境，添加 dataBinding 元素，开启 DataBinding：

```groovy
android {
    ...
    dataBinding {
        enabled = true
    }
}
```

注意：库模块跟 App 模块都需要开启。



#### 2. 新建一个 Activity 以及 XML 并写绑定逻辑

`basic_databinding.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="student"
            type="me.yifeiyuan.databinding.Student"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:text="@{student.name}"
            />
    </LinearLayout>

</layout>
```



#### 3&4. DataBindingUtil.contentView 并设置属性

```java
public class BasicDataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
				//自动生成的 XBinding 类
        BasicDatabindingBinding binding = DataBindingUtil.setContentView(this, R.layout.basic_databinding);

        Student student = new Student();
        student.name = "程序亦非猿";
				//生成了一个 setStudent 的方法
        binding.setStudent(student);
    }
}
```



通过` DataBindingUtil.setContentView`设置布局，AS 会生成一个根据我们的 XML `basic_databinding.xml`命名的 `Binding` 文件。(一个抽象类 XBinding，一个实现类 XBindingImpl，暂时不展开)

拿到这个 Binding，我们可以设置我们的 Student，这些类名字、方法名字都是有一定规律的。



运行 APP 就能看到这个 TextView 展示了 "程序亦非猿"，这样就是一个非常基本的 DataBinding 的运用例子了。



### DataBinding 表达式|语法



DataBinding 用在 XML ，那是本来我们用来写布局的，如果要用来写绑定的代码，必然会新加语法，这个最基本的语法就是，`Layout Expressions`（布局表达式），长这样： `@{}`，跟 React 的 JSX 也差不多。



看上面的例子，可以发现 DataBinding 使得 XML 的*根节点不再是以往的根布局*了，而是`<layout>` 节点，另外还新增了`data`节点，我们以往的根布局现在跟`data`同级了。



- `<layout>` 节点，**新的根节点**，替代了原本的根布局；

- `<data>` 节点，用于*存放可能被用到的属性的声明*；

  - `variable` 
    - `name`，相当于给变量命名
    - `type`，类的全路径

  

上面的例子里，`android:text="@{student.name}"` 就表示 text 跟 student.name 进行了绑定，name 是什么，text 就会展示什么。



student.name 会尝试绑定 name 属性、 name() 方法、getName() 方法。(谁优先呢？TODO)











### 配合 LiveData

LiveData 可以替代 observable fields 





### 相关类，原理？

DataBinderMapper

DataBinderMapperImpl

`ViewDataBinding`

BaseObservable

Observable

PropertyChangeRegistry

`TextViewBindingAdapter`



### 疑问

bind 的逻辑 属性 方法 getter 的优先级是怎么样的？

为什么说 DataBinding 的性能比较好？

为什么说 DataBinding 能防止空指针？(XBinding 的类里都判断了对象是否为 null)



### 资料

https://developer.android.com/topic/libraries/data-binding

https://developer.android.com/topic/libraries/data-binding/architecture

https://developer.android.com/topic/libraries/data-binding/generated-binding.html

https://developer.android.com/topic/libraries/data-binding/binding-adapters.html

https://developer.android.com/topic/libraries/data-binding/architecture.html

https://developer.android.com/topic/libraries/architecture/livedata.html#the_advantages_of_using_livedata

compilerv2：https://juejin.im/entry/5af91556f265da0b736dc25e

https://medium.com/androiddevelopers/data-binding-lessons-learnt-4fd16576b719