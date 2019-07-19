# 数组



大小已知，不可扩容，内存连续的数据结构。



### 数组实例化

```
// A
int[] array2 = new int[3];
// B
int[] array = new int[]{0, 1, 2};
```



A 写法，在实例化的时候*只指定数组大小*，但是不指数组元素。

B 写法，在实例化时就*指定了元素*，并也确定了*数组的大小*。



### 数组的复制



从 A 数组写到 B 数组，或者说是 copy，推荐用系统的 `System.arraycopy` 方法。

```
System.arraycopy(array, 0, array2, 0, array.length);
```



表示从 array 的第 0 个开始 copy 到 array2(从0 开始赋值)，copy array.lenth 个元素。