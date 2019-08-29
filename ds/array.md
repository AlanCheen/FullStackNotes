# 数组 Array

[TOC]

### 什么是数组？



数组是由`相同类型`的元素（element）的集合所组成的数据结构，分配一块`连续的内存`来存储。利用元素的`索引`（index）可以*计算出该元素对应的存储地址*。（索引也称为下标，可将数组的值对应到存储对象，在 Java 里 index 从 0 开始，到 length-1）



数组第一个元素的存储器地址称为`第一地址`或`基础地址`。



例如，索引为0到9的32位整数数组，可作为在存储器地址2000，2004，2008，...2036中，存储10个变量，因此索引为i的元素即在存储器中的2000+4×i地址。



在算法的描述中，数组一词特别着重意义为`关系数组`或“抽象的数组”，一种理论上的计算机科学模型（抽象数据类型或 `ADT`），专注于数组的基本性质上。(没懂啥意思)



#### 多维数组



一维数组就是最简单的数据结构。数组还可以是多个维度的：



```java
//二维数组
int[][] doubleArray = {
        {1,2,3},
        {4,5,6},
        {7,8,9},
};
```



### 数组的操作



#### 数组的初始化



数组的初始化的前提是 知道数组的长度，也就是说我们在初始化数组的时候就需要指定长度，并且不能再次扩展。



在 Java 中有如下几种方式：

```java
public static void main(String[] args) {
    int[] array = new int[5];
    array[0] = 1;
    array[1] = 2;
    array[2] = 3;
    array[3] = 4;
    array[4] = 5;
  
    int[] array2 = new int[]{1, 2, 3, 4, 5};
  
    int[] array3 = {1, 2, 3, 4, 5};
}
```



1. `new int[5]` ，只指定数组长度，不指定元素；
2. `new int[]{1,2,3,4,5}`，指定元素，从而也指定了长度；
3. `{1, 2, 3, 4, 5}`，第二种方法的另外一种写法，本质是一样的；



#### 数组的遍历



在 Java 有 `for`、 `foreach` 等方式遍历。



数组的元素可以直接通过`下标`来获取，`array[index]`。

```java
int[] array3 = {1, 2, 3, 4, 5};
for (int i = 0; i < array3.length; i++) {
    int v = array3[i];
    System.out.println(v);
}
```



随机查找，复杂度 O(1)。



#### 数组的赋值 TODO



数组元素赋值跟遍历一样，通过 index 就能访问元素，直接赋值即可，`array[index]=value`。



#### 数组的插入 TODO



数组的插入比较麻烦，在插入的时候需要把后面的元素都往后移动，并且还有可能溢出。



例如，想在`{1，3，4}` 数组中插入 2，变成` {1,2,3,4}`，需要把 3，4 挪到后面，然后把 2 插入。



#### 数组的删除 TODO





### 数组的效率



| 操作 | 时间复杂度 | 空间复杂度 | 备注                          |
| ---- | ---------- | ---------- | ----------------------------- |
| 遍历 | O(1)       |            | 能通过 index 直接寻址访问元素 |
| 插入 | O(n)       |            | 插入需要把元素移动到后面      |
| 删除 | O(n)       |            | 删除需要把元素移动到前面      |



### 数组的特性总结



数组设计之初是在形式上依赖于内存分配而成的，所以必须在使用前必须预先请求内存空间，使得数组有以下特性：

1. `数组里的元素类型一致`；
2. `初始化数组需要一块连续的内存`；
3. `初始化后大小固定，不能再改变`；



特性 1 比较好理解，数组的元素类型需要一致，这样才能计算元素的位置。

特性 2，数组的元素在内存的排列是连续的，所以在初始化数组的时候要求内存是连续的，当数组长度很大时需要大块的内存。有时候会出现总内存够大，但是不足以分配大数组的情况。例如有 5M 的内存，只有 3M 的内存是连续的，那么当申请一个 4M 的数组的时候就会出现内存不足的情况。这种情况也可以说成`内存伪溢出`。



### 总结



如果想要使用可变大小的数据类型，可以用 ArrayList/Vector 等`动态数组`。



### 资料

[https://zh.wikipedia.org/wiki/%E6%95%B0%E7%BB%84](https://zh.wikipedia.org/wiki/数组)