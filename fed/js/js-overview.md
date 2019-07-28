# JavaScript 

[TOC]



### 历史背景



1995 年 Netscape 的一名叫做 Brendan  Eich 的工程师创造。原名 LiveScript ，后改名 JavaScript，跟 Java 没有关系。

Netscape 将 JS 提交至 Ecma International(一个欧洲标准化组织)，ECMAScript 标准第一版便在 1997 年诞生。

- ES1，1997 年；
- ES4，因分歧而被废除；
- ES5，2009 年 12 月发布；
- ES6，2015 年 6 月 17日 发布，也称 ES 2015；

### 概览

JavaScript 是一个在宿主环境（host environment）下运行的脚本语言。



JavaScript 是一种多范式的动态语言，它包含类型、运算符、标准内置对象(build-it)和方法。

它语法来源于 Java 和 C。



JavaScript 通过原型链而不是类来支持面向对象编程。它还支持函数编程，因为它们也是对象；还支持命令式和声明式风格（如函数式编程）。



JS 的特性：

- `多范式`
- `轻量`
- `解释性`
- `动态性`，动态语言，不需要编译，
  - 运行时构造对象
  - 可变参数列表
  - 函数变量
  - 动态脚本执行（通过 eval）
  - 对象内枚举（通过 for…in）
  - 源码恢复（JavaScript 程序可以将函数反编译回源代码）
- `脚本语言`，宿主环境 host environment 下运行
- `支持面向对象`，用原型链实现，跟“类”实现不太一样，另外 ES6 有 class 的概念；
- `支持命令式`，
- `支持函数式编程`，
- 语法来源 Java、C，比较灵活（太灵活）



### JS 的类型



JS 类型包括：



- `Number`，数字，64 位双精度浮点数，类似 Java 的 double；
- `String`，字符串，跟 Java 的差不多；
- `Boolean`，布尔，`true`or`false`，JS 布尔转换规则如下：
  - `false`、`0`、空字符串`""`、`NaN`、`null` 和 `undefined` 被转换为 `false`;
  - 所有其他的值都被转换为 `true`；
- `Symbol`，符号 ES2015 新增
- `Object`，对象
  - `Function`，函数
  - `Array`，数组
  - `Date`，日期
  - `RegExp`，正则表达式
- `null`，空，表示一个空值（non-value）,必须使用 null 关键字才能访问；
- `undefined`，未定义，是一个“未定义”类型的对象，表示一个未初始化的值，也就是还没有被分配的值，因为 JS 允许声明变量但不赋值，一个没有被赋值的变量就是`undefined`类型；另外`undefined`是一个不允许修改的常量；



### Number

JS 的数字遵循 双精度 64 位格式，并且不区分整数值和浮点数值，所有的数字都是浮点值，所以需要特别注意。

比如下面这个例子，0.1+0.2 结果不是 0.3：

```js
//0.1+0.2 结果不是 0.3
console.log(0.1+0.2)
//0.30000000000000004
```



Number 还有三个特殊的值`NaN`(Not a Number)、`Infinity`（正无穷）、`-Infinity`（负无穷）



解析一些不是数值形式的字符串就会返回 NaN：

```js
parseInt('hi',1o);//NaN
```

NaN 可以用`isNaN(num)`来判断：

```js
var result = isNaN(1);//false
var result2 = isNaN(NaN);//true
console.log(result,result2)// false true
```

NaN 跟其他数字计算，结果都是 NaN：

```js
var result3 = 3+NaN;
console.log(result3)//NaN
```



正负无穷，被除数为 0 的情况：

```js
var result4 = 1/0;
console.log(result4)//Infinity
var result5 = -1/0
console.log(result5)//-Infinity
```

有个内置方法`isFinite(num)`来判断是否是`有穷数`，注意是有穷数，所以如果是 Infinity 的话会返回 false。

#### 小结

- `parseInt`、`parseFloat`来解析数字；
- NaN，特殊值，表示非数字，有内置的函数`isNaN(nubmer)`可以判断；
- 无穷数，`Infinity`（正无穷）、`-Infinity`（负无穷），可以用`isFinite(number)`来判断，不过要注意的是它判断的是有穷数，另外 NaN 不是无穷数；

### String

JS 里的 String 是一串 Unicode 字符序列，是一串 UTF-16 编码单元的序列，每个编码单元由一个 16 位二进制数表示。每一个 Unicode 字符由一个或两个编码单元来表示。

