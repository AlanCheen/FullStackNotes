# 《漫画算法——小灰的算法之旅》



### 第 1 章 算法概述



### 1.1 算法和数据结构

计算机科学领域的算法，它的本质是一些列程序指令，用于解决特定的运算和逻辑问题。



衡量算法好坏的标准：

1. 时间复杂度
2. 空间复杂度



算法的应用领域多种多样：

1. 运算
2. 查找
3. 排序
4. 最优决策
5. 面试

##### 数据结构

数据结构是数据的组织、管理和存储格式， 目的是为了高效地访问和修改数据。



1. 线性结构
   1. 数组
   2. 链表
   3. 栈
   4. 队列
   5. 哈希表
2. 树
   1. 二叉树
   2. 二叉堆
   3. ...
3. 图，更加复杂，多对多的关联关系
4. 其他
   1. 调表
   2. 哈希链表
   3. 位图



#### 1.2 时间复杂度



衡量时间复杂度得刨去硬件影响，引入了`基本操作执行次数`这个概念。



设 T(n)为程序基本操作执行次数的函数（也可以认为是程序的相对执行时间函数），n 为输入规模。



常见的 4 种：

- 线性的，例如 T(n) = 3n
- 对数，T(n)=5$log_2{n}$
- 常数，T(n)=2
- 多项式的，T(n) = 5$n^2$+0.5n



为了解决在 n 取不同值，相同算法可能有不同结果这个问题，引入了`渐进时间复杂度（asymptotic time complexity）` ：==若在函数 f(n)，使得当 n 趋近于无穷大时，T(n)/f(n)的极限值为不等于零的常数，则称 f(n)是 T(n)的同数量级函数。记作 T(n)=O(f(n)) ，O 为算法的渐进时间复杂度，简称为*时间复杂度*。==

因为渐进时间复杂度用大写 O 来表示，所以也被称为`大 O 表示法`。



如何推导出时间复杂度呢？有如下几个原则；

1. `如果运行时间是常数量级，则用常数 1 表示`；
2. `只保留时间是函数中的最高阶项`；
3. `如果最高阶项存在，则省去最高阶项前面的系数`；



其实很简单，如果是常数则用 1，如果不是，则只保留最高阶项并去掉系数（当 n 无穷大时系数什么的的影响都可以被忽略）。



#### 空间复杂度







