# HTML



HTML（HyperText Markup Language），超文本标记语言，是一种用于创建网页的标准标记语言。（有点类似 XML）它定义了网页内容的*含义*和*结构*。



HTML 使用标记来注明文本、图片和其他内容，以便在 Web 浏览器中显示。



HTML 不是一门编程语言，而是一种用于定义你内容结构的标记语言。

HTML 由一些列的元素组成，元素通过“标签”（tag）将文本从文档中引出，标签由“<”和“>”中包裹的元素名组成，元素名*不区分大小写*，不过一般小写。



### Hello HTML

一个简单的 Hello HTML 的 demo：

新建一个 helloworld.html 文件，填入：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello HTML</title>
</head>
<body>

<p>Hello HTML!</p>

</body>
</html>
```

HTML 保存的文件后缀可以是下面两种，无差都可以用：

- `.html`
- `.htm`

注意：中文的网页需要用`<meta charset="utf-8"> ` 声明编码，否则会出现乱码。



在浏览器中打开 helloworld.html，就会展示 Hello HTML。



### HTML 元素详解



举个例子：`<p>这个是段落</p>`



元素的组成：

1. `开始标签`（Opening tag），包含元素的名称（例为 p），被大于号、小于号所包围，表示*元素的开始*；
2. `结束标签`（Closing tag），跟开始标签类似但多了个斜杠，表示*元素的结尾*；
3. `内容`（Content），开始标签跟结束标签之间的内容，例子中的“这个是段落”；
4. `元素`（Element），开始标签+内容+结束标签 的结合就是一个完整的元素。



#### 元素的属性



元素可以拥有自己的属性：`<p class="test">这个是段落</p>`，添加了`class`属性。



属性的组成：

1. `空格`，在属性与元素名称（或上一个属性，如果有超过一个属性的话）之间的空格符；
2. `属性的名称`，并且接上一个等号；
3. `属性的值`，由引号包围的属性值，不包含 ASCII 空格（以及 `"` `'` ``` `=` `<` `>` ）的简单属性值可以不使用引号，但是建议将所有属性值用引号括起来，这样的代码一致性更佳，更易于阅读。



#### 嵌套元素

元素允许嵌套——可以将一个元素置于其他元素中。

比如把`<strong>`标签嵌入：

`<p class="test">这个是<strong>段落</strong></p>`



嵌套要注意开闭标签的对称，要正确开闭。



#### 空元素

不包含任何内容的元素称为空元素。比如`<img>`元素。

`<img src="xxx/xxx" alt="图片">`

这没有结束标签也没有元素内容。



### HTML 文档解析



html 文档由各种元素组成，最基本的有：



- `<!DOCTYPE html> `，文档类型，历史原因，现在不用关心了；
- `<html></html>`，html 元素，这个元素包含整个页面的内容，有时也被称为根元素；
- `<head></head> `，head 元素，这里的内容不是展示给用户的，包含如搜索关键字、字符编码声明等信息；
- `<meta charset="utf-8">`，指定当前文档使用 UTF-8 字符编码，它基本可以处理任何文本内容，包括中文，没理由用其他编码了；
- `<title></title>`，title 标签，设置页面的标题，显示在浏览器标签页上，同时作为搜藏网页的描述文字；
- `<body></body> `，body 元素，这里包含了所有希望让用户看到的内容；



### DOM 

DOM，`D`ocument `O`bject `M`odel，文档对象模型。

DOM 事件模型（DOM Event Model）



```javascript
button.addEventListener('click',greet,false);
function greet(event){
  alert('hello world!');
}
```