通过访问字符串的`length`（编码单元的个数）属性，可以得到它的长度。

```js
"hello world".length
```



字符串的方法：

```javascript
"hello".charAt(0); // "h"
"hello, world".replace("world", "mars"); // "hello, mars"
"hello".toUpperCase(); // "HELLO"
...
```



### 变量

在 JS 声明一个新的变量可以使用关键字 let const 和 var。



let 语句声明一个块级域的本地变量



```js
// myLetVariable is *not* visible out here

for (let myLetVariable = 0; myLetVariable < 5; myLetVariable++) {
  // myLetVariable is only visible in here
}

// myLetVariable is *not* visible out here
```



const 允许声明一个不可变的常量，这个常量在定义域内总是可见的。

```javascript
const Pi = 3.14; // 设置 Pi 的值  
Pi = 1; // 将会抛出一个错误因为你改变了一个常量的值。
```



var 没有其他两个关键字的限制，var 声明的变量在它所声明的整个函数都是可见的。

```javascript
// myVarVariable *is* visible out here 

for (var myVarVariable = 0; myVarVariable < 5; myVarVariable++) { 
  // myVarVariable is visible to the whole function 
} 

// myVarVariable *is* visible out here
```



如果声明了一个变量却没有赋值，那么这个变量的类型就是 undefined。



JavaScript 与其他语言的（如 Java）的重要区别是在 `JavaScript 中语句块（blocks）是没有作用域的，只有函数有作用域`。因此如果在一个复合语句中（如 if 控制结构中）使用 var 声明一个变量，那么它的作用域是整个函数（复合语句在函数中）。但是从 ES6 开始将有所不同的， let 和 const 关键字允许你创建块作用域的变量。

比如：

```javascript
var a =5
console.log(d)//underfined,变量提升
if (a>3) {
	var d = 4;//var 定义
}
console.log(d)//输出 4
```

上面 if 语句定义的 d，可以在外面访问，而类似 Java 是不可以的。



### 运算符

JS 算术操作符包括包含 `+`、`-`、 `*`、 `/`和 `%` ，赋值`=`，复合运算符，`+=`、`-=`，`++`、`--`;

比较操作：<、 > 、<=、 >=。

JS 中的相等比较稍微复杂些，由两个等号 ` ==` 组成，并具有`类型自适应`的功能。

```javascript
123 == "123"//true
1 == "true" //true
```

如果在比较前不需要自动类型转换，应该使用由三个等号`===`组成的相等运算符：

```javascript
123 === "123"//false
1 === true//fasle
```

