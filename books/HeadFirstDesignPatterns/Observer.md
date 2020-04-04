# 观察者模式

观察者模式：  
**定义了对象之间的一对多依赖，这样依赖，当一个对象改变状态时，它的所有依赖者都会受到通知，并自动更新。**  


![](http://ww3.sinaimg.cn/large/98900c07jw1f5zaullrcij2099060t8v.jpg)

出版者（Subject） + 订阅者（Observer） = 观察者模式  

观察者依赖于主题。  


![UML](http://ww4.sinaimg.cn/large/98900c07gw1f5uino6cvoj20rs0bh0ub.jpg)  

**观察者模式提供了一种对象设计，让主题和观察者之间松耦合。**  

1. 观察者与主题之间依赖于接口，主题不需要知道观察者的具体实现
2. 可以动态添加删除观察者，对主题没有任何影响  
3. 新增新类型的观察者对主题没有影响，主题的代码不需要修改，而且旧的观察者也不受影响  
4. 观察者与主题都可以独立复用，因为是松耦合  
5. 只要约定的接口不改变，修改主题或观察者任何一方，都不会影响另一方  



比如 EventBus 就是观察者模式。  
注意： **观察者模式会造成内存泄漏，一定要记得取消订阅**  



## See also
[Observer pattern](https://en.wikipedia.org/wiki/Observer_pattern)  
[Observer Design Pattern Example](http://javadesign-patterns.blogspot.com/p/page22.html)  
