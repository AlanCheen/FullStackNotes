# 掌握 DataBinding 实践篇

[TOC]

[掌握 DataBinding 理论篇](./databinding.md)

代码 GitHub：https://github.com/AlanCheen/LearnDataBinding



本文是关于 DataBinding 基础使用的笔记，分为两部分。



1. **使用场景**，教大家在不同场景下如何使用 DataBinding；
2. **使用技巧**，教大家 DataBinding 的各种奇淫技巧。





**使用场景**：

- [x] 在 Activity 中使用 DataBinding；
- [x] 在 Fragment 中使用 DataBinding；
- [x] 与 RecyclerView 结合使用 DataBinding；



**使用技巧**：

- [ ] 表达式





### DataBinding 环境配置

DataBinding 默认是不启用的，需要我们配置一下，手动开启。



条件：

1. Gradle 至少是 1.5 版本
2. 启用 dataBinding



开启方法：在 android 闭包下添加 dataBinding 元素，开启 DataBinding：

```groovy
android {
    ...
    dataBinding {
        enabled = true
    }
}
```

注意：库模块跟 App 模块都需要开启，否则你可以在编译的时候遇到类似这个样子的错误：

```
error: Error: No resource type specified (at 'xxx' with value '@={xxxx}')
```



### 在 Activity 中里使用 DataBinding



大概的步骤：

1. 新建 Activity & XML
2. 按 DataBinding 的方式编写 XML，比如添加 `layout` `data`节点， 写绑定逻辑；
3. 通过 `DataBindingUtil.setContentView` 给 Activity 设置布局；
4. 给自动生成的 Binding 设置变量；
5. Run App !



#### 1&2. 新建一个 Activity 以及 XML 并写绑定逻辑

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

需要按照 DataBinding 的要求编写 XML，在 data 标签声明要用到的对象，在布局里用`@{expression}`表达式写绑定逻辑。

跟我们之前的不一样，根节点变成了`layout`，并且多了一个`data`标签，data 里存放了我们要用到的对象声明，可以理解为一个在 XML 里的对象，我们可以使用它，类似在 Java 中使用。



小技巧：可以通过 AS 一键转换成 data binding layout：

![image-20190620211341946](/Users/mingjue/self/FullStackNotes/android/assets/image-20190620211341946.png)



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



通过` DataBindingUtil.setContentView()`设置布局，取代原来的 `Activity.setContentView()`。

AS 会生成一个根据我们的 XML `basic_databinding.xml`命名的 `Binding` 文件`BasicDatabindingBinding`。

这个**生成的类的名字命名规则**：*把 XML 名字改成 Pascal 命名，然后再添加 Binding 作为后缀*，比如这里最终的是 *BasicDatabindingBinding*。



拿到这个 Binding，我们需要设置我们的 Student（*Binding 为我们设置的 variable 生成了 `setter` 方法*），这样就完成了 UI 跟数据的绑定。



运行 APP 就能看到这个 TextView 展示了 `"程序亦非猿"`，这样就是一个非常基本的 DataBinding 的运用例子了。



#### 初识 DataBinding 的表达式



DataBinding 用在 XML ，那是本来我们用来写布局的，如果要用来写绑定的代码，必然会新加语法，这个最基本的语法就是，`Layout Expressions`（布局表达式），长这样： `@{expressions}`，比如`android:text="@{student.name}"`表示 `text` 跟 `student.name` 绑定，跟 React 的 JSX 差不多。



看上面的例子，可以发现 DataBinding 使得 XML 的*根节点不再是以往的根布局*了，而是`<layout>` 节点，另外还新增了`data`节点，我们以往的根布局现在跟`data`同级了。



- `<layout>` 节点，**新的根节点**，替代了原本的根布局；

- `<data>` 节点，用于*存放可能被用到的属性的声明*；

  - `variable` 
    - `name`，相当于给变量命名
    - `type`，类的全路径

  

上面的例子里，`android:text="@{student.name}"` 就表示 text 跟 student.name 进行了绑定，name 是什么，text 就会展示什么。student.name 会尝试绑定 name 属性、 name() 方法、getName() 方法。



可以先简单了解，后面还有表达式的更多用法。



### 在 Fragment 里使用 DataBinding



大概步骤：

1. 重写 `Fragment.onCreateView` 方法
2. 通过`Binding.inflate`方法获取生成的 Binding 对象；
3. 通过 `Binding.getRoot()` 方法获取 View；



跟 Activity 类似，我们需要创建 Fragment 以及 databinding 的 XML，不同的是，Fragment 不是 setContentView 的。