![image-20190727152118181](http://ww2.sinaimg.cn/large/006tNc79ly1g5efal3je8j308w052q3o.jpg)

另外还支持 `!=`和 `!==`，两种不等运算符，差别跟上述类似。



运算符小结：

1. 算术操作符，加、减、乘、除、取余；
2. 赋值操作符，`=`;
3. 复合运算符，+=，-=，++，--
4. 比较操作符，< > <= >=
5. 相等运算符
   1. `==`，值比较，自动转换类型
   2. `===` ，严格相等运算符，会比较类型以及值，不会自动转换类型
6. 不等运算符
   1. `!=`
   2. `!==`
7. 位操作符，
   1. `a&b`, and
   2. `a|b`,or
   3. `a^b`，xor
   4. `~a`，not
   5. `a<<b`，
   6. `a>>b`,
   7. `a>>>b`，



### 控制结构

JS 的控制结构跟 C、Java 类似，有 `if`、`if-else`、 `while`、 `do-while`、`for`

另外还添加了两种 `for`循环：`for…of`,`for..in`

for…of，循环遍历数组里的 value:

```javascript
for (let value of array){
  //do sth with value
}
```



for…in，循环遍历对象的属性:

```javascript
for(let property in object){
  //do sth with object property
}
```



`&&`和`||`运算符使用`短路逻辑`（short-circuit logic），`是否会执行第二个语句（操作数）取决于第一个操作数的结果`。如果第一个为 true 才会执行后面的。



#### 控制结构小结

1. if if-else
2. while do-while
3. for ,for…of , for…in
4. && ||
5. switch case，switch case 都支持表达式，并且是用`====`进行比较
6. 三元表达式 `?:`

### 对象

JS 中的对象，Object，可以简单理解为`“名称-值”对`，而*不是“键值对”*。所以 JS 中的对象跟 Java 中的 HashMap 概念类似。JS 中的一切（除了核心类，core object）都是对象。



“名称”部分是一个 JS 的字符串，“值”部分是任何 JS 的数据类型——包括对象。

两种简单的方法可以创建一个空对象：

一：

```javascript
var obj = new Object();
```

二：

```javascript
var obj = {}
```



第二种更方便的方法叫做`“对象字面量（object literal）”法`。这种也是 JSON 格式的核心预发，一般优先选择。



用对象字面量可以用来定义对象，举个例子：

```javascript
var obj = {
	name: "Fitz",
	age: 18,
	skill: [
	"Android",
	"JS"
	]
}
```

**注意：**字面量一般指 `[1, 2, 3]` 或者 `{name: "mdn"}` 这种简洁的构造方式。

对象的属性可以通过`链式（chain）表示方法`来进行访问：

1. object+`.`+`名称`，比如： obj.name
2. object+`["`+`名称`+`"]`，注意中括号内的名称由引号`""`引起来(单引号`''`也行)，比如：obj["age"]；这种方式的优点是属性的名称被看做一个字符串，这就意味着它可以在运行时被计算，缺点在于这样的代码有可能无法在后期被解释器优化；它也可以被用来访问某些预留关键字作为名称的属性的值，不过 ES5 开始预留关键字可以作为对象的属性名了；

```javascript
console.log(obj.name)//Fitz
console.log(obj["age"])//18
console.log(obj[skill])//Uncaught ReferenceError: skill is not defined
```



可以用 function 定义对象原型，new 创建原型的实例：

```javascript
//定义一个对象原型，Person，和原型的实例，You
function Person(name,age){
	this.name = name;
	this.age = age;
}
var You = new Person("Fitz",18);
```

取值、赋值：

```javascript
console.log(You.name)//取值 Fitz
console.log(You['age'])//18
You.age = 19 //赋值
console.log(You['age'])//19
```

对象和原型：https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/prototype

### 数组

JS 的数组是一种特殊的对象，原理与普通对象类似，`以数字为属性名，但只能通过[]来访问`，另外还有一个特殊的属性——`length`。需要注意 `length` 这个属性的值比数组最大索引值大 1，并且`并不总是等于数组中元素的个数`。

#### 创建数组

创建数组的两种方法：

一，传统方式：

```javascript
var a = new Array();
a[0] = "Android"
a[1] = "JavaScript"
```

二，数组字面量（array literal）：

```javascript
var array = ["Andorid","JavaScript"]		
```



length 的坑，Array.length 并不总是等于数组中元素的个数：

```javascript
var array2 = [1,2,3];
array2[10] = 10;
array2.length;//11
array2[8]//undefined
array2.push(4)//加一个元素
```

`记住：数组的长度是比数组最大索引值多一的数。`

并且访问一个不存在的数组索引，会得到`underfined`。

#### 数组遍历

假设拥有一个数组`var array3 = [0,1,2]`:

传统的 `for`:

```javascript
for(var i=0;i<array3.length;i++){
	console.log(array3[i])//0 1 2
}
for (var i = array3.length - 1; i >= 0; i--) {
	console.log(array3[i])//2 1 0
}
```

ES6 加入的 `for…of` 可以用来遍历对象，当然数组也行：

```javascript
for (const value of array3) {
	console.log(value)//0 1 2
}
```

`for…in` ，它并不是遍历数组元素而是数组的索引。

注意，如果直接用 `Array.prototype`添加了新的属性，用forin 也会遍历这些属性，所以`不推荐 forin 遍历数组`。

```javascript
for (const index in array3) {
	console.log(array3[index]);
}
```

ES5 新增了另外一个遍历数组的方法，`forEach()`：

```javascript
array3.forEach( function(value, index) {
	console.log("value:"+value+" index:"+index)
});
```



数组的方法，[完整的点这里](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array)：

1. push(item1,…,itemN)，在数组中添加元素，将 item 添加到数组；
2. contact(item1[],…itemN[])，整合数组，返回一个数组，这个包含所有元素；
3. join(sep)，
4. slice(start,end)，
5. sort([cmpfn])，
6. toString()，
7. ...



### 函数

学习 JS 最重要的就是理解对象和函数两个部分。最简单的函数定义：

```javascript
function add(x,y){
	var sum = x + y;
	return sum;
}
```

`function functionName(){}`

一个 JS 函数可以包含 0 个或多个以命名的变量。函数体中的表达式数量也没有限制。

如果没有使用`return`语句，或者一个没有值的`return`语句，JS 都会返回`underfined`。

如果调用函数时没有提供足够的参数，缺少的参数会被`underfined`替代。

```javascript
add(); //NaN ，实际上是 add(underfined,underfined)
```

如果传入多余的参数，则会被忽略：

```javascript
add(2,3,4,5);//结果5
```



#### arguments

函数实际上是访问了函数体中一个名为`arguments`的内部对象，这个对象类似数组，`包括了所有被传入的参数`。



可以利用来重写实现一个接收任意个数的参数：

```javascript
function add(){
	var sum =0;
	for(var k = 0, length3 = arguments.length; k < length3; k++){
		sum += arguments[k]
	}
	return sum;
}
add(1,2,3,4,5)//15
```



使用 `arguments` 会让代码看上去有点冗长，可以使用`剩余参数`来替换 `arguemnts`。

#### 剩余参数操作符（rest parameter）

`剩余参数操作符（rest parameter）`在函数中以：`...variable`的形式被使用，它将包含调用函数时使用的`未捕获整个参数列表`到这个变量中。其实就是`把除了自己之前的所有参数都保存起来`，比如`finction foo(a,...args)`会把传进来`a`以外的参数都存入`args`；另外剩余参数`必须必须是函数的最后一个参数`。



用`args`实现一个计算平均数的函数：

```javascript
function avg (...args) {
	var sum = 0;
	for (let value of args) {
		sum +=value;
	}
	return sum/args.length;
}
avg(1,2,3,4,5)//3
```



`剩余参数操作符`是一个很有用的语言特性，但是有个问题，`它只接受逗号分开的参数列表`——如果你想使用数组当参数就不行。

比如之前的`avg`函数我们传入个数组进去，会得到 NaN：

```javascript
avg([1,2,3,4,5])//NaN
```



那怎么办？

重新写一个方法支持数组，肯定可行，但是要是能复用就好了。

#### apply()

JS 运行通过任何函数对象的`apply()`方法来传递它一个数组作为参数列表。

```
avg.apply(null,[1,2,3,4,5]);//3
```

`apply()`方法的第二个参数是一个数组，它将被当做 `avg()`参数列表使用。



#### 匿名函数

JS 允许创建匿名函数，`function(){}`，少了函数名字，类似这样：

```javascript
var avg = function () {
	var sum = 0;
	for (var i = arguments.length - 1; i >= 0; i--) {
		
		sum += arguments[i]
	}
	return sum/arguments.length;
}
avg(1,2,3,4,5)//3
```

可以在代码中的任何地方定义这个函数。

#### IIFE（Immediately Invoked Function Expression）

基于这个特性，有人发明出一些有趣的技巧，类似 C 中的快捷作用域，下面这个例子`隐藏了局部变量`：

```javascript
var a = 1;
var b = 2;
//立即执行的函数，块级作用域
(function() {
	console.log(this)//Window 对象
    var b = 3;//跟外面的 b 不是同一个
    a += b;
})();
a;//4
b;//2
```

其实上面的是 IIFE，语法类似：`（function(args){body}）(args)`。

再举个有参数的例子：

```javascript
(function (a,b) {
	console.log('a+b='+(a+b))//a+b=3
})(1,2)
```

当然也可以给IIFE 的函数取名字：

```javascript
var add =(function add(a,b) {
	console.log('a+b='+(a+b))
})(1,2);
```

注意：一定要有一个变量来接收 IIFE，不然不可以命名。

如下的就是错误的：

```javascript
(function add(a,b) {
  console.log('a+b='+(a+b))
})(1,2);
// Uncaught TypeError: (intermediate value)(...) is not a function
```



作用域仅仅是该函数自身。





### 自定义对象

[JS 面向对象简介](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript)



在经典的面向对象语言中，对象是指数据和在这些数据上进行的操作的集合。跟 C++和 Java 不同，JS 是一种基于原型的编程语言，并没有 class 语句，而是`把函数用作类`。

尝试定义一个对象：

```javascript
function makePerson (first,last) {
	
	return {
		first: first,
		last: last,
		fullName: function () {
			return this.first+' '+this.last;
		},
		fullNameReveresd: function () {
			return this.last+','+this.first;
		}
	}
}
let p = makePerson("Fitz","Chen");
p.fullName();//"Fitz Chen"
p.fullNameReveresd();//"Chen,Fitz"
```

#### this 关键字

关键字`this`，当在函数中使用时，`this`指代当前的对象，也就是`调用了这个函数的对象`。

如果在一个对象上使用点或者方括号来访问属性或方法，这个对象就成了`this`，而如果没有那么`this`将指向`全局对象`（global object ，通常指的是 `Window` 对象）。这是个经常出错的地方。

例如：

```javascript
let p = makePerson("Fitz","Chen");
p.fullName();//"Fitz Chen"
p.fullNameReveresd();//"Chen,Fitz"
var fullName = p.fullName;
fullName()//undefined undefined
```

因为 `fullName()`执行时，`调用它的对象其实是指全局对象`，并没有`first` 或 `last` 属性，所以它们两个的值都是 `undefiened`。

#### new 关键字

使用`this`改进方案：

```javascript
function Person (first,last) {
	this.first = first;
	this.last = last;
	this.fullName = function () {
			return this.first+' '+this.last;
	},
	this.fullNameReveresd = function () {
			return this.last+','+this.first;
	}
}
var p2 = new Person("San","Zhang")
```

这里引入了`new`关键字，它和`this`密切相关。它的作用是**创建一个崭新的空对象**，然后使用指向那个对象的`this`调用特定的函数。

注意，含有 `this` 的特定函数不会返回任何值，只会修改 `this` 对象本身。`new` 关键字将生成的 `this` 对象返回给调用方，而被 `new` 调用的函数称为`构造函数`。习惯的做法是`将这些函数的首字母大写`，这样用 `new` 调用他们的时候就容易识别了。

注意：改进的方案还是会有之前的问题。



另外还有问题在于我们每次创建一个 Person 对象的时候，都在其中创建了两个新的函数对象——如果可以共享就会更好。

```javascript
function personFullName() {
    return this.first + ' ' + this.last;
}
function personFullNameReversed() {
    return this.last + ', ' + this.first;
}
function Person(first, last) {
    this.first = first;
    this.last = last;
    this.fullName = personFullName;
    this.fullNameReversed = personFullNameReversed;
}
```

这样的写法的好处是我们只需要创建一次方法函数，在构造函数中引用它们。

还有更好的方法吗？ 

#### prototype

使用 prototype 来优化：

```javascript
function Person(first,last){
	this.first = first;
	this.last = last;
}
Person.prototype.fullName = function(){
	return this.first+' '+this.last;
};
Person.prototype.fullNameReversed = function(){
    return this.last + ', ' + this.first;	
};
var p3 = new Person("San","Zhang");
```



`Person.prototype` 是一个可以被`Person`的**所有实例共享的对象**。

它是一个名叫原型链（prototype chain）的查询链的一部：当你试图访问一个`Person`没有定义的属性时，解释器会首先检查这个`Person.prototype`来判断是否存在这样一个属性。所以任何分配给`Person.prototype`的东西对通过`this`对象构造的实例都是可用的。



这个特性功能十分强大，JS 运行你在程序中的任何时候修改原型中的一些东西，也即`在运行时修改已存在的对象`添加额外的方法。

比如给 Person 添加一个`sayHi`的方法

```javascript
var p3 = new Person("San","Zhang");
//p3.sayHi();//Uncaught TypeError: p3.sayHi is not a function
Person.prototype.sayHi = function(){
	console.log("Hi")
};
p3.sayHi();//Hi
```



另外有意思的是，我们还能给 JS 内置函数原型添加东西。比如给 String 添加一个返回逆序字符串的方法：

```javascript
String.prototype.reversed = function(){
	var r ="";
	for (var i = this.length - 1; i >= 0; i--) {
		r += this[i];
	}
	return r;
};
"Fitz".reversed();//ztiF
```

这个就很吊了啊!



原型链的根节点是 `Object.prototype`。

#### prototype 小结

1. `prototype` 有点类似 Java 中的基类，子类没有属性的时候就找父类，但是 prototype 更加强大，可以在运行时给已经存在的对象添加额外的方法；

2. 并且它还支持修改 JS 内置函数原型，也就是说可以扩展系统能力，屌屌屌；

3. 原型链的根节点是`Object.prototype`，它包括`toString（）`方法。



### 内部函数

JS 允许在`一个函数内部定义函数`，也可以叫做`嵌套函数`，它有个很重要的细节是，`它们可以访问父函数作用域中的变量`，但是父函数不能访问内部函数的变量。



```javascript
function parentFunc () {
	var a =1;
	function nestedFunc () {
		var b = 4;//parentFunc 无法访问 b
		return a+b;
	}
	return nestedFunc();
}
parentFunc();//5
```



如果某个函数依赖于其他的一两个函数，而这一两个函数*对你其余的代码没有用处*，你可以将它们嵌套在会被调用的那个函数内部，这样做**可以减少全局作用域下的函数的数量**，这有利于编写易于维护的代码。

这也是一个减少使用全局变量的好方法。当编写复杂代码时，程序员往往试图使用全局变量，将值共享给多个函数，但这样做会使代码很难维护。**内部函数可以共享父函数的变量**，所以你可以使用这个特性把一些函数捆绑在一起，这样可以有效地防止“污染”你的全局命名空间——你可以称它为`“局部全局（local global）”`。虽然这种方法应该谨慎使用，但它确实很有用，应该掌握。



### 闭包

闭包是 JS 中最强大抽象概念之一——但它也是最容易造成困惑的。

它到底干啥的？

```javascript
function makeAdder(a) {
	return function(b) {
		return a + b;//可以访问外部函数 a
	}	
}	
var add5 = makeAdder(5);
var add20 = makeAdder(20);
add5(6); // 11
add20(7); // 27
```

`makeAdder`函数会用一个参数来创建一个新的函数，再用另外一个参数来调研被创建的函数，然后将两个参数相加。

两个参数的来源问题：makeAdder 函数先接受调用它的参数，然后被创建的函数被调用时接收后面的参数。



这里发生的事情和前面介绍过的内嵌函数十分相似：一个函数被定义在了另外一个函数的内部，内部函数可以访问外部函数的变量。唯一的不同是，外部函数已经返回了，那么常识告诉我们局部变量“应该”不再存在。`但是它们却仍然存在`——否则 `adder` 函数将不能工作。也就是说，这里存在 `makeAdder` 的局部变量的两个不同的“副本”——一个是 `a` 等于 5，另一个是 `a` 等于 20。



那么那到底发生了啥？？？？？？

每当 JS `执行`一个函数时，都会创建一个`作用域对象`（scope object），用来`保存在这个函数中创建的局部变量`。它使用一切被传入函数的变量进行初始化，初始化后它包含一切被传入函数的变量。这与那些保存的所有全局变量和函数的全局对象类似，担任有一些很重要的区别：

1. 第一，`每次`函数被执行的时候，就会创建一个`新的`、特定的作用域对象（注：每次都创建一个新的）；
2. 第二，与全局对象不同的是，`你不能从 JS 代码中直接访问作用域对象`，也没有刻意遍历当前作用域对象中的属性的方法。

注：是否可以理解为在每次执行函数的时候都会把函数的参数用来构建一个新的作用域对象，它会保存参数，从而实现作用域的效果？



所以当调用`makeAdder`时，解释器创建了一个作用域对象，它带有一个属性：`a`（函数参数），这个属性被当做函数传入`makeAdder`函数。然后`makeAdder`返回一个新创建的函数`adder`。

通常，JS 的垃圾回收器会在这时回收`makeAdder`创建的作用域对象`b`，但是，`makeAdder`的返回值，新函数`adder`，拥有一个指向作用域对象`b`的引用。最终，作用域对象`b`不会被垃圾回收器回收，直到没有任何引用指向新函数`adder`。（感觉跟 Java 的 GC 规则差不多啊）



作用域对象组成了一个名为作用域链（scope chain）的调用链。它和JS 的对象系统使用的原型链相类似。



一个**闭包**，就是 `一个函数` 与其 `被创建时所带有的作用域对象` 的组合。（`闭包 = 函数 + 函数的作用域对象`）

闭包允许你你保存状态——所以，它们可以用来替代对象。

这里有些关于闭包的详细介绍：https://stackoverflow.com/questions/111102/how-do-javascript-closures-work



#### 闭包小结

1. JS 在`每次执行函数`的时候，都会把`传入函数的变量`都拿来创建一个新的、特定的`作用域对象`，用来`保存在这个函数中创建的局部变量`；
2. 闭包 就是 `一个函数` 与其 `被创建时所带有的作用域对象` 的组合；
3. `闭包 = 函数 + 函数的作用域对象`；



### 资料

https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/A_re-introduction_to_JavaScript

注意：call()部分没看完全

