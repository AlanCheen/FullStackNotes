# Closure 闭包



A closure is one way of supporting [first-class functions](https://en.wikipedia.org/wiki/First-class_function); it is an expression that can reference variables within its scope (when it was first declared), be assigned to a variable, be passed as an argument to a function, or be returned as a function result.



 In JavaScript, if you use the `function` keyword inside another function, you are creating a closure.



Simply accessing variables outside of your immediate lexical scope creates a closure**.



闭包不一定非要是一个被 return 的函数

```javascript
function foo(x){
  
  var tmp = 1;
  
  function adder(x,y){
    return tmp + x + y;//这个其实也是闭包
  }
}
```











https://stackoverflow.com/questions/111102/how-do-javascript-closures-work