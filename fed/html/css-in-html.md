# 在 HTML 中使用 CSS





### 内部样式

在 HTML 文件中的 `<head>`标签中编写 `<style>`。



```html
<head>
  <style type="text/css">
	h1 {color: red}
	p {color: blue}
	</style>
</head>
```



### 外部样式



通过在 head 标签内添加 link 标签来引入外部 CSS 。



```html
<head>
<link rel="stylesheet" type="text/css" href="/html/csstest1.css" >
</head>
```



### 内联样式

当特殊的样式需要应用到个别元素时，就可以使用内联样式。

```html
<p style="color: red">
This is a paragraph
</p>
```

