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
2. 包含外部 JavaScript文件：需要指定 `src` 属性，指向外部 JavaScript 文件的链接，需要**注意安全性**；



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



#### 2.1.1 标签的位置

传统的做法中，`<script>`元素都放在`head`元素中，这意味着必须等待所有的 JS 文件被下载、解析和执行完成后，才能开始呈现页面的内容（浏览器在遇到 body 标签才开始呈现内容）。

这样如果 JS 代码非常多的页面就会导致页面内容呈现出现明显的延迟，为此现代的做法都是把 JS 引用都放在`body`元素页面的内容后面。

```html
<body>
  //这里是网页内容
  ...
  
  //底部放 JS 引用
  <script src="foo.js"></script>
</body>

```

这样 JS 就不会阻碍页面的渲染了。



#### 2.1.2 延迟脚本 defer

HTML4.01 为 script 定义了 defer 属性，只适用于外部脚本。它用来表面脚本在执行时不会影响页面的构造，也即脚本会被延迟到整个页面都解析完毕后再运行。具体是遇到`</html>`后再执行，相当于告诉浏览器立即下载，但延迟执行。



虽然 H5 规范要求脚本按它们的出现顺序执行，然而现实不一定是这样，因此最好只包含一个延迟脚本。

因此，**把延迟脚本放在页面底部仍然是最佳选择**。



注意：

1. 在 XHTML 文档中，要把 defer 属性设置为 defer="defer"。
2. 脚本顺序不一定以出现顺序执行；



#### 2.1.3 异步脚本 async

HTML5 为 script 元素定义了 `async` 属性，只适用于**外部脚本**文件，并告诉浏览器**立即下载**文件。

指定 async 属性的目的是**不让页面等待脚本的下载和执行**，从而异步加载页面的其他内容。



但是跟 defer 不同的是，异步脚本**不保证它们的执行顺序**。例如：

```html
<script> </script> A
<script> </script> B
```

A 不一定在 B 之前执行。



需要**注意**：

1. 异步脚本不保证脚本执行顺序；
2. 异步脚本之间不要有依赖；
3. 异步脚本不要在加载期间修改 DOM；
4. 在 `XHTML` 文档中，要把 async 属性设置为 `async="async"`。
5. 异步脚本**一定**会在页面的 `load` 事件前执行，但可能会在 `DOMContentLoaded` 事件触发之前或之 后执行



可以用 Java 多线程理解，开了线程，但是不保证执行的顺序。



| 属性  | 下载     | 执行                              |
| ----- | -------- | --------------------------------- |
| defer | 立即下载 | 延迟执行，遇到`</html>`后再执行； |
| async | 立即下载 | 不保证先后顺序执行                |



#### 2.1.4 在 XHTML 中的用法

可扩展超文本标记语言（XHTML），将 HTML 作为 XML 的应用而重新定义的一个标准。它比 HTML 更加严格，有些能在 HTML 中用的不能在 XHTML 中使用。

为保证在 XHTML 中正常运行，可以采用 `CData` 片段来包含 JS 代码。CData 片段是文档中的一个特殊区域，这个区域中可以包含不需要解析的任意格式的文本内容。

```js
<![CDATA[
  function foo(){}
]]>
```



在将页面的 MIME 类型指定为"application/xhtml+xml"的情况下会触发XHTML 模式。并不是所有浏览器都支持以这种方式提供 XHTML 文档。

后面的发展应该还是 HTML5，所以 XHTML 了解下就行。



### 2.2 嵌入代码与外部文件

虽然这两种方式都行，但是更推荐外部文件的方式。

1. **可维护性**：把 JS 文件放在一个文件夹下，维护起来轻松得多。并且把 HTML 分离开，可以集中精力编辑 JS 代码；
2. **可缓存**：浏览器能够根据具体的设置缓存链接的所有外部的 JavaScript 文件。也即如果有两个页面使用同一个文件，则只需要下载一次，这就能够加速页面的加载速度；
3. **适应未来**：通过外部文件来包含 JavaScript 无需使用 XHTML 或注释 hack，因为 HTML 和 XHTML 包含外部文件的语法是相同的。



### 2.3 文档模式(DOCTYPE)



IE5.5 引入了文档模式的概念，而这个概念通过使用文档类型（doctype）切换实现的。

最初的两种模式：

1. 混杂模式（quirks mode）：浏览器的默认行为，让 IE 的行为与 IE5 相同，不推荐，因为不同浏览器的行为差异巨大；
2. 标准模式（standards mode）：让 IE 的行为更接近标准行为。



