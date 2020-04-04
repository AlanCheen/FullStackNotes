# 第1章-Activity的生命周期和启动模式


## [Activity](http://developer.android.com/intl/zh-cn/reference/android/app/Activity.html)的生命周期

1. 正常状态
2. 异常状态(系统杀死/Configuration变化如屏幕旋转)

### 正常状态

1. onCreate   表示Activity正在创建,可以做一些初始化操作
2. onRestart  正在重新启动,onstop后回来会调用
3. onStart    正在被启动,后台,不可交互
4. onResume   **可见可交互前台**,非常重要,许许多多的重要的类在这里初始化
4. onPause    正在停止 **后台(不一定不可见)** 不可做太耗时的操作,**因为onPause之后新启动Activity的onResume才能被调用**
5. onStop    即将停止,**不可见** 可以做一些稍微重量级  的回收
6. onDestroy 即将销毁,可以做一些回收资源,关闭线程,移除Handler消息等操作


生命周期配对去记忆效果更佳:  
create -- destroy
start -- stop	 
resume -- pause  


注意点:  

A 启动 B
如果B是透明的Activity,那么A的onStop不会被调用  


### 异常状态

Activity的销毁与重建涉及到这两方法:  
1. `onSaveInstanceState(@Nullable Bundle state)`    系统会调用它来保存状态,以便之后恢复  
2. `onRestoreInstanceState(@NonNull Bundle state)`  为系统恢复所用


**当Activity将处于可能被销毁或要被销毁的状态,就会调用`onSaveInstanceState`**
而**`onRestoreInstanceState`则是重建的时候被调用**  


#### 调用时机

`onSaveInstanceState` 在`onStop`之前调用,而不一定在`onPause`之后  
`onRestoreInstanceState` 在`onStart`之后,`onResume`之前

所以,大致的完整的生命周期是这样的:  
onCreate  
onRestart  
onStart   
onRestoreInstanceState  
onResume  
onPause  
onSaveInstanceState  
onStop  
onDestroy  


PS:`onSaveInstanceState`如下情况会调用:  
1. 启动了新的Activity  
2. 按了Home键等等    

另外:可以在onCreate里判断bundle是否为null来判断是新建还是重新创建  



