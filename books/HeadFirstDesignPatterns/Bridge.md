# 桥接模式

桥接模式通过 **将实现和抽象放在两个不同的类层次中而使它们可以独立改变**。  

使用桥接模式不只改变你的实现，也改变你的抽象。

![](http://ww4.sinaimg.cn/large/98900c07jw1f6ef0jrt68j20rs0dwq4l.jpg)



上code：  

```
/** "Implementor" */
interface DrawingAPI {
    public void drawCircle(double x, double y, double radius);
}

/** "ConcreteImplementor"  1/2 */
class DrawingAPI1 implements DrawingAPI {
    public void drawCircle(double x, double y, double radius) {
        System.out.printf("API1.circle at %f:%f radius %f\n", x, y, radius);
    }
}

/** "ConcreteImplementor" 2/2 */
class DrawingAPI2 implements DrawingAPI {
    public void drawCircle(double x, double y, double radius) {
        System.out.printf("API2.circle at %f:%f radius %f\n", x, y, radius);
    }
}

/** "Abstraction" */
abstract class Shape {
    protected DrawingAPI drawingAPI;

    protected Shape(DrawingAPI drawingAPI){
        this.drawingAPI = drawingAPI;
    }

    public abstract void draw();                             // low-level
    public abstract void resizeByPercentage(double pct);     // high-level
}

/** "Refined Abstraction" */
class CircleShape extends Shape {
    private double x, y, radius;
    public CircleShape(double x, double y, double radius, DrawingAPI drawingAPI) {
        super(drawingAPI);
        this.x = x;  this.y = y;  this.radius = radius;
    }

    // low-level i.e. Implementation specific
    public void draw() {
        drawingAPI.drawCircle(x, y, radius);
    }
    // high-level i.e. Abstraction specific
    public void resizeByPercentage(double pct) {
        radius *= (1.0 + pct/100.0);
    }
}

/** "Client" */
class BridgePattern {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[] {
            new CircleShape(1, 2, 3, new DrawingAPI1()),
            new CircleShape(5, 7, 11, new DrawingAPI2())
        };

        for (Shape shape : shapes) {
            shape.resizeByPercentage(2.5);
            shape.draw();
        }
    }
}
```


解析： 
具体实现为 `Implemetor`,抽象为 `Shape`,放在不同的层次，抽象拥有实现的引用，具体实现的修改不需要修改抽象



用途:  

1. 适合使用在需要跨越多个平台的图形和窗口系统上。
2. 当需要用不同的方式改变接口和实现时，你会发现桥接模式很好用。


优点：

1. 将实现予以解耦，让它和界面之间不再永久绑定。
2. 抽象和实现可以独立扩展，不会影响到对方。
3. 对于『具体的抽象类』所做的改变，不会影响到客户。

缺点： 

1. 桥接模式的缺点是增加了复杂度。


## See also  

[Bridge](https://en.wikipedia.org/wiki/Bridge_pattern)
