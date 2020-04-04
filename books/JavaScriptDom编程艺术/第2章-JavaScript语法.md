# 第2章-JavaScript语法

本章介绍 JS 的基础知识，语句、变量、数组和函数等。

JavaScript 编写的代码必须通过 HTML/XHTML 文档才能执行。

把`<script>`标签放在HTML 文档最后，`</body>`标签之前是最好的做法，这样能使浏览器更快地加载页面。

`<script>`标签的`type="text/java script"`属性是默认的，不需要指定。


JavaScript 是解释型语言，需要解释器，Web 浏览器负责解释和执行；解释型代码中的错误只能等到解释器执行相关代码时才能被发现。

## 语法：语句 注释 变量


- JS 不强制句末加分号，但是推荐加分号；
- JS 允许直接对变量赋值而无需事先声明，但是推荐提前声明；
- 推荐用`//`注释单行 `/*---*/`注释多行；
- JS 不允许变量中包含空格或标点符号（除了美元符号$）；
- JS 允许变量名包含字母、数字、美元符号和下划线（但第一个字符不允许是数字）；
- 通常驼峰格式是函数名、方法名和对象属性名命名的首选格式；


## 数据类型

JS 是弱类型（weakly typed），不需要进行类型声明。

- 字符串 `\`转义符合；
- 浮点数 `var age = 20; var tep=-23; var price = 10.23443`；
- 布尔值（boolean） true or false；
- 数组 array,可以混合类型跟 Python 一样；`var beatles = Array(4); var bealtes = ["A","B","C"]`；
- 关联数组 `var lennon  = Array();lennon["name"]="John"` ,不推荐使用；
- 对象，创建对象可以用 `Object`关键字:`var lennon = Object();lennon.name="John";`或者花括号语法:`{propertyName:value,propertyName:value}`;对象的每个值都是对象的属性;

## 操作

- 算术操作 + - * /  +=
- 字符串拼接 string + string;

## 循环语句

```
//if else
if(condition){
    statements;
}else{
    statements;
}
//while
while(condition){
    statements;
}
// do while
do{
    statemenets
}while(condition)

//for 循环
for(initial condition;test condition;alter condition){
    statements;
}
```


## 函数

函数是一组允许在代码里随时调用的语句。每个函数实际上是一个短小的脚本。

```
function name(arguments){
    statements;
}
```

变量命名用下划线，函数命名用驼峰。

## 变量的作用域

- 全局变量 可以在脚本任何位置被引用
- 局部变量 仅限于某个特定的函数

当函数内部使用了全局变量的名字，JS 会认为是在引用全局变量。

为了避免二义性，建议函数内使用`var`来明确声明局部变量。


## 对象

对象是自包含的数据集合，包含在对象里的数据可以通过两种形式访问————属性和方法。

- 属性是隶属于某个特定对象的变量； object.property
- 方法是只有某个特定对象才能调用的函数。object.method()

对象就是由一些属性和方法组合在一起而构成的一个数据实体。

这个跟 Java 还是一样的~

在 JS 中，把用户自己创建的对象叫做 用户定义对象（user-defined object）。

#### 內建对象

并且，JS 提供了一系列预先定义好的对象，这些拿来就可以用的对象称为內建对象(native objcet)。

比如 Array 、Date 、Math。

#### 宿主对象

由运行环境提供的，预先定义的对象。

Web 应用，环境是浏览器，由浏览器提供的预定义的对象被称为宿主对象（host object）。

Form Image Element 可以获得网页上表单，图像和各种表单元素等信息。

另外还有一种宿主对象也能获得网页上的任何一个元素的信息，就是 document 对象。



















