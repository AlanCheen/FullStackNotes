# SharedPreferences

基于 Android API 28 

共享首选项





## 概述



SharedPreferences 是一个接口，用于访问和修改首选项数据，通过 Editor 接口的 putxxx 和 getxxx 来操作，最终调用 commit 或者 apply 来提交操作。



## 基本使用



通过 context.getSharedPreferences(String name,int mode) 可以获取一个 SharedPreferences 实例。







## 资料