后面还推了一个准标准模式，跟标准模式非常接近，差异几乎可以忽略不计。



**标准模式**可以通过使用下面的任意一种文档类型开启：

```html
<!-- HTML 4.01 严格型 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<!-- XHTML 1.0 严格型 -->
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- HTML 5 -->
<!DOCTYPE html>
```



尝试用 WebStorm 新建一个 HTML 文件，发现它用的是`<!DOCTYPE html>`，也即是 HTML 5。

准标准就不记 了。

### 2.4 `<noscript>`模式

早期浏览器不支持 JS，noscript 可以用在不支持 JS 的浏览器中显示替代的内容。

在如下情况下noscript 元素才会显示出来：

1. 浏览器不支持脚本；
2. 浏览器支持脚本，但脚本被禁用；



```html
<body>
  <noscript>
    <p>本页面需要浏览器支持（启用）JavaScript</p>
  </noscript>
</body>
```

这个页面会在脚本无效的情况下向用户显示一条消息。而在启用了脚本的浏览器中，用户永远也不 会看到它——尽管它是页面的一部分。



### 2.5 小结

在 HTML 中使用 JS 有两种方法，嵌入式和外部引入 。需要注意：

1. 在包含外部 JS 文件时，必须将 src 属性设置为指向相应文件的 URL。它可以是同服务器上的文件，也可以是其他地方的文件；

2. 所有`script`元素都会按照它们在页面中出现的**先后顺序**依次被解析。在不适用 defer 和 async 属性的情况下，只有在解析完`script`元素中的代码之后，才会开始解析后面`script`元素中的代码。

3. 由于浏览器会先解析完不使用 defer 属性的`<script>`元素中的代码，然后再解析后面的内容， 所以一般应该把`<script>`元素放在页面最后，即主要内容后面，`</body`>标签前面。

4. 使用 `defer` 属性可以让脚本在文档完全呈现之后再执行。延迟脚本总是按照指定它们的顺序执行。

5. 使用 `async` 属性可以表示当前脚本不必等待其他脚本，也不必阻塞文档呈现。不能保证异步脚

   本按照它们在页面中出现的顺序执行。

6. `noscript`可以指定在不支持脚本的浏览器中显示替代内容。

## 第 3 章 基本概念



### 3.1 语法

1. **区分大小写**，
2. **标识符**，指变量、函数、属性的名字或函数的参数，它的规则：
   1. 第一个字符必须是一个字母、下划线（_）或一个美元符号（$）;
   2. 其他字符可以是字母、下划线、美元符号或数字；
   3. ECMAScript 标识符采用**驼峰法则**；
3. **注释**，有单行注释跟多行注释，跟 Java 一样，`//`、`/***/`；
4. **严格模式**，ES5 引入了严格模式（strict mode），严格模式是为 JavaScript 定义了一种不同的解析与执行模型。在严格模式下，ES3 中的一些不确定的行为将得到处理，而且对某些不安全的操作也会抛出错误。在脚本顶部增加`“use strict”`可以开启严格模式；它是一个编译指示，告诉 JS 引擎切换到严格模式；这是为不破坏 ECMAScript 3 语法而特意选定的语法。严格模式也可以在单独的函数下执行 ：

```js
function foo(){
  "use strict"
  //
}
```

5. **语句**，ECMAScript 中的语句以一个分号结尾；分号非必须，但是建议保留。



### 3.2 关键字和保留字

![image-20190930110354319](/Users/mingjue/self/FullStackNotes/books/assets/image-20190930110354319.png)



### 3.3 变量



1. ES 的变量是松散类型的（弱类型的），可以用来保存任何类型，定义变量用`var` 操作符，忽略 var 的会创建一个**全局变量，但是不推荐；
2. 未初始化的变量会保存一个特殊的值——`undefined`;



```js
var message = "hi";
message = 111;// 可以改变类型
var foo; //undefined
```



### 3.4 数据类型



ES 中有 5 中简单类型（基本类型）和一种复杂类型——Object：

1. **Undefined**   
2. **Null** 
3. **Boolean**
4. **Number** 
5. **String**
6. **Object**（复杂类型），本质上是由一组无序的名值对组成的。



ECMAScript不支持任何创建自定义类型的机制，而所有值最终都将是上述 6 种数据类型之一。



#### 3.4.1 typeof 操作符



`typeof` 是 ES 用来`检测变量的数据类型`的**操作符**，它会返回某个字符串。

| 返回值      | 代表                               |
| ----------- | ---------------------------------- |
| "undefined" | 值未定义（后面还提到了变量未定义） |
| "boolean"   | 值是布尔值                         |
| "string"    | 值是字符串                         |
| "number"    | 值是数值                           |
| "object"    | 值是对象或 null                    |
| "function"  | 值是函数                           |

有些时候，typeof 操作符会返回一些令人迷惑但技术上却正确的值。比如，调用 typeof null会返回"object"，因为特殊值 null 被认为是一个空的对象引用。

在 console 里尝试了下：

![image-20190930142851833](/Users/mingjue/self/FullStackNotes/books/assets/image-20190930142851833.png)

### 3.4.2 Undefined 类型



`Undefined` 类型**只有一个值**，即特殊的 `undefined`。在使用 var 声明变量但未对其加以初始化时，这个变量的值就是 undefined。

```js
var message;
alert(message == undefined); //true
```



`undefined`值在 ES3 引入，为了正式区分`空对象指针`与`未经过初始化`的变量。



### 3.4.3 Null 类型

Null 类型也只有一个值——`null`。它表示一个空对象指针。typeof(null) 返回"object"。

如果定义的变量准备在将来用于保存对象，那么最好出初始化为 null。

```js
var car = null;
```



实际上 **null 派生自 undefined** ，所以如下的等式是成立的，返回 true 。

```js
alert(null==undefined) //true
```



尽管 null 和 undefined 有这样的关系，但它们的用途完全不同。如前所述，无论在什么情况下 都没有必要把一个变量的值显式地设置为 undefined，可是同样的规则对 null 却不适用。换句话说， **只要意在保存对象的变量还没有真正保存对象，就应该明确地让该变量保存 null 值**。这样做不仅可以体现 null 作为空对象指针的惯例，而且也有助于进一步区分 null 和 undefined。



#### 3.4.4 Boolean 类型

Boolean 类型只有`true`和`false`两个值。

其他类型可以跟 Boolean 转换，调用`Boolean()`函数：

```js
var msg = "HI";
var magBoolean = Boolean(msg);//true
```



**转换规则**：

| 数据类型  | 转换为 true 的值            | 转换为 false 的值 |
| --------- | --------------------------- | ----------------- |
| Boolean   | true                        | false             |
| String    | 任何非空字符串              | ""（空字符串）    |
| Number    | 任何非 0 数字（包含无穷大） | 0 和 NaN          |
| Object    | 任何对象                    | null              |
| Undefined | n/a                         | undefined         |

注意：n/a 或 N/A，是 not applicable 的缩写，意思是“不适用”。



**控制流程语句会自动执行相应的 Boolean () 转换成 Boolean 类型**。

```js
var msg = "Hello";
if(msg){ //自动转换 Boolean 类型
  alert("msg is true");
}
```

很方便。



#### 3.4.5 Number 类型

Number 类型使用 `IEEE754` 格式来表示 `整数`和`浮点数值`(浮点数值在某些语言中也被称为`双精度数`，类似 Java 里的 double )。为支持各种数值类型，ECMA-262 定 义了不同的数值字面量格式。



**整数**：

1. 十进制，直接写数字；
2. 八进制，第一位必须是 `0`，在严格模式下无效；
3. 十六进制，`0x`开头；

在进行算术计算时，所有以八进制和十六进制表示的数值最终都将被**转换成十进制数值**。



**浮点数**：

浮点数必须包含一个小数点，并且小数点后必须至少有一位数字。

浮点数值需要的内存空间是保存整数值的两倍，因此 ES 会不失时机地将浮点数转换为整数值。

例如 1.0 会被转换为整数。

ES 也支持科学计数法。



```js
var fN = 1.1
var fN2 = 3.1125e7 //等于 31250000
var fN3 = 3e-7 //0.0000007
```



注意，浮点数的**精度远不如整数**。例如 **0.1+0.2 的结果不是0.3，而是 0.30000000000000004**。

所以不要写这种代码：

```js
if（a+b==0.3）{
  
}
```



**数值范围**：

1. NaN
2. Number.MIN_VALUE，5e-324
3. Number.MAX_VALUE，1.7976931348623157e+308
4. Number.POSITIVE_INFINITY，正无穷
5. Number.NEGATIVE_INFINITY，负无穷



一个数如果不在 Min-Max 之间就变成了 Infinity。





#### 3.4.6 **String**类型

String 类型用于表示由零或多个 16 位 Unicode 字符组成的字符序列，即字符串。字符串可以由双

引号(")或单引号(')表示。



#### 3.4.7 Object 类型