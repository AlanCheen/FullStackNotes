# 掌握 DataBinding 理论篇

> Android 4.0 (API level 14) or higher

### 概述

> The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.



DataBinding 是一个可以允许使用声明式（declarative）让我们在 layout 里把 UI 控件跟数据源绑定的库。 



- Declarative UIs inside XML
- Binding the gap between code and XML



DataBinding 的优势：

- 在 layout 里做绑定可以省去很多代码，提升可维护性；
- 性能更好，防止空指针。



### 支持的表达式

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

LiveData 可以替代 observable fields 







### Binding classes

https://developer.android.com/topic/libraries/data-binding/generated-binding.html



### Binding Adapters

https://developer.android.com/topic/libraries/data-binding/binding-adapters



属性跟布局的绑定实际上是由 BindingAdatpters 来完成的。





### 双向绑定

https://developer.android.com/topic/libraries/data-binding/two-way





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

文中 Demo 地址：https://github.com/AlanCheen/LearnDataBinding

https://codelabs.developers.google.com/codelabs/android-databinding/#2