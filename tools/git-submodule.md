# Git Submodule



## 常用命令



git submodule init 

- add：添加一个仓库作为 submodule
- init：初始化 submodule
- update：更新 submodule
- deinit：注销一个 submodule
- status：show the status of a submodule，
- sync： synchronize submodule settings，
- summary:  show commit summary between given commit and working tree/index 
- foreach： evaluate shell command in each checked-out submodule 

## 新增 Submodule 子工程



```shell
git submodule add <gitUrl>
```



通过 add 一个仓库地址来添加 submodule，执行命令后会 clone 指定的仓库到当前目录下，并且会更新到 .gitmodules 文件中去



在 AndroidStudio 中还得修改一下 setting.gradle ，手动把工程加上去，不然不会编译。

## 移除 Submodule 子工程



```shell
git submodule deinit <projectName>
```



在 AndroidStudio 中还得修改一下 setting.gradle ，手动把工程移除，不然编译会再新建目录。



## 更新 submodule 子工程



更新工程下的 submodule，可以 cd 到具体的子仓库的目录下进行操作，像单独的一个仓库一样 add commit push。



不过不同的是，还需要到工程根目录下进行一次 add commit push 才会同步 submodule 的更新到主工程。



## 批量操作 submodule



一个主工程下的 submodule 可能包含多个子工程，如果需要批量操作的话，可以是用 foreach 命令。



例如把所有子工程都提交并 push 一下到远程 master，可以这么做：

```shell
git submodule foreach git add .
git submodule foreach git commit -m "update"
git submodule foreach git push origin master
```



还可以用来批量创建分支等。



## Clone 一个带 submodule 的仓库



如果一个仓库带有 submodule ,那么 clone 下来后还要执行一下 submodule 相关命令

```shell
git clone gitUrl
git submodule init
git submodule update
```

执行完这些命名后该工程才会正常。



