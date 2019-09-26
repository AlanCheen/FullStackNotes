# 《JavaScript 高级程序设计设计》（第 3 版）

Professional JavaScript for Web Developers 3rd Edition —— Nicholas C.Zakas

[TOC]

## 第 1 章 JavaScript 简介

### 1.1 JavaScript 简史

JS 诞生于 1995 年，作者是 Netscape 的 Brendan Eich，JS 前生 LiveScript，为了蹭 Java 流量改名 JavaScript。

1997 年以 JavaScript1.1 为蓝本被提交给欧洲计算机制造协会（ECMA），完成了 ECMA-262——定义了一种名为 ECMAScript 的新脚本语言的**标准**。（ES5 ES6 说的就是它了）



### 1.2 JavaScript 实现

虽然 JS 跟 ES 通常被人们用来表达相同的含义，但是实际上 JavaScript 的含义比 ECMA-262 规定的含义多的多，完整的 JS 实现由下面三部分组成：

1. 核心（ECMAScript）
2. 文档对象模型（DOM）
3. 浏览器对象模型（BOM）

![image-20190924193514379](https://tva1.sinaimg.cn/large/006y8mN6ly1g7au6yer85j30cy05q0sy.jpg)

由 ECMA-262 定义的 ES 跟 Web 浏览器**没有依赖关系**。它不包含输入输出定义，只是定义了基础。

Web 浏览器只是 ES 实现可能的**宿主环境**之一，类似的宿主环境还有 Node 和 Adobe Flash。

宿主环境不仅提供基本的 ES 实现，同时也提供扩展，以便语言与环境之间对接交互，例如 DOM，它利用 ES 的核心类型和语法提供更多功能。



ECMA-262 定义了：

1. 语法
2. 类型
3. 语句
4. 关键字
5. 保留字
6. 操作符
7. 对象



#### 1.2.2 文档对象模型（DOM）

**文档对象模型**（DOM，Document Object Model）是**针对 XML 但经过扩展用于 HTML 的应用程序编程接口**（API，Application Programming Interface）。**DOM 把整个页面映射为一个多层节点结构**。HTML 或 XML 页面中每个组成部分都是某种类型的节点，这些节点又包含着不同类型的数据。

例如：

```html
<html>
  <head>
    <title>Sample Page</title>
  </head>
  <body>
    <p>
      Hello World!
    </p>
  </body>
</html>
```

生成的分层节点图：

```
html
|
|--head
|   |____title
|						|___Sample Page
|
|--body
|   |___p
|				|___Hello World!
```



通过 DOM 创建的这个表示文档的树形图，开发者**获得了控制页面内容和结构的主动权**。通过 DOM 提供的 API，开发可以轻松**删除、添加、替换和修改任何节点**。（有点类似 View Tree）



##### 1.为什么要用 DOM？

Netscape 和微软分别支持不同形式的 DHTML（Dynamic HTML），他们各执己见，导致两个平台互不兼容，如果要保持跨平台，需要大量额外的开发时间，于是 W3C 开始规划 DOM。 



##### 2.DOM 级别

**DOM1 级**（DOM Level 1）于 1998年 10 月成为 W3C 的推荐标准。

DOM1 级由两个模块组成：

1. **DOM 核心**（DOM Core）：规定如何映射基于 XML 的文档结构，以便简化对文档中任意部分的访问和操作；
2. **DOM HTML**：在DOM Core 的基础上加以扩展，添加了针对 HTML 的对象和方法。



如果说 DOM1 级的目标主要是映射文档的结构，那么 DOM2 级的目标则宽泛得多。

**DOM2 级**在 原来 DOM 的基础上又扩充了(DHTML 一直都支持的)**鼠标**和**用户界面事件**、**范围**、**遍历**(迭代 DOM文档的方法)等细分模块，而且通过对象接口增加了对 CSS(Cascading Style Sheets，层叠样式表)的 支持。DOM1 级中的 DOM 核心模块也经过扩展开始**支持 XML 命名空间**。

DOM2 级引入了下列新模块，也给出了众多新类型和新接口的定义。

- **DOM 视图**（DOM Views）：定义了跟踪不同文档（例如，应用 CSS 之前和之后的文档）视图的接口；
- **DOM 事件**（DOM Events）：定义了**事件和事件处理的接口**；
- **DOM 样式**（DOM Style）：定义了基于 CSS 为元素应用样式的接口；
- **DOM 遍历和范围**（DOM Traversal and Range）：定义了遍历和操作文档树的接口。



**DOM3 级** 进一步扩展了 DOM，引入以同一方式加载和保存文档的方法——在 DOM 加载和保存（DOM Load and Save）模块中定义；新增了验证文档的方法——在 DOM 验证(DOM Validation)模块中定义。DOM3 级也对 DOM 核心进行了扩展，开始支持 XML 1.0 规范，涉及 XML Infoset、XPath 和 XML Base。



**DOM 标准需要浏览器实现并支持。**（这也是个兼容性问题的来源）



#### 1.2.3 浏览器对象模型（BOM）



**浏览器对象模型**（BOM，Browser Object Model），开发人员使用 BOM 可以**控制浏览器显示的页面以外的部分**。BOM 原先没有标准，直到 HTML5 才致力于将 BOM 的功能写进规范。



从根本上讲，BOM 只处理浏览器窗口和框架;但人们习惯上也把所有针对浏览器的 JavaScript 扩展算作 BOM 的一部分。下面就是一些这样的扩展:

- 弹出新浏览器窗口的功能;
-  移动、缩放和关闭浏览器窗口的功能;
-  提供浏览器详细信息的 navigator 对象;
-  提供浏览器所加载页面的详细信息的 location 对象;
-  提供用户显示器分辨率详细信息的 screen 对象;
-  对 cookies 的支持;
-  像 XMLHttpRequest 和 IE 的 ActiveXObject 这样的自定义对象。



### 1.4 小结

JavaScript 是一种专为与网页交互而设计的脚本语言，由下列三个不同的部分组成:

- **ECMAScript**，由 ECMA-262 定义，提供核心语言功能; 
- **文档对象模型**(DOM)，提供访问和操作网页内容的方法和接口;
- **浏览器对象模型**(BOM)，提供与浏览器交互的方法和接口。



如果用 Java 的思想来理解它们的关系，那么应该是这样的：

```java
public class JavaScript implements ECMAScript,DOM,BOM{}
```



## 第 2 章 在 HTML 中使用 JavaScript



### 2.1 `<script>` 元素

向 HTML 中插入 JavaScript 的主要方法，就是使用`<script>`元素。

`script` 的属性：

1. **async**：可选。表示应该立即下载脚本，但不应妨碍页面中的其他操作，比如下载其他资源或 等待加载其他脚本。**只对外部脚本文件有效**。
2. **charset**：可选。表示通过 src 属性指定的代码的**字符集**。由于大多数浏览器**会忽略它**的值， 因此这个属性**很少有人用**。
3. **defer**：表示脚本可以**延迟到文档完全被解析和显示之后再执行**。只对外部脚本文件有效。IE7 及更早版本对嵌入脚本也支持这个属性。
4. **language**：已废弃。原来用于表示编写代码使用的脚本语言(如 JavaScript、JavaScript1.2 或 VBScript)。大多数浏览器会忽略这个属性，因此也没有必要再用了。
5. **src**：可选。表示包含要执行代码的**外部文件**。
6. **type**：可以看成是 language 的替代属性；表示编写代码使用的脚本语言的内容类型(也
   称为 MIME 类型)。默认为`text/javascript`，也是兼容性最好的值；



使用`<script>`元素的方式有两种:

1. 直接在页面中嵌入 JavaScript 代码：只须指定 `type` 属性，就可以直接在元素内编写 JS 代码；
2. 包含外部 JavaScript文件：需要指定 `src` 属性，指向外部 JavaScript 文件的链接，需要注意安全性；



内部嵌入的方式：

```js
<script type="text/javascript">
    function sayScript(){
	alert("</script>");
}
</script> 
```

外部引用：

```js
<script type="text/javascript" src="example.js"></script>
```



无论如何包含代码，**只要不存在 defer 和 async 属性，浏览器都会按照<script>元素在页面中 出现的先后顺序对它们依次进行解析。**换句话说，在第一个<script>元素包含的代码解析完成后，第 二个<script>包含的代码才会被解析，然后才是第三个、第四个......

#### 2.1.2 延迟脚本



#### 2.1.3 异步脚本