而是要利用 `Binding.inflate` 方法来获取 Binding 对象，并通过 `Binding.getRoot()`方法来获取 被 inflate 的 View 。

举个例子：

```java
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
  	//inflate 方法
    final FragmentDbBinding binding = FragmentDbBinding.inflate(getLayoutInflater(), container, false);
    final Student student = Student.create();
    binding.setStudent(student);
		//getRoot 获取 View
    View root = binding.getRoot();
    return root;
}
```

XML 很简单，就省略了。

### 配合 RecyclerView 使用 DataBinding







### DataBinding 所支持的表达式

https://developer.android.com/topic/libraries/data-binding/expressions

- Mathematical `+ - / * %`
- String concatenation `+`
- Logical `&& ||`
- Binary `& | ^`
- Unary `+ - ! ~`
- Shift `>> >>> <<`
- Comparison `== > < >= <=` (Note that `<` needs to be escaped as `&lt;`)
- `instanceof`
- Grouping `()`
- Literals - character, String, numeric, `null`
- Cast
- Method calls
- Field access
- Array access `[]`
- Ternary operator `?:`



举例：

```xml
android:text="@{String.valueOf(index + 1)}"
android:visibility="@{age > 13 ? View.GONE : View.VISIBLE}"
android:transitionName='@{"image_" + id}'
```



#### 不支持的操作

The following operations are missing from the expression syntax that you can use in managed code:

- `this`
- `super`
- `new`
- Explicit generic invocation



#### Null 操作

`??` 来判断对象是否为 null

```xml
android:text="@{user.displayName ?? user.lastName}"
```

相当于：

```xml
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```



### Observable data objects

https://developer.android.com/topic/libraries/data-binding/observability.html

在这之前，当我们修改 Model UI 并不会更新，因为到这才是单向绑定，如果需要让 UI 随着我们 Model 的改动而自动更新，需要实现双向绑定，这时候我们需要使用到 Observable data objects。



DataBinding 提供了把对象、字段和集合变得可观察，来实现双向绑定，并提供了一些内置的对象：

- [`ObservableBoolean`](https://developer.android.com/reference/androidx/databinding/ObservableBoolean.html)
- [`ObservableByte`](https://developer.android.com/reference/androidx/databinding/ObservableByte.html)
- [`ObservableChar`](https://developer.android.com/reference/androidx/databinding/ObservableChar.html)
- [`ObservableShort`](https://developer.android.com/reference/androidx/databinding/ObservableShort.html)
- [`ObservableInt`](https://developer.android.com/reference/androidx/databinding/ObservableInt.html)
- [`ObservableLong`](https://developer.android.com/reference/androidx/databinding/ObservableLong.html)
- [`ObservableFloat`](https://developer.android.com/reference/androidx/databinding/ObservableFloat.html)
- [`ObservableDouble`](https://developer.android.com/reference/androidx/databinding/ObservableDouble.html)
- [`ObservableParcelable`](https://developer.android.com/reference/androidx/databinding/ObservableParcelable.html)



使用的时候：

```java
private static class User {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
}
```



需要注意，它们是对象，不能像基本类型直接使用，而是通过`set`和`get`方法。

```java
//实例化 
public ObservableInt weight = new ObservableInt(70);
// get() set() 方法
public void loseWeight() {
  int pre = weight.get();
  weight.set(--pre);
}
```



### LiveData

https://developer.android.com/topic/libraries/data-binding/architecture.html#livedata

LiveData 可以在部分场景下替代掉 observable fields 。







### Binding classes

https://developer.android.com/topic/libraries/data-binding/generated-binding.html



### Binding Adapters

https://developer.android.com/topic/libraries/data-binding/binding-adapters



属性跟布局的绑定实际上是由 BindingAdatpters 来完成的。





### 双向绑定

https://developer.android.com/topic/libraries/data-binding/two-way









### More



**导包**：

`java.lang` 下的类，比如 String 是不用手动导入的。



**设置默认值**：

```xml
android:text="@{user.firstName, default=my_default}"
```





### 踩的坑



#### TextView 设置 text

当属性是个 int 类型，设置给 text 会报错，系统会误以为是资源 ID`android.content.res.Resources$NotFoundException: String resource ID #0x42`:

```
//student.age 是 int 类型
android:text="@{student.age}"
```

修正：

```
andorid:text="@{String.valueOf(student.age)}"
```



get 方法必须用`@Bindable`修饰，set 方法必须调用 notify 方法，否则不能实现双向绑定。





### 资料

https://github.com/googlesamples/android-databinding

https://developer.android.com/topic/libraries/data-binding/two-way