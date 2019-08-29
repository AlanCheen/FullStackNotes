# 堆栈 Stack

[TOC]

### 什么是堆栈？



堆栈是计算机科学中的一种抽象数据类型，只允许在有序的线性数据集合一端（称为栈顶 top）进行加入数据（push）和移除数据（pop）的运算。(入栈出栈都在栈顶)



按照后进先出（LIFO，Last In First Out）的原理运作。



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6fg76btgxj30b407zmxb.jpg" style="zoom:50%" />



堆栈常用`一维数组`或`链表`来实现。



### 堆栈的操作



- `入栈`，push，将数据放入栈的顶部；
- `出栈`，pop，将栈顶数据移除；



### 堆栈的特性



1. 先进后出，后进先出；
2. 除了头尾节点之外，每个元素都有一个前驱，一个后继；



### Java 中的堆栈



Java 中有 Stack 以及 ArrayDeque 可以实现栈的功能，推荐使用 ArrayDeque。



### 栈的应用

todo

- [回溯](https://zh.wikipedia.org/wiki/回溯法)
- [递归](https://zh.wikipedia.org/wiki/递归)
- [深度优先搜索](https://zh.wikipedia.org/wiki/深度优先搜索)



### 资料

https://zh.wikipedia.org/wiki/%E5%A0%86%E6%A0%88