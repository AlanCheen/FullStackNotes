# LinkedList（链表）



```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```







LRU 算法



### 特性

- 双端队列，支持在头部和尾部同时添加或删除元素；
- 支持扩容



### 主要方法



- `addFirst(E)`，
- `addLast(E)`，
- `removeFirst()`，
- `removeLast()`，
- 