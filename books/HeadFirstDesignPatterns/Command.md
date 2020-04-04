# 命令模式

命令模式: **将请求封装成对象，这可以让你使用不同的请求、队列，或者日志请求来参数化其他对象。命令模式也可以支持撤销操作。**    

1. 将请求封装成对象，比如 `Runnable`  
2. 将 **发出请求的对象** 和 **接受与执行这些请求的对象** 分隔开来。  

![类图](http://ww3.sinaimg.cn/large/98900c07jw1f60h04snyqj21540pqwke.jpg)

一些应用：日程安排、线程池、工作队列等。  

感觉Android中的 `Handler` 相关也可以算是：把命令(Runnable对象) `post(r)` 到 MessageQueue 里，Looper再取出来处理~，
不需要管Runnable里做的到底什么操作，只要调用它的 `run()` 方法就行了。


Java代码示例：  

```
/** The Command interface */
public interface Command {
   void execute();
}
/** The Invoker class */
public class Switch {
   private List<Command> history = new ArrayList<Command>();
   public void storeAndExecute(Command cmd) {
      this.history.add(cmd); // optional
      cmd.execute();
   }
}
/** The Receiver class */
public class Light {
   public void turnOn() {
      System.out.println("The light is on");
   }
   public void turnOff() {
      System.out.println("The light is off");
   }
}
/** The Command for turning on the light - ConcreteCommand #1 */
public class FlipUpCommand implements Command {
   private Light theLight;
   public FlipUpCommand(Light light) {
      this.theLight = light;
   }
   @Override    // Command
   public void execute() {
      theLight.turnOn();
   }
}
/** The Command for turning off the light - ConcreteCommand #2 */
public class FlipDownCommand implements Command {
   private Light theLight;
   public FlipDownCommand(Light light) {
      this.theLight = light;
   }
   @Override    // Command
   public void execute() {
      theLight.turnOff();
   }
}
/* The test class or client */
public class PressSwitch {
   public static void main(String[] args){
      // Check number of arguments
      if (args.length != 1) {
         System.err.println("Argument \"ON\" or \"OFF\" is required.");
         System.exit(-1);
      }
      Light lamp = new Light();
      Command switchUp = new FlipUpCommand(lamp);
      Command switchDown = new FlipDownCommand(lamp);
      Switch mySwitch = new Switch();
      switch(args[0]) {
         case "ON":
            mySwitch.storeAndExecute(switchUp);
            break;
         case "OFF":
            mySwitch.storeAndExecute(switchDown);
            break;
         default:
            System.err.println("Argument \"ON\" or \"OFF\" is required.");
            System.exit(-1);
      }
   }
}
```

宏命令，可以存储一系列命令，一起执行，达到一个『按钮』实现多个功能的目的。  

可以用个List来保存历史命令，来实现撤销功能。  
## 要点

1. 命令模式将发出请求的对象和执行请求的对象解耦
2. 在被解耦的两者之间是通过命令对象进行沟通的。命令对象封装了接受者和一个或一组动作。
3. 调用者通过命令对象的 `execute()` 发出请求，这会使得接受者的动作被调用。
4. 调用者可以接受命令当做参数，甚至在运行时动态地进行。  
5. 命令可以支持撤销，做法是实现一个 `undo()`方法来回到 `execute()`被执行前的状态。
6. 宏命令是命令的一种简单的延伸，允许调用多个命令。宏方法也可以支持撤销。
7. 实际操作时，很常见使用『聪明』命令对象，也就是直接实现了请求，而不是将工作委托给接受者。
8. 命令也可以用来实现日志和事务系统。


## See also

[Command pattern](https://en.wikipedia.org/wiki/Command_pattern)  
[Command Design Pattern Example](http://javadesign-patterns.blogspot.com/p/co.html)  
