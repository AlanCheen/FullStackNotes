# 深入浅出 Shell 脚本编程

> 本文基于 Mac OS & bash



## 什么是 Shell 脚本？



先不套弄概念，举个例子：

新建立一个 hello-shell 脚本并运行，输出 “Hello Shell”：

```shell
➜  head-first-shell git:(master) ✗ vim hello-shell.sh
➜  head-first-shell git:(master) ✗ chmod +x hello-shell.sh
➜  head-first-shell git:(master) ✗ ./hello-shell.sh
Hello Shell
```

脚本内容如下：

```shell
#!/bin/bash
echo "Hello Shell"
```



简单释义：

1. `#!/bin/bash` ：指定脚本解释器 bash，`#!`是个约定，`/bin/bash`是解释器的路径；
2. `echo "Hello Shell"`：输出字符串，`echo`是 linux 自带的命令；



## Shell 和 Shell 脚本概念



shell 是一种应用程序，通过它可以访问操作系统内核的服务。

shell 脚本是一种专门为 shell 编写的脚本程序，它是一种解释性语言。



通常我们所说的 “shell 编程”是指 shell 脚本编程。



## Shell 脚本能干些什么？



shell 脚本可以做一些简单的操作，并可以实现自动化。

例如文件操作、软件安装和下载这些操作可以用 shell 脚本来封装。

另外需要注意的是 shell 脚本没有复杂的语法，没有数据结构、面向对象等东西，功能有限制。



## Shell 脚本解释器

解释性语言需要一个解释器，用来解释并执行脚本，shell 脚本也是，常见的解释器有 sh、bash 和 zsh 等。



当前的主流系统都支持 shell 编程，Linux 默认带了 shell 解释器，Mac OS 也自带了很多。



Mac 上可以执行`ll /bin/*sh`查看一下：

```shell
➜  ~ ll /bin/*sh
-r-xr-xr-x  1 root  wheel   618416  9 21  2018 /bin/bash
-rwxr-xr-x  1 root  wheel   379952  9 21  2018 /bin/csh
-r-xr-xr-x  1 root  wheel  1282864  9 21  2018 /bin/ksh
-r-xr-xr-x  1 root  wheel   618480  9 21  2018 /bin/sh
-rwxr-xr-x  1 root  wheel   379952  9 21  2018 /bin/tcsh
-rwxr-xr-x  1 root  wheel   610224  9 21  2018 /bin/zsh
```



1. `sh`：Bourne Shell，由 Bell Labs 开发，路径为`/bin/sh`；
2. `bash`：Bourne Again Shell，是 sh 的替代品，兼容性更好，路径为`/bin/bash`；



sh 跟 bash 都有着非常好的兼容性。



## Shell 脚本的编写与运行

### 编写

shell 脚本只需要普通的编辑器就可以编写，甚至只需要 vim，不需要用到 IDE。

shell 脚本扩展名为`.sh`。



所以随便新建一个以`.sh`结尾的文件即可，通常第一行是`#!/bin/bash`，它指定了运行脚本的解释器。

### 运行

运行脚本有两种方法：

**1）作为可执行的脚本**

首先需要赋予可执行的权限，`chomod +x hello-shell.sh`

再执行,`./hello-shell.sh`。注意不能直接写名字，否则会去 PATH 去找命令。



**2）作为解释器的参数**

当我们指定解释器，同时把 shell 脚本作为参数时，也可以执行脚本，此时不需要执行权限。

并且此时会忽略 sh 里的指定的 shell 解释器，但是通常我们还是会写上。

```shell
➜  head-first-shell git:(master) bash hello-shell.sh
Hello Shell
```



## Shell 脚本的语法



### 变量

定义变量 & 使用变量

```shell
name="程序亦非猿"
echo $name
echo ${name}
echo "hello ${name}"

name="Fitz"
echo ${name}

readonly name #只读变量，不能赋值

unset name #删除变量
```



定义变量的时候**变量名跟等号之间不能有空格**;一个变量可以**被多次赋值**。

使用变量的时候用美元符号引用，大括号非必须，但是推荐加上，可以帮助解释器识别变量的边界，在某些场景下必须需要。



1. `readonly` 定义只读变量；
2. `unset` 删除变量；



变量的 scope：

1. 局部变量：
2. 环境变量：PATH 里的变量，用 export 可以定义；
3. shell 变量：



### 数组

用**括号**来表示数组，数组元素用**"空格"**符号分隔开。

```shell
array=(value1 value2 ... n)
#赋值
array[0]=value
#读取某个元素
${array[index]}
#获取所有元素
${array[@]}
```



### 注释

以`#`开头的行就表示是注释，并且没有多行注释，只能一行一行加`#`。

有个骚操作就是定义一个不调用的函数，用来写注释。



### 字符串



1. **单引号**中的内容会**原样子输出**，**变量会失去效果**！也不能用转义符号！
2. **双引号**可以引用变量，也可以出现转义符号！所以推荐使用双引号！



举个例子：

```shell
#!/bin/bash

name="程序亦非猿"

#单引号不能引用变量
str='a simple string ${name}'
echo ${str}

str="Hello,${name},\"大风起兮云飞扬\"!"
echo ${str}
```

运行的输出：

```shell
➜  head-first-shell git:(master) ✗ ./string.sh
a simple string ${name}
Hello,程序亦非猿,"大风起兮云飞扬"!
```



#### 字符串操作



获取字符串长度，`${#string}`，例如：

```shell
#获取字符串的长度
str="abc"
echo "${str} 的长度为 ${#str}" #abc 的长度为 3
```



获取子字符串，`${string:from:to}`，index 也是从 0 开始的，例如：

```shell
#提取字符串
str="abcdef"
echo "${str:1:2}" #bc
echo "${str:3}" #def
```



更多：http://tldp.org/LDP/abs/html/string-manipulation.html



### 数组



### 流程判断 控制语句

if else , while ...



#### if else



##### if

```shell
if [[ condition ]]; then
			command1
			...
			commandn
fi
```



注意条件写在`[[ ]];` 内部，并且两边都有空格，然后以`;`结束，并且跟`then`  之间有空格，最后`fi`结束。



例如：

```shell
#if
num=3
if [[ num>1 ]]; then 
	echo "${num} 大于 1"
fi
```





##### if else

```shell
if condition
then 
		 command1
		 ...
		 commandN
else 
			command
fi
```





##### if else if else



```shell
if condition
then
	command
elif condition
	command
else
	command
fi
```







### 文件相关 



## 用户的输入输出



## 常用 linux 命令

ps 

grep

awk

sed

xargs

curl



## 资料

https://www.runoob.com/linux/linux-shell.html

https://github.com/qinjx/30min_guides/blob/master/shell.md

https://bash.cyberciti.biz/guide/Main_Page

https://github.com/AlanCheen/head-first-shell