# 装饰者模式

装饰者模式：  

**动态地将责任附加到对象上，若要扩展功能，装饰者提供了比继承更有弹性的替代方案。**  

装饰者模式可以给类 **动态地添加功能，而不需要修改已经存在的类**，类似一个`Wrapper`。  

装饰者有弹性，非常符合 `开闭原则`

## 特点  

1. 装饰者和被装饰者对象**拥有相同的超类型**  
2. 你可以用一个或多个装饰者包装一个对象  
3. 既然装饰者和被装饰者对象拥有相同的超类型，所以在任何需要原始对象（被包装的）的场合，可以用装饰过的对象替代它  
4. **装饰者可以在所委托被装饰者的行为之前或之后加上自己的行为，以达到特定的目的**  
5. 对象可以在任何时候被装饰，所以可以在运行时动态地不限量地用你喜欢的装饰者来装饰对象  


## 装饰者模式的缺点

**可能会引入大量的类**，导致理解起来并不容易，也可能会使使用者懵逼。   

可能会出现以下情况：  
```
D a = new A(new B(new C(new D)));
```

比较复杂，难以理解。  

## 具体使用

Java源码中的`I/O`就是装饰者模式，各种 `XXXStream`、`Reader` 和 `Writer`。  

比如：  

```
BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\testing.txt")));
```

## See also
[Decorator Design Pattern Example](http://javadesign-patterns.blogspot.com/p/decorator.html)
