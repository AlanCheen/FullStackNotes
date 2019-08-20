# LinkedHashMap





访问顺序对于实现高速缓存的“ 最近最少使用”原则十分重要。例如，可能希望将访问 频率高的元素放在内存中， 而访问频率低的元素则从数据库中读取。当在表中找不到元素项 且表又已经满时， 可以将迭代器加入到表中， 并将枚举的前几个元素删除掉。这些是近期最 少使用的几个元素。



### 特性

- 可以构造一个`访问顺序`模式而不是`插入模式`的 LinkedHashMap，实现 LRU 算法。




### LinkedHashMap 实现 LRU 算法

使用 LinkedHashMap 能够记住访问顺序的这个特性可以实现 LRU 算法。

使用这个构造器就可以实现 LRU 算法：

```java
public LinkedHashMap(int initialCapacity,
                     float loadFactor,
                     boolean accessOrder)
```



链接散列映射将用`访问顺序`， 而不是插入顺序， 对映射条目进行迭代。 每次调用 get 或put, 受到影响的条目`将从当前的位置删除`，`并放到条目链表的尾部`(只有条目在链表中的位 置会受影响， 而散列表中的桶不会受影响。一个条目总位于与键散列码对应的桶中)。



举个例子：

```java
LinkedHashMap<String, String> lru = new LinkedHashMap<>(16, 0.75f, true);
lru.put("A", "A");
lru.put("C", "C");
lru.put("D", "D");
lru.put("B", "B");
lru.put("E", "E");
System.out.printf("-------------\n");
lru.forEach(new BiConsumer<String, String>() {
    @Override
    public void accept(String s, String s2) {
        System.out.println("accept() called with: s = [" + s + "], s2 = [" + s2 + "]");
    }
});
//
System.out.printf("-------------\n");
lru.get("A");
lru.get("B");
lru.get("C");
lru.get("D");
lru.get("E");
lru.forEach(new BiConsumer<String, String>() {
    @Override
    public void accept(String s, String s2) {
        System.out.println("accept() called with: s = [" + s + "], s2 = [" + s2 + "]");
    }
});
lru.put("A","A");// A 会再次被放到后面
```



输出的日志是：

```java
-------------
accept() called with: s = [A], s2 = [A]
accept() called with: s = [C], s2 = [C]
accept() called with: s = [D], s2 = [D]
accept() called with: s = [B], s2 = [B]
accept() called with: s = [E], s2 = [E]
-------------//这里输出的已经不是原来的顺序了
accept() called with: s = [A], s2 = [A]
accept() called with: s = [B], s2 = [B]
accept() called with: s = [C], s2 = [C]
accept() called with: s = [D], s2 = [D]
accept() called with: s = [E], s2 = [E]
```







### ``