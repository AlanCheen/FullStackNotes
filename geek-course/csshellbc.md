# 《初识 Shell 编程》-笔记

> 本文 GitHub 地址：https://github.com/AlanCheen/FullStackNotes
>
> 作者公众号：程序亦非猿



[TOC]

### 01 什么是 Shell



Shell 是命令解释器，用于解释用户对操作系统的操作。

Shell 有很多

​	cat /ect/shells

### 02 Linux 的启动流程

BIOS-MBR-BootLoader(grub)-kernel-systemd-系统初始化-shell



bios->硬盘->



### 03 Shell脚本的格式 



- UNIX 的哲学：一条命令只做一件事。

- 为了组合命令和多次执行，使用脚本文件夹保存需要的命令
- 赋予该文件执行权限（chmod u+rx filename），默认新建的文件`只可读`，不可执行



`cd /var/ ; ls` 用 分号 分隔，就分开执行了

du -sh 展示文件所占用的大小

 du -sh * 展示每个文件占用的大小



bash 有些命令没有，所以需要加申明脚本用什么解释：

```shell
#!/bin/bash
```

这个声明被称为`Sha-Bang`（不知道什么鬼）



### 04 脚本不同执行方式的影响



当用 bash filename 执行的时候，`Sha-Bang` 会被当做注释，当用`./`方式运行，则会被理解为是 bash 的脚本。



- `bash xxx.sh`，开启新的进程运行，不需要有可执行权限，
- `./xxx.sh`  开启新的进程运行，`需要有可执行权限`，不然会报错`permission denied:xxx.sh`
- `source xxx.sh` ，在当前进程执行，
- `. xxx.sh` ，是`source`的简写方式，也是在当前进程，



开启子进程会出现一个情况就是父进程定义的变量获取不到。也即默认情况下，子进程获取不到父进程的变量。



- 内建命令不需要创建子进程

- 内建命令对当前 Shell 生效



### 05 管道



- 管道和信号一样，也是进程通信的方式之一
- 匿名管道（管道符）是 Shell 编程经常用到的通信工具
- 管道符是 `“|”`，将前一个命令执行的结果传递给后面的命令
  - ps | cat
  - echo 123 | ps



### 06 重定向



- 一个进程默认会打开`标准输入`、`标准输出`、`错误输出`三个`文件描述符`（0，1，2）
- 输入重定向符号`“<”`
  - read var < /path/to/file ，把文件的内容读到 var 里，可以通过 echo $var ，来获取
- 输出重定向符号，输出默认是到终端的：
  -  `“>”`，重写内容，会`覆盖`所有内容
  -  `">>"`， `追加`内容，不会覆盖
  -  `"2>"`，`错误`的时候写入，可以用来保存错误信息
  - `"&>"`，不论正确还是错误，都输入，全部重定向
  - echo 123 > /path/to/file ，把 123 写入到文件，如果没有则会新建文件
- 输入和输出重定向组合使用
  - cat > /path/ << EOF
  - I am $USER
  - EOF



重定向可以利用文件来`代替终端`输入、输出。



wc -l  行数

wc -l filename 可以查看文件有多少行

read

echo

cat

### 07 变量赋值



- 变量的定义
  - 不以数字开头
- 变量的赋值
- 变量的引用
- 变量的作用范围
- 系统环境变量
- 系统变量配置文件





1. shell 脚本的变量是弱类型，不区分类型
2. 变量名=变量值，a=123，`等号边上不能出现空格`，不然 shell 脚本会认为是命令
3. 使用 let 为变量赋值，let  a=10+20
4. 将命令赋值给变量，l=ls
5. `将命令结果赋值给变量`，使用`$()`或者``
   1. letc=$(ls -l /etc)
   2. letc=\`ls -l /etc\`
6. 变量赋值有空格等特殊字符可以包含在双引号或单引号中



例如：

```shell
cmd=`ll`
echo $cmd
```



### 08 变量引用及作用范围



#### 变量的引用

1. `${变量名}` ，称作对变量的引用；
2. echo ${变量名}，查看变量的值
3. `${变量名}`  在部分情况下可以省略为  `$变量`



```shell
string="1"
echo $string //输出 1
echo $string23 //没输出
echo ${string}23 //输出 123
```



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6nca02vw6j30cm088tas.jpg" alt="image-20190904114722624" style="zoom:50%;" />



#### 变量的作用范围



- 变量的默认作用范围：`只在自己的进程当中起作用`

- 变量的导出，可以让子进程获取到父进程的变量
  - export
- 变量的删除，删除变量
  - unset



例如定义一个 demo 的变量，然后新建个脚本尝试输出 demo 的值，分别在子进程跟当前进程执行该脚本：



```shell
➜  testshell demo="hello subshell"
➜  testshell vim d.sh
➜  testshell bash d.sh   #没有输出

# 需要执行权限
➜  testshell ./d.sh
zsh: permission denied: ./d.sh
➜  testshell chmod u+x d.sh
➜  testshell ./d.sh #没有输出

➜  testshell source d.sh #有输出
hello subshell
➜  testshell . ./d.sh #有输出
hello subshell
➜  testshell
```



可以看到 source 这种方式在当前进程执行，可以获取到 demo 变量，而子进程的执行方式不能获取到变量。



使用 export 关键字定义变量，可以让子进程获取到变量。

```shell
➜  testshell export demo="hello subshell"
➜  testshell bash d.sh
hello subshell
➜  testshell
```



### 09 环境变量、预定义变量与位置变量



- 环境变量：每个进程的每个 Shell 打开都可以获得到的变量
  - `set` 和  `env`
    - `env` ，可以查看当前已经有了的所有的环境变量
    - `set`，可以查看当前的预定义变量与位置变量
  - `$PATH`，命令搜索路径，只有在 PATH 里才能直接执行
    - `PATH=$PATH:/xxx` 可以把路径添加到 PATH，只对当前的终端生效，也即重新打开一个终端就没了，想要全局都可用得加`export`关键字；
  - `$PS1`，提示终端，修改它可以展示更多信息，更友好
- 预定义变量
  - `$?` ，`上一条命令是否正确执行`，在执行某条命令后执行`echo $?`，输出 0 为成功，1 出错，可以帮助确认上一条命令是否执行成功；
  - `$$`，可以`显示当前进程的 PID`，
  - `$0`，当前进程的名称，
- 位置变量
  - `$1 $2 ... $n`，可以用来`获取参数`，例如我们执行`cmd -l -b` 这里的`-l -b`就是参数，如果要获取它们就需要用到位置变量，需要注意当 n 大于 9 时，需要用`{}`括起来，如`${10}`；
  -  `$0` , 为执行的文件名(补充)；



新建一个 sh 脚本，定义两个变量接受参数，并输出：

```shell
#!/bin/bash

pos1=$1
pos2=$2

echo $pos1
echo $pos2
```

执行时传入两个参数：

```shell
➜  testshell bash 7.sh -a -l
-a
-l
```



假如有传入空值，为了防止变量为空可以设置默认参数，`${n-defaultValue}`，例如`pos2=${2-_}` 来指定默认值为`_`。类似其他语言中的默认参数。



修改后执行：

```shell
# 不设置 pos2，则是默认参数
➜  testshell bash 7.sh -a
-a
_
# 设置了 pos2，则是输入的参数
➜  testshell bash 7.sh -a -l
-a
-l
```



### 10 环境变量配置文件



- 配置文件
  1. `/etc/profile`，
  2. `/etc/profile.d`，
  3. `~/.bash_profile`，
  4. `~/.bashrc`，
  5. `/etc/bashrc`，



保存在`etc`目录下的配置文件是`所有用户都通用的`，不管你是 root 用户还是普通用户。

`~`下面的是`用户的家目录`，一般保存`用户特有`的配置。



分类成`profile`和`bashrc`是因为登录的时候也分为 login-shell 和 nologin-shell，`su - userName` (login shell)  profile+bashrc 都用到,`su userName` 的话只有 4、5 两个被执行（bashrc），因为未登录，所以加载不完全。

- profile
- bashrc，未登录的



**注意：**所以推荐用`su - userName`来登录，这样才能加载所有的配置文件。



**配置文件的加载顺序**



当我们执行命令的时候会加载配置文件，当命令重复的时候，后加载的会把前面加载的覆盖掉。

顺序为：`/etc/profile` => `~/.bash_profile`=> `~/.bashrc` => `/etc/bashrc`



配置文件是`每次打开终端的时候加载的`，所以如果在配置文件中新添加配置是`不会立即生效的`。

那么如何生效呢？

1. 退出终端，重启
2. 执行 `source fileName` , （其实也就相当于手动加载一遍配置）



### 笔记



#### 执行 sh 脚本文件来生成新的 sh 脚本



```shell
#!/bin/bash

cat > ./c.sh <<EOF
#!/bin/bash

echo "hello Fitz"
EOF
```



该脚本的意思是在当前目录下生成一个`c.sh`文件，内容为`<<EOF` 跟 `EOF` 之间的内容。



执行后会生成如下的文件：

```shell
#!/bin/bash

echo "hello Fitz"
```



我们在 Shell 脚本里还能获取系统的变量，比如`$USER`、`$PATH`，写入到文件中时会获取对应的值， 然后把值写入文件。



### 总结



命令：

- `cat`，连接文件并打印到标准输出设备上

- `read`，输入

- `echo`，输出

- `wc -l filename`，查看文件有多少行内容

- `ps`  查看进程信息

- `du -sh` 展示当前目录文件所占用的`总大小`

-  `du -sh *` 展示当前目录`每个文件`占用的大小

- `chmod u+rx filename` ，给文件添加`执行`权限

- `#！/bin/bash` ，声明放 shell 脚本第一行

  

  

课程地址：https://time.geekbang.org/course/detail/227-128835





### 联系我

<img src="http://ww1.sinaimg.cn/large/98900c07ly1g69mj0q0z0j20v60wc41n.jpg" style="zoom:25%"/>

