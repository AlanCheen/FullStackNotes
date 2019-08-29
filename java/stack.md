# Stack（栈）



栈是一种 `LIFO`(last in first out，后进先出)的数据结构，最后 push 进去的元素最先被 pop 出来。



不过 Stack 的作者更推荐 ArrayDeque 而不是 Stack，实际上也确实很少看到 Stack 的使用。



### Java Stack<E> 

```java
public class Stack<E> extends Vector<E> 
```



在 Java 里有 Stack<E>，还有 Deque<E>等实现，以 Stack<E> 为例。



Stack<E> 的方法：

- `push（E）`，*入栈*，把元素放到栈顶；
- `pop()`，*出栈*，取出栈顶元素，这个操作*会把栈顶元素移除*；
- `peek()`，查看栈顶元素，*只是看不会移除*；
- `empty()`，检查站是否为空；
- `search(E)`，搜索，这个得注意， 有点奇怪，返回的数据是从栈顶到那个元素所在的位置的距离（index 从 1 开始）；



```java
public static void main(String[] args) {

    Stack<Integer> stack = new Stack<>();

    stack.push(0);
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
  	//4 栈顶
    //3
    //2
    //1
    //0 栈底
    //查看栈顶元素，但是不移除
    Integer peek = stack.peek();
    System.out.println("peek:"+peek);//4

    //查看栈顶元素，并移除
    Integer pop = stack.pop();
    System.out.println("pop:"+pop);//4

    //因为 pop()方法把 4 移除了
    System.out.println("peek2:"+ stack.peek());//3
    //size 4
    System.out.println("size:"+ stack.size());//4
    //搜索到 index 为 1，然后从栈顶到 1 的位置是 4-1=3
    System.out.println("search 0:"+ stack.search(1));//3

}
```

