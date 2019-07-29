# Var Let Const





用 var let const 尝试定义同一个变量两遍得到的结果：

**用 var**：

```js
var a = 555
var a = 666
console.log(a)
//输出 666
```

**用 let:**

```js
let b = 1
let b = 2;
console.log(b)
```

报错：

```
Uncaught SyntaxError: Identifier 'b' has already been declared
```



**用 const：**

```js
const c = 1
const c = 2
console.log(c)
```

报错：

```js
Uncaught SyntaxError: Identifier 'c' has already been declared
```



### 总结

1. var 可以声明定义一个变量多次，而 let、const 则不行；
2. 





定义变量，**优先使用 const，let 次之，尽量避免使用 var**。







