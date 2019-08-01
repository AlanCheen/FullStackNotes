# HTML 元素

[TOC]

HTML 由一系列元素组成。



### 块级元素和内联元素

HTML 中有两种重要的元素类别，块级元素和内联元素。



1. `块级元素`（block element），在页面中以块的形式展现，*相对于它前面的内容它会出现在新的一行，其后的内容也会被挤到下一行展现*（**会导致换行**）。块级元素通常用于展示页面上结构化的内容，例如段落、列表等。一个以 block 形式展现的块级元素不会被嵌套进内联元素中，但可以嵌套在其他块级元素中；

2. `内联元素`（inline element），它通常出现在块级元素中并包裹文档内容的一小部分，而不是一整个段落或者一组内容。内联元素不会导致文本换行：它通常出现在一堆文字之间例如超链接元素`<a>`或者强调元素`<em>`和`<strong>`。



### 空元素

不是所有元素都拥有开始标签，内容和结束标签。一些元素只有一个标签，通常用来在此元素所在位置插入/嵌入一些东西。例如，`<img>`用来插入图片。







### 图片

`<img src="路径" alt="描述" width="56" height="56">`

还有 map、area 标签定义地图。

属性：

- `src`，“source”，图片路径，可以是：
  - 绝对路径
  - 相对路径
- `alt`，描述，当图片无法展示的时候会展示 alt 里的文本内容；
- `width`，宽度
- `height`，高度
- `align`，设置图像对齐方式
  - bottom，底部对齐，默认方式；
  - middle，居中对齐，
  - top，顶部对齐
- `border`，例子 20



```html
给网页添加背景图，如果图片小于背景则重复图片
<body background="/i/eg_background.jpg">

```





### 标记文本



#### 标题 （Heading）

`<h1>`-`<h6>`，一般也就用到 3-4 级标题。

```html
<h1>这个是一级标题</h1>
<h2>这个是二级标题</h2>
<h3>这个是三级标题</h3>
<h4>这个是四级标题</h4>
<h5>这个是五级标题</h5>
<h6>这个是六级标题</h6>
```



#### 段落（Paragraph）

段落标签用来指定段落，通常指定文本内容，且独占一行。

`<p>段落标签</p>`



#### 列表

列表分两种：

1. `有序列表`（Ordered List），`ol`，有顺序，会展示 1. 2. 3.
2. `无序列表`（Unordered List），`ul`，不讲顺序



```html
<p> ==== 无序列表标签 ul： ==== </p>

 <ul>
 	<li>Android</li>
 	<li>iOS</li>
 	<li>HTML</li>
 	<li>CSS</li>

 </ul>

<p> ====  有序列表标签 ol： ==== </p>
 <ol>
 	<li>第一节</li>
 	<li> 第二节</li>
 </ol>
```



展示：

![image-20190730200814740](http://ww4.sinaimg.cn/large/006tNc79ly1g5i4g1pkq1j30ek0da3zo.jpg)

##### 嵌套列表

列表之前可以相互嵌套，可以在某个` <li>`的内容里添加列表，比如我们在 JavaScript 里添加个列表：

```html
<ul>
	<li>Android</li>
	<li>iOS</li>
	<li>HTML</li>
	<li>CSS</li>
	<li>JavaScript
		<ul>
			<li>ES5</li>
			<li>ES6</li>
		</ul>
	</li>
</ul>
```

就展示成这样：

![image-20190730202826536](http://ww3.sinaimg.cn/large/006tNc79ly1g5i5122dc9j30cg09ywf3.jpg)



### 链接

a 标签，定义锚，“anchor”的缩写，可以链接一切。



属性：

1. `href`（**h**ypertext **ref**erence），指定超文本引用，可以是网页链接；
2. `title`，鼠标移动到链接会展示的标题；
3. `target`，指定被`链接的文档在何处显示`，默认是在当前页面展示；
   1. `target="_blank"`，在新窗口打开；
   2. `target="_top"`，
4. `name`，规定锚 anchor 的名称，可以用来创建 HTML 书签，可以用来做类似回到顶部的效果，也可以用来做导航，具体用法是`<a name="foo">`和`<a href="#foo">`；



简单的例子如下：

```html
 <a href="http://yifeiyuan.me">这是个指向外部页面的链接</a>
```



```html
<a href="./xx.html">一个指向内部页面的链接</a>
```



用图片来做链接

```html
<p>
  <a href="http://yifeiyuan.me">这是个用图片做的链接<img src="https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1557131045935-3b37e4fb-658b-4d27-956b-eff115f51352.jpeg" width="100" height="100"></a>
</p>
```



锚效果：

#### 回到顶部

```html
<a name="top">

<p><a href="#top">点击我回到顶部</a></p>
```

或者跳转到其他页面的某个章节：

```html
<a href="xxxxx.html#top"></a>
```