关于完整的生命周期,附上一张图,配有fragment的生命周期,可能你在很多地方都看到过,很多人盗用了它,但是并不给出处,这里提一下,它出自[android-lifecycle](https://github.com/xxv/android-lifecycle):    
![complete_android_fragment_lifecycle](http://ww1.sinaimg.cn/mw690/98900c07gw1f3qo4cy2f7j21bu2u2wu8.jpg)  

#### View 的恢复
A系统默认做了一定的恢复,如视图结构,LV的滑动的位置等等(View也有save,restore方法)  

	> PS 看到有的文章说解决Fragment重叠的问题,就是注释掉Activity的onSaveInstanceState方法,简直是误人子弟!!!有机会以后讲    

扯远了,保存和恢复View的层次结构,系统的工作流程是这样子的:  
Activity==>Window==>DecorView==>ContentView==>View  
一层一层**委托**保存恢复状态  

### Activity优先级

1. 前台Activity  正在交互的,onResume状态的Activity
2. 可见但非前台,弹了Dialog,依然可见但是不能交互
3. 后台Activity ,执行onStop之后

### Configuration 改变
Configuration改变的时候系统默认会重建Activity,如果我不想重建,那么可以选择配置`android:configChanges`属性,一般常用的就`orientation`,`screenSize`,`keyboardHidden`  

so,一般给Activity配上这个就行了:  
`android:configChanges="orientation|screenSize|keyboardHidden"`  

## 启动模式

### Standard   

标准模式,默认的启动模式,**每次启动都会新建一个Activity实例**    

需要注意的是当使用ApplicationContext去启动Standard模式的Activity的时候会报错,说需要添加NEW_TASK 的标记  

为什么呢?  

因为**Activity启动需要任务栈**,而用**Standard模式去启动Activity,默认会进入启动它的Activity所属的任务栈中,而非Activity类型的Context并没有所谓的任务栈**.  

AB--启动C->ABC

### SingleTop  

栈顶复用模式,如果新的Activity已位于栈顶,那么不会重新创建Activity,而是回调`onNewIntent`方法  

`onNewIntent-->onResume`

ABC--启动C--> ABC  

### SingleTask

栈内复用模式,只要占中存在都不会重新创建,并且也是回调`onNewIntent`  
另外需要注意的是,该模式拥有**clearTop** 的效果,会把位于它顶上的Activity全部出栈(PS:必须同一个栈)  

如: ABCDE--启动C(SingleTask)--> ABC  

### SingleInstance  

单实例模式,**栈内单例**,一个Activity实例**独占一个任务栈**,可以说整个手机都只有一个实例  


指定启动模式有两种方式:  
1. 清单里修改`android:launchMode`属性  
2. Intent.addFlags() 来指定    

#### Activity的Flags

常用的Flags:  
1. FLAG_ACTIVITY_NEW_TASK  指定启动模式为`singleTask`
2. FLAG_ACTIVITY_SINGLE_TOP    指定`singleTop`启动模式
3. FLAG_ACTIVITY_CLEAR_TOP  将在它之上的所有Activity移出栈,这个模式一般需要和`FLAG_ACTIVITY_NEW_TASK`一起出现  
4. FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS  具有这个标记的Activity不会出现在历史Activity列表中,相当于`android:exludeFromRecents="true"`  

## 任务栈

TaskAffinity(任务相关性),**标识了一个Activity的任务栈名称**,*默认为应用的包名*(万能的包名啊!)      

我们可以在清单文件里配置,也可以为每个Activity配置不同的值,但是需要注意的是它**不能跟包名相同,并且必须要包含`.`分隔符!**  

并且TaskAffinity属性主要和`SingleTask`或者`allowTaskReparenting`配对使用,在其他情况下没有意义.  

1. TaskAffinity和SingleTask配合: TaskAffinity的值为该模式的任务栈的名字
2. TaskAffinity和`allowTaskReparenting` 配合就比较复杂了:
当`allowTaskReparenting`为`true`时,A应用启动B应用的一个Activity C,然后按Home回到桌面,然后再单击B的桌面图标,这个时候不是启动B的主Activity,而是重新显示被应用A启动的Activity C(原本来说C是A启动的,那么C应该待在A的任务栈里),或者说C从A的任务栈转移到了B的任务栈中(*也许这就是re-parenting的含义吧*)  

补充:`allowTaskReparenting`需要和`FLAG_ACTIVITY_RESET_TASK_IF_NEEDED`标记合作才行,而从Home点击图标启动应用的`Intent`就带有该标记.  



## IntentFilter的匹配规则

启动Activity有两种方式:**显示调用**和**隐式调用**  

显示调用非常简单,明确指定被启动对象的组件信息即可.  

而隐式调用需要配合`IntentFilter`去匹配,**一个Activity可以有多个IntentFilter**,匹配到了**其中一个**就能启动,否则启动失败,`IntentFilter`的过滤信息有*action,category,data*    

当一个Intent同时匹配`IntentFilter`的action,category,data,才能启动一个Activity  

### action的匹配规则

action是个字符串,区分大小写,系统自带一些Action,也可以自定义  

规则是:**必须要匹配**,即Intent的action需要一模一样!**有多个action的时候,只需要匹配到一个即可**    

需要注意的是,**Intent必须包含action**,否则匹配失败  

### category的匹配规则  

category跟action一样,也是个字符串,系统也自带了一些,我们也可以自定义.  

它要求`Intent`中如果含有`category`,那么所有的`category`都必须和过滤规则的其中一个相同,即**被过滤规则所包含,是它的子集**  

**注意:与`action`不同的是,它可以不指定`category`**,这是因为`startActivity`和`startActivityForResult`会默认给Intent加上`android.intent.category.DEFAULT`这个`categrory`,所以如果你的Activity要能够接受隐式调用,就必须在清单文件中为这个Activity的`IntentFilter`中指定`android.intent.category.DEFAULT`这个`category`  


### data的匹配规则

#### data的组成

data由两部分组成,`mineType`和`URI`  

mineType指媒体类型,如`image/jpeg`和`video/*`  

URI比较复杂,它的格式:   
`<scheme>://<host>:<port>/[<path>||<pathPrefix>|<pathPattern>]`  

看例子就简单了,比如:`http://yifeiyuan.me:80/about`

- Scheme: **必须** URI的模式,比如http,ftp,如果URI中没有指定scheme,那么整个URI的其他参数无效  
- Host: **必须** URI的主机名,如果Host没有指定,那么URI无效
- Port: URI中的端口号,**非必须**,仅当URI中指定了scheme和host参数的时候port参数才有意义的.  
- path 表示完整的路径,pathPrefix表示路径前缀,pathPattern表示完整的路径,但是它可以包含通配符

#### 匹配

data也是完全匹配,可以在xml里给Activity配置:`<data android:mineType="image/*">``  

启动Activity的时候可以通过`intent.setDataAndType()`设置


    小Tip:隐式启动可能会遇到匹配不到Activity而导致Crash的情况,可以使用`PackageManager`或者`Intent`的`resolveActivity`的方法先判断是否有匹配到的Activity,防止Crash

