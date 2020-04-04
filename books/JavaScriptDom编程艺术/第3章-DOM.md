# 第3章-DOM


## 文档：DOM 中的 D
D 文档 document ，当创建了一个网页并把它加载到 Web 浏览器中时，DOM 就在幕后生成，它把你编写的网页文档转换为一个文档对象。（加载到内存？）

## 对象：DOM 中的 O

- 用户定义对象
- 内建对象
- 宿主对象

最基础的宿主对象： window对象，对应浏览器窗口本身。（类似 Android 的 PhoneWindow？）

window 对象的属性和方法通常称为 BOM（浏览器对象模型）。

BOM 提供了如 window.open window.blur 等方法。

更重要的是 document 对象。


## 模型：DOM 中的 M

模型：某种事物的表现形式。 比如模型火车就代表一列真正的火车。

DOM 把一份文档表示为一棵树（数学意义上的树，类似二叉树）

家谱书模型 非常适合用来表示一份用(X)HTML 语言编写出来的文档。（parent child sibling）

节点树。

## 节点node

节点相当于现实世界组成万物的『原子』。

文档是『由节点构成的集合』。

节点的类型：元素节点、文本节点和属性节点。

#### 元素节点（element node）

元素节点，DOM 的原子是元素节点。 如 `<p> `,`<body>`。标签的名字就是元素的名字。

#### 文本节点 （text node）

`<p>这里是文本</p>` 包含文本的就是文本节点，文本节点总是被包含在元素节点的内部。

#### 属性节点 （attribute node）

属性节点用来对元素做出更具体的描述。比如 title href id class 等属性。

属性节点总是被包含在元素节点中。

## CSS

可以通过CSS（层叠样式表）告诉浏览器应该如何显示一份文档的内容。

语法：
```
selector{
    property:value;
}

//指定 class 元素
.class{

}

// 指定 id 元素
#id{

}
```

CSS 可以继承，比如 body ,不仅作用于那些直接包含在 body 标签里的内容，还将作用于嵌套在 body 元素内部的所有元素。

## 获取元素

跟 Android 中的 findViewById 获取 View类似 ，JS 中也有方法来获取文档中的元素。

- getElementById 通过 id 获取,返回唯一的元素
- getElementByTagName 通过标签名获取，如`<p>`,返回一个对象数组
- getElementByClassName 通过 class 属性来获取，返回一个对象数组；可以指定多个类名

## 获取和设置属性

getAttribute setAttribute

setAttribute 做出的修改不会反映在文档本身的源代码里。

DOM的工作模式：先加载文档的静态内容，再动态刷新，动态刷新不影响文档的静态内容。这正是 DOM 的真正威力：对页面内容进行刷新却不需要在浏览器里刷新页面。

## 小结

JS 与 Android 相同，同为一门前端语言，其实有很多类似的设计，可以类比来学习理解。

感觉可以把 html中的 body 部分理解为 Android 中的 XML，用来描述网页的布局，标签元素可以理解为 Android 中的 View，CSS 可以理解为 style，这样更容易去理解，虽然可能不是很准确。








