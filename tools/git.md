# Git

[TOC]













### Git 配置



#### Git branch 不分页

配置 git branch 不以分页形式展示，默认情况下，查看分支会以分页形式展示，退出需要输入`q`，多一个步骤多一个麻烦，可以通过下面的配置关闭：

```shell
git config --global pager.branch false
```

More：https://stackoverflow.com/questions/48341920/git-branch-command-behaves-like-less