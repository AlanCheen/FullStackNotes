# 工欲善其事必先利其器



[TOC]

### Mac App



#### Fork

Fork，一款 git client

https://git-fork.com/



#### iPic

iPic &iPic Mover

https://toolinbox.net/iPic/



#### Typora

配置 Typora 别名

```
vim .bash_profile
##增加如下
alias typora="open -a typora"
```
 然后就可以在终端使用`typora foo.md` 来打开 md 文件了。



#### tree



tree 是一个工具，可以让我们在终端里输出一个树状的目录结构。



用 brew 就可以安装了

```shell
brew install tree
```



然后在目录下执行`tree -N`就行。例如：

```shell
➜  tools git:(master) ✗ tree -N
.
├── README.md
├── android-studio.md
├── sublime-text.md
└── webstorm.md

0 directories, 4 files
```



tree 的命令参数： 

- `--help`，可以查看参数
- `-N`，直接列出文件和目录名称，包括控制字符，中文不会乱码。
- `-L`，指定目录层级，例如`tree -L 1` 只会输出当前目录的一级子目录
- `-d`，只打印目录
- `-f`，打印全路径
- `-a`，打印全部文件和目录



另外还能指定如何排序以及过滤条件，可以`--help`学习。



#### GIF Brewery 功能强大的动图制作工具



GIF Brewery，该款软件除了能够将视频文件自动转换为GIF动画文件，还支持在转换前剪切大小、调整帧数、添加标题等功能，是一款十分优秀的软件。

视频 to  GIF ，只 3 步：

1. 通过快捷键 ⌘+O 导入视频文件
2. 点击左上角的「朋友圈」图标 （误） 自动将视频转换为 GIF
3. 最后，选择路径保存，搞定


### Chrome 



#### 微博图床

微博图床：https://github.com/Suxiaogang/WeiboPicBed

