
## 第12章 泛型程序设计

泛型程序设计(Generic programming)意味着编写的代码可以被很多不同的类型的对象所重用  

泛型在集合框架里拥有着非常广泛的运用,如果没有泛型估计是一大堆Object以及强转代码吧!  

想想都可怕啊!    

### 泛型类(generic class)

泛型类就是具有一个或多个**类型变量**的类,如

```
public class Pair<T,V>{
	T first;
	T second;
	V value;
}
```

什么是类型变量?  

**上诉代码中的 T和V 就是类型变量 ,用<>括起来,放在类名后面**  

类型变量使用大写形式,且比较短  

一些常用的:  

- E 表示集合的元素类型  
- K 和V 分别表示表的关键字与值的类型  
- T(U或者S) 表示任意类型

### 泛型方法  

```
public Class A{
	public static <T>T test(T t){
		return t;
	}
	public static <T> void hi(T t){
	}
}
```

类型变量放在修饰符(public static等)的后面,返回类型的前面。  


### 类型变量的限定

当我们想要**对类型变量加以约束**的时候我们就需要用到**限定符** 。  


#### 子类型限定

```
public static <T extends Comparable>T min(T[] a)...
```

表示`T`需要实现或继承Comparable接口,值得注意的是这里是`extends`。  

这里的`extends`表示`T`必须是子类型(实现或继承)    

当然也可以用多个限定符来限制类型变量(用`&`):    

`T extends Comparable&Serializable`  

#### 通配符 的超类型限定


通配符用`?`来表示,超类型限定(supertype bound)用`super`关键字表示  

值得注意的是**超类限定只能用于通配符**  

`Pair<? extends Employee>`表示任何泛型Pair类型,它的类型参数是Employee的子类  

**? super Manager**   

将通配符限制为Manager的所有超类型  

====

## 实例  
写了个练习的小例子,总结一下:  

```
public  class GenericPractice {

    public static void main(String[]args){

        Person person = new Person();
        Hero hero = new Hero();
        Knight knight = new Knight();

        attack(person);// wrong !!!
        attack(hero);
        attack(knight);

        List<Person> persons = new ArrayList<>();
        List<Hero> heros = new ArrayList<>();
        List<Knight> knights = new ArrayList<>();
        group(persons);
        group(heros);
        group(knights);//wrong !!!

    }

    public static <T extends Hero> void attack(T t){
    }

    public static void group(List<? super Hero> group){
    }
    public static class Person{
    }
    public static class Hero extends Person{
    }
    public static class Knight extends Hero{
    }
}
```

留点笔记:

前提:  
`Knight extends Hero`,`Hero extends Person`  

attack方法使用`<T extends Hero>`来限定,即`T`必须是`Hero`类本身或者子类,所以`attack(person)`不能通过编译.  

group方法使用`<? super Hero>`来限定,即`?`必须是`Hero`类的本身或者父类,所以`group(knights)`不能通过编译.  







