# Flutter Startup



按照 codelab 编写第一个 Flutter App。



### 基本的列表

根据 编写第一个 Flutter 应用 所描述的一点一点执行。



修改 `pubspec.yaml` 文件，增加`english_words: ^3.1.0` 依赖。

```yaml
{step1_base → step2_use_package}/pubspec.yaml
  dependencies:
    flutter:
      sdk: flutter
    cupertino_icons: ^0.1.2
+   english_words: ^3.1.0
```



把 main.dart 修改成如下代码：

```dart
// Copyright 2018 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final wordPair = WordPair.random();
    return MaterialApp(
      title: 'Welcome to Flutter',
      home: Scaffold(
        appBar: AppBar(
          title: Text('Welcome to Flutter'),
        ),
        body: Center(
          // child: Text(wordPair.asPascalCase),
          child: RandomWords(),
        ),
      ),
    );
  }
}

class RandomWordsState extends State<RandomWords> {
  final _suggestions = <WordPair>[];
  final _biggerFont = const TextStyle(fontSize: 18.0);

  // @override
  // Widget build(BuildContext context) {
  //   final wordPair = WordPair.random();
  //   return Text(wordPair.asPascalCase);
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Startup Name Generator'),
      ),
      body: _buildSuggestions(),
    );
  }

  Widget _buildSuggestions() {
    return ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemBuilder: (context, i) {
          if (i.isOdd) return Divider();

          final index = i ~/ 2;
          if (index >= _suggestions.length) {
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        });
  }

  Widget _buildRow(WordPair pair) {
    return ListTile(
      title: Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
    );
  }
}

class RandomWords extends StatefulWidget {
  @override
  RandomWordsState createState() => RandomWordsState();
}

```



Run 起来：

![2019-09-12 15_26_44](https://tva1.sinaimg.cn/large/006y8mN6ly1g6wrl2uefhg30aw0m6465.gif)





一个列表完成。



### 增加交互



Codelab 还有第二部分，增加了收藏、展示收藏功能。

按照 https://codelabs.flutter-io.cn/codelabs/first-flutter-app-pt2-cn/index.html#3 描述教程做。

修改后如下：

```dart
// Copyright 2018 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final wordPair = WordPair.random();
    return MaterialApp(
      title: 'Welcome to Flutter',
      theme: ThemeData(
        primaryColor: Colors.amberAccent,
      ),
      home: RandomWords()
    );
  }
}

class RandomWordsState extends State<RandomWords> {
  final _suggestions = <WordPair>[];
  final Set<WordPair> _saved = Set<WordPair>();
  final _biggerFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Startup Name Generator'),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.list),
            onPressed: _pushSaved,
          ),
        ],
      ),
      body: _buildSuggestions(),
    );
  }

  _pushSaved() {
    Navigator.of(context).push(new MaterialPageRoute<void>(
      builder: (BuildContext context) {
        final Iterable<ListTile> tiles = _saved.map(
          (WordPair pair) {
            return ListTile(
              title: Text(
                pair.asCamelCase,
                style: _biggerFont,
              ),
            );
          },
        );

        final List<Widget>divided = ListTile.divideTiles(
          context: context,
          tiles: tiles,
        ).toList();

        return Scaffold(
          appBar: AppBar(
            title: const Text('Saved Suggestions'),
          ),
          body: ListView(children: divided,),
        );
      },
    ));
  }

  Widget _buildSuggestions() {
    return ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemBuilder: (context, i) {
          if (i.isOdd) return Divider();

          final index = i ~/ 2;
          if (index >= _suggestions.length) {
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        });
  }

  Widget _buildRow(WordPair pair) {
    final bool alreadySaved = _saved.contains(pair);
    return ListTile(
      title: Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
      trailing: Icon(
        alreadySaved ? Icons.favorite : Icons.favorite_border,
        color: alreadySaved ? Colors.red : null,
      ),
      onTap: () {
        setState(() {
          if (alreadySaved) {
            _saved.remove(pair);
          } else {
            _saved.add(pair);
          }
        });
      },
    );
  }
}

class RandomWords extends StatefulWidget {
  @override
  RandomWordsState createState() => RandomWordsState();
}
```



Run 起来：

![img](https://tva1.sinaimg.cn/large/006y8mN6ly1g6wtcdp7rxg30cm0m8e6i.gif)





**提示:** 在 Flutter 的响应式风格的框架中，调用 `setState()` 会为 State 对象触发 `build()` 方法，从而导致对 UI 的更新。

**提示**: 某些 widget 属性需要单个 widget（child），而其它一些属性，如 action，需要一组widgets（children），用方括号 [] 表示。



### 总结



学了几个类`List<T>`、`Set<T>`、`TextStyle`，部件`ListTile`、`Icon`，点击事件处理`onTap`、`onPressed`。

语法上也延续了一些 Java 的风格，同时又看到了 React 的影子，`setState()` 方法调用后触发 `build()` 方法，就是 React 的路子啊。



### 资料

[编写第一个 Flutter 应用](https://flutter.cn/docs/get-started/codelab)

[编写你的第一个 Flutter App [2/2]](https://codelabs.flutter-io.cn/codelabs/first-flutter-app-pt2-cn/index.html#0)

