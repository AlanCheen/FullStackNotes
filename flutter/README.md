# Flutter









State*less* widgets 是不可变的，这意味着它们的属性不能改变 —— 所有的值都是 final。

State*ful* widgets 持有的状态可能在 widget 生命周期中发生变化，实现一个 stateful widget 至少需要两个类： 1）一个 StatefulWidget 类；2）一个 State 类，StatefulWidget 类本身是不变的，但是 State 类在 widget 生命周期中始终存在。



语法跟 Java 也类似，有泛型，继承用 extends



```dart
class FooWidget extends StatefulWidget {
  @override
  FooWidgetState createState() => FooWidgetState();
}

class FooWidgetState extends State<FooWidget> {
  @override
  Widget build(BuildContext context) {
    return Text(title:"Fitz");
  }
}
```



**提示:** 在 Flutter 的响应式风格的框架中，调用 `setState()` 会为 State 对象触发 `build()` 方法，从而导致对 UI 的更新。

**提示**: 某些 widget 属性需要单个 widget（child），而其它一些属性，如 action，需要一组widgets（children），用方括号 [] 表示。







### 学习资料



YouTuBe 频道：https://www.youtube.com/flutterdev



https://github.com/flutter/flutter/wiki/Release-Notes-Flutter-1.5.4

https://flutter-io.cn/docs



https://groups.google.com/forum/#!forum/flutter-announce

dart：https://flutter.dev/docs/resources/bootstrap-into-dart