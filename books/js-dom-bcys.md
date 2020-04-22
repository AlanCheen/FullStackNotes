# JavaScript DOM编程艺术（第 2 版）

JavaScript DOM编程艺术(第2版)
Jeremy Keith  Jeffrey Sambells
103个笔记


## 第一章 JavaScript 简史

◆ 1.2 DOM

DOM是一套对文档的内容进行抽象和概念化的方法。


◆ 1.3 浏览器战争

>> ❑ 利用HTML把网页标记为各种元素；❑ 利用CSS设置元素样式和它们的显示位置；❑ 利用JavaScript实时地操控页面和改变样式。


◆ 1.4 制定标准

>> W3C对DOM的定义是：“一个与系统平台和编程语言无关的接口，程序和脚本可以通过这个接口动态地访问和修改文档的内容、结构和样式。”W3C推出的标准化DOM，在独立性和适用范围等诸多方面，都远远超出了各自为战的浏览器制造商们推出的各种专有DOM。


## 第2章 JavaScript 语法

◆ 2.1 准备工作

>> 但最好的做法是把<script>标签放到HTML文档的最后，</body>标签之前：

>> 这样能使浏览器更快地加载页面（第5章将详细讨论这个问题）。

>> 程序设计语言分为解释型和编译型两大类。Java或C++等语言需要一个编译器（compiler）。编译器是一种程序，能够把用Java等高级语言编写出来的源代码翻译为直接在计算机上执行的文件。解释型程序设计语言不需要编译器——它们仅需要解释器。对于JavaScript语言，在互联网环境下，Web浏览器负责完成有关的解释和执行工作。浏览器中的JavaScript解释器将直接读入源代码并执行。浏览器中如果没有解释器，JavaScript代码就无法执行。用编译型语言编写的代码有错误，这些错误在代码编译阶段就能被发现。而解释型语言代码中的错误只能等到解释器执行到有关代码时才能被发现。与解释型语言相比，编译型语言往往速度更快，可移植性更好，但它们的学习曲线也往往相当陡峭。


◆ 2.2 语法

>> 2.2.1 语句用JavaScript编写的脚本，与其他语言编写出来的脚本一样，都由一系列指令构成，这些指令叫做语句（statement）。只有按照正确的语法编写出来的语句才能得到正确的解释。

>> 把那些会发生变化的东西称为变量（variable）。

>> 把值存入变量的操作称为赋值（assignment）。

>> 请注意，JavaScript允许程序员直接对变量赋值而无需事先声明。这在许多程序设计语言中是不允许的。有很多语言要求在使用任何变量之前必须先对它做出“介绍”，也称为声明（declare）。

>> 有些其他的语言要求在声明变量的同时还必须同时声明变量的数据类型，这种做法称为类型声明（typing）。必须明确类型声明的语言称为强类型（strongly typed）语言。JavaScript不需要进行类型声明，因此它是一种弱类型（weakly typed）语言。这意味着程序员可以在任何阶段改变变量的数据类型。


◆ 2.6 函数

>> 2.6 函数

>> 变量的作用域

>> 全局变量（global variable）可以在脚本中的任何位置被引用。一旦你在某个脚本里声明了一个全局变量，就可以从这个脚本中的任何位置——包括函数内部——引用它。全局变量的作用域是整个脚本。局部变量（local variable）只存在于声明它的那个函数的内部，在那个函数的外部是无法引用它的。局部变量的作用域仅限于某个特定的函数。


◆ 2.7 对象

>> 对象是自包含的数据集合，包含在对象里的数据可以通过两种形式访问——属性（property）和方法（method）：❑ 属性是隶属于某个特定对象的变量；❑ 方法是只有某个特定对象才能调用的函数。

>> 由浏览器提供的预定义对象被称为宿主对象（host object）。


## 第3章 DOM

>> ❑ 5个常用DOM方法：getElementById、getElementsByTagName、getElementsByClassName、get-Attribute和setAttribute


◆ 3.2 对象：DOM中的“O”

>> JavaScript语言里的对象可以分为三种类型。❑ 用户定义对象（user-defined object）：由程序员自行创建的对象。本书不讨论这种对象。❑ 内建对象（native object）：内建在JavaScript语言里的对象，如Array、Math和Date等。❑ 宿主对象（host object）：由浏览器提供的对象。

>> 最基础的对象是window对象。window对象对应着浏览器窗口本身，这个对象的属性和方法通常统称为BOM（浏览器对象模型），但我觉得称为Window Object Model（窗口对象模型）更为贴切。BOM提供了window.open和window.blur等方法，这些方法某种程度上要为到处被滥用的各种弹出窗口和下拉菜单负责。难怪JavaScript会有一个不好的名声！


◆ 3.3 模型：DOM中的“M”

>> 3.3 模型：DOM中的“M”

>> DOM中的“M”代表着“Model”（模型），但说它代表着“Map”（地图）也未尝不可。

>> DOM代表着加载到浏览器窗口的当前网页。浏览器提供了网页的地图（或者说模型），而我们可以通过JavaScript去读取这张地图。

>> DOM把一份文档表示为一棵树（这里所说的“树”是数学意义上的概念），这是我们理解和运用这一模型的关键。更具体地说，DOM把文档表示为一棵家谱树。家谱树本身又是一种模型。家谱树的典型用法是表示一个人类家族的谱系，并使用parent（父）、child（子）、sibling（兄弟）等记号来表明家族成员之间的关系。家谱树可以把一些相当复杂的关系简明地表示出来：一位特定的家族成员既是某些成员的父辈，又是另一位成员的子辈，同时还是另一位成员的兄弟。家谱树模型非常适合用来表示一份用(X)HTML语言编写出来的文档。

>> 型来表示。[插图]图3-2

>> 根元素是html。不管从哪个角度看，html都代表整个文档。

>> 如果你能把一个文档的各种元素想象成一棵家谱树，我们就可以用同样的术语描述DOM。不过，与使用“家谱树”这个术语相比，把文档称为“节点树”更准确。


◆ 3.4 节点

>> 3.4 节点节点（node）这个词是个网络术语，它表示网络中的一个连接点。一个网络就是由一些节点构成的集合。

>> 在DOM里有许多不同类型的节点。就像原子包含着亚原子微粒那样，也有很多类型的DOM节点包含着其他类型的节点。接下来我们先看看其中的三种：元素节点、文本节点和属性节点。

>> 3.4.1 元素节点DOM的原子是元素节点（element node）。

>> 标签的名字就是元素的名字。文本段落元素的名字是“p”，无序清单元素的名字是“ul”，列表项元素的名字是“li”。

>> 元素可以包含其他的元素。

>> 3.4.2 文本节点

>> 元素节点只是节点类型的一种。

>> 在XHTML文档里，文本节点总是被包含在元素节点的内部。但并非所有的元素节点都包含有文本节点。

>> 3.4.3 属性节点

>> 属性结点用来对元素做出更具体的描述。

>> 在DOM中，title="a gentle reminder"是一个属性节点（attribute node）

>> [插图]

>> 3.4.4 CSS

>> DOM并不是与网页结构打交道的唯一技术。我们还可以通过CSS（层叠样式表）告诉浏览器应该如何显示一份文档的内容。

>> CSS声明元素样式的语法与JavaScript函数的定义语法很相似：[插图]

>> 继承（inheritance）是CSS技术中的一项强大功能。类似于DOM, CSS也把文档的内容视为一棵节点树。节点树上的各个元素将继承其父元素的样式属性。

>> 1. class属性

>> 2. id属性

>> id属性的用途是给网页里的某个元素加上一个独一无二的标识符

>> 3.4.5 获取元素

>> 有3种DOM方法可获取元素节点，分别是通过元素ID、通过标签名字和通过类名字来获取。1. getElementById

>> DOM提供了一个名为getElementById的方法，这个方法将返回一个与那个有着给定id属性值的元素节点对应的对象。

>> 这个调用将返回一个对象，这个对象对应着document对象里的一个独一无二的元素

>> 利用DOM提供的方法能得到任何一个对象。一般来说，用不着为文档里的每一个元素都定义一个独一无二的id值，那也太小题大做了。

返回的是数组
>> 2. getElementsByTagNamegetElementsByTagName方法返回一个对象数组，每个对象分别对应着文档里有着给定标签的一个元素。类似于getElementById，这个方法也是只有一个参数的函数，它的参数是标签的名字：[插图]

>> 2. getElementsByTagNamegetElementsByTagName方法返回一个对象数组，每个对象分别对应着文档里有着给定标签的一个元素。类似于getElementById，这个方法也是只有一个参数的函数，它的参数是标签的名字：[插图]

>> 它与getElementById方法有许多相似之处，但它返回的是一个数组，你在编写脚本时千万注意不要把这两个方法弄混了。

>> 3. getElementsByClassNameHTML5 DOM（http://www.whatwg.org/specs/web-apps/current-work/）中新增了一个令人期待已久的方法：getElementsByClassName。这个方法让我们能够通过class属性中的类名来访问元素。

>> 与getElementsByTagName方法类似，getElementsByClassName也只接受一个参数，就是类名：[插图]这个方法的返回值也与getElementsByTagName类似，都是一个具有相同类名的元素的数组。

>> 下面是对本章此前学习内容的一个简要总结。❑ 一份文档就是一棵节点树。❑ 节点分为不同的类型：元素节点、属性节点和文本节点等。❑ getElementById将返回一个对象，该对象对应着文档里的一个特定的元素节点。❑ getElementsByTagName和getElementsByClassName将返回一个对象数组，它们分别对应着文档里的一组特定的元素节点。❑ 每个节点都是一个对象。


◆ 3.5 获取和设置属性

>> 3.5 获取和设置属性

>> 3.5.1 getAttributegetAttribute是一个函数。它只有一个参数——你打算查询的属性的名字：[插图]

>> 3.5.2 setAttribute此前介绍的所有方法都是用来获取信息。setAttribute()有点不同：它允许我们对属性节点的值做出修改。与getAttribute一样，setAttribute也只能用于元素节点：[插图]

## 第 4 章

◆ 4.5 小结

>> DOM提供的几个新属性

>> ❑ childNodes❑ nodeType❑ nodeValue❑ firstChild❑ lastChild


## 第7章 动态创建标记

>> 第7章 动态创建标记


◆ 7.1 一些传统方法

>> 7.1.1 document.writedocument对象的write()方法可以方便快捷地把字符串插入到文档里。

>> document.write的最大缺点是它违背了“行为应该与表现分离”的原则。

>> 7.1.2 innerHTML属性

>> innerHTML属性要比document.write()方法更值得推荐。使用innerHTML属性，你就可以把JavaScript代码从标记中分离出来。用不着再在标记的<body>部分插入<script>标签。

>> 类似于document.write方法，innerHTML属性也是HTML专有属性，不能用于任何其他标记语言文档。浏览器在呈现正宗的XHTML文档（即MIME类型是application/xhtml+xml的XHTML文档）时会直接忽略掉innerHTML属性。


◆ 7.2 DOM方法

>> 7.2 DOM方法

>> 这项任务需要分两个步骤完成：(1) 创建一个新的元素；(2) 把这个新元素插入节点树。

>> 第一个步骤要用DOM方法createElement来完成。下面是使用这个方法的语法：[插图]下面这条语句将创建一个p元素：[插图]

>> 7.2.2 appendChild方法

>> 7.2.3 createTextNode方法


◆ 7.4 Ajax

>> 7.4 Ajax2005年，Adaptive Path公司的Jesse James Garrett发明了Ajax这个词，用于概括异步加载页面内容的技术。

>> 7.4.1 XMLHttpRequest对象

>> Ajax技术的核心就是XMLHttpRequest对象。这个对象充当着浏览器中的脚本（客户端）与服务器之间的中间人的角色。以往的请求都由浏览器发出，而JavaScript通过这个对象可以自己发送请求，同时也自己处理响应。

>> XMLHttpRequest对象有许多的方法。其中最有用的是open方法，它用来指定服务器上将要访问的文件，指定请求类型：GET、POST或SEND。

>> [插图]

>> 代码中的onreadystatechange是一个事件处理函数，它会在服务器给XMLHttpRequest对象送回响应的时候被触发执行。在这个处理函数中，可以根据服务器的具体响应做相应的处理。

>> 服务器在向XMLHttpRequest对象发回响应时，该对象有许多属性可用，浏览器会在不同阶段更新readyState属性的值，它有5个可能的值：❑ 0表示未初始化❑ 1表示正在加载❑ 2表示加载完毕❑ 3表示正在交互❑ 4表示完成只要readyState属性的值变成了4，就可以访问服务器发送回来的数据了。

>> 访问服务器发送回来的数据要通过两个属性完成。一个是responseText属性，这个属性用于保存文本字符串形式的数据。另一个属性是responseXML属性，用于保存Content-Type头部中指定为"text/xml"的数据，其实是一个DocumentFragment对象。你可使用各种DOM方法来处理这个对象。而这也正是XMLHttpRequest这个名称里有XML的原因。

>> 注意 在使用Ajax时，千万要注意同源策略。使用XMLHttpRequest对象发送的请求只能访问与其所在的HTML处于同一个域中的数据，不能向其他域发送请求。此外，有些浏览器还会限制Ajax请求使用的协议。比如在Chrome中，如果你使用file://协议从自己的硬盘里加载example.txt文件，就会看到“Cross origin requests are only supported forHTTP”（跨域请求只支持HTTP协议）的错误消息。


## 第 9 章 CSS-DOM

### 9.1 三位一体的网页

>> 9.1 三位一体的网页我们在浏览器里看到的网页其实是由以下三层信息构成的一个共同体：❑ 结构层❑ 表示层❑ 行为层

>> 9.1.1 结构层网页的结构层（structural layer）由HTML或XHTML之类的标记语言负责创建。标签（tag），也就是那些出现在尖括号里的单词，对网页内容的语义含义做出了描述，例如，<p>标签表达了这样一种语义：“这是一个文本段。”（如图9-1所示。）但这些标签并不包含任何关于内容如何显示的信息。

>> 9.1.2 表示层表示层（presentation layer）由CSS负责完成。CSS描述页面内容应该如何呈现。你可以定义这样一个CSS来声明：“文本段应该使用灰色的Arial字体和另外几种scan-serif字体来显示。”如图9-2所示。

>> 9.1.3 行为层行为层（behavior layer）负责内容应该如何响应事件这一问题。这是JavaScript语言和DOM主宰的领域。例如，我们可以利用DOM实现这样一种行为：“当用户点击一个文本段时，显示一个alert对话框。”

>> 9.1.4 分离

>> 具体到网页设计工作，这意味着：❑ 使用(X)HTML去搭建文档的结构；❑ 使用CSS去设置文档的呈现效果；❑ 使用DOM脚本去实现文档的行为。


◆ 9.2 style属性

>> 9.2.1 获取样式你能够得到para变量所代表的<p>标签的样式。为了查出某个元素在浏览器里的显示颜色，我们需要使用style对象的color属性：[插图]

>> DOM style属性不能用来检索在外部CSS文件里声明的样式


## 第 10 章 用 JavaScript 实现动画效果

◆ 10.1 动画基础知识

>> 10.1.2 时间

>> JavaScript函数setTimeout能够让某个函数在经过一段预定的时间之后才开始执行。这个函数带有两个参数：第一个参数通常是一个字符串，其内容是将要执行的那个函数的名字；第二个参数是一个数值，它以毫秒为单位设定了需要经过多长时间后才开始执行第一个参数所给出的函数：[插图]在绝大多数时候，把这个函数调用赋值给一个变量将是个好主意：[插图]

>> 如果想取消某个正在排队等待执行的函数，就必须事先像上面这样把setTimeout函数的返回值赋值给一个变量。你可以用一个名为clearTimeout的函数来取消“等待执行”队列里的某个函数。这个函数需要一个参数——保存着某个setTimeout函数调用返回值的变量：[插图]

>> 2．使用moveElement函数


◆ 10.2 实用的动画

>> overflow属性的可取值有4种：visible、hidden、scroll和auto。❑ visible：不裁剪溢出的内容。浏览器将把溢出的内容呈现在其容器元素的显示区域以外，全部内容都可见。❑ hidden：隐藏溢出的内容。内容只显示在其容器元素的显示区域里，这意味着只有一部分内容可见。❑ scroll：类似于hidden，浏览器将对溢出的内容进行隐藏，但显示一个滚动条以便让用户能够滚动看到内容的其他部分。❑ auto：类似于scroll，但浏览器只在确实发生溢出时才显示滚动条。如果内容没有溢出，就不显示滚动条。

## 第 11 章 HTML5

### 11.1 HTML5简介
HTML5是HTML语言当前及未来的新标准。HTML规范从HTML 4到XHTML，再到Web Apps1.0，最后又回到HTML5，整个成长历程充满了艰辛和争议。

谈到Web设计，最准确的理解是把网页看成三个层：
(1) 结构层
(2) 样式层
(3) 行为层

这三个层分别对应不同的技术，分别是：
(1) 超文本标记语言（HTML）
(2) 层叠样式表（CSS）
(3) JavaScript和文档对象模型（DOM）

### 新的输入控件类型包括：
❑ email，用于输入电子邮件地址；❑ url，用于输入URL；❑ date，用于输入日期和时间；❑ number，用于输入数值；❑ range，用于生成滑动条；❑ search，用于搜索框；❑ tel，用于输入电话号码；❑ color，用于选择颜色。


### 其他特性

❑ 使用localStorage和sessionStorage在客户端存储大型和复杂数据集的更有效方案（http://dev.w3.org/html5/webstorage）；
❑ 使用WebSocket与服务器端脚本进行开放的双向通信（http://dev.w3.org/html5/websockets/）；
❑ 使用Web Worker在后台执行JavaScript（http://www.whatwg.org/specs/web-workers/current-work/）；
❑ 标准化的拖放实现（http://www.whatwg.org/specs/web-apps/current-work/multipage/dnd.html#dnd）；
❑ 在浏览器中实现地理位置服务（http://www.w3.org/TR/geolocation-API/）。


◆ 11.2 来自朋友的忠告

>> Modernizr（http://www.modernizr.com/）是一个开源的JavaScript库，利用它的富特性检测功能，可以对HTML5文档进行更好的控制。


◆ 11.3 几个示例

>> 新的输入控件类型包括：❑ email，用于输入电子邮件地址；❑ url，用于输入URL；❑ date，用于输入日期和时间；❑ number，用于输入数值；❑ range，用于生成滑动条；❑ search，用于搜索框；❑ tel，用于输入电话号码；❑ color，用于选择颜色。


◆ 11.4 HTML5还有其他特性吗

>> ❑ 使用localStorage和sessionStorage在客户端存储大型和复杂数据集的更有效方案（http://dev.w3.org/html5/webstorage）；❑ 使用WebSocket与服务器端脚本进行开放的双向通信（http://dev.w3.org/html5/websockets/）；❑ 使用Web Worker在后台执行JavaScript（http://www.whatwg.org/specs/web-workers/current-work/）；❑ 标准化的拖放实现（http://www.whatwg.org/specs/web-apps/current-work/multipage/dnd.html#dnd）；❑ 在浏览器中实现地理位置服务（http://www.w3.org/TR/geolocation-API/）。


◆ A.1 选择合适的库

>> ❑ jQuery（http://jquery.com）官方网站说它是“一个快速简洁的JavaScript库，致力于简化HTML文档搜索、事件处理、动画以及Ajax交互，从而实现快速Web开发。jQuery的设计目的是为了改变你编写JavaScript程序的方式。”jQuery极为强大的选择方法、连缀语法以及简化的Ajax和事件方法，都会让你的代码变得简洁且容易理解。这个库的背后还有一个非常大的社区，包括大量插件开发人员，他们开发的插件极大增加了库的功能。❑ Prototype（http://prototypejs.org）把自己描绘成“一个旨在简化动态Web应用开发的JavaScript框架。”Prototype提供了很多非常棒的DOM操作功能，还有一个广受好评的Ajax对象。它是出名最早的一个JavaScript库，也是通过$()实现选择功能的鼻祖。


◆ A.3 选择元素

 ❑ $('*')选择所有元素；❑ $('tag')选择所有HTML标签中的tag元素；❑ $('tagA tagB')选择作为tagA后代的所有tagB元素；❑ $('tagA, tagB, tagC')选择所有tagA元素、tagB元素和tagC元素；❑ $('#id')和$('tag#id')选择所有ID为id的元素或ID为id且标签为tag的元素；❑ $('.className')和$('tag.className')选择所有类名为className的元素或类名为className标签为tag的元素。



◆ 点评

点评:★★★★☆
在vdom盛行的时代 选择性的看了下