# 自定义 Gradle 插件





Gradle 插件可以包含可重用的构建逻辑，在不同的工程中使用。



Gradle 插件可以使用任何最终编译成 JVM 字节码的语言开发，例如 groovy,java,kotlin ，通常 java 跟 kotlin 这些静态类型语言性能会更好。



## 打包插件

可以有几个方式存放插件的代码：



1. 构建脚本（build script），可以把插件的源码直接包含到构建脚本里去，可以直接编译并被添加到 classpath
2. `buildSrc` 工程，可以把源代码放在插件的 *rootProjectDir*/buildSrc/src/main/groovy` directory (or `*rootProjectDir*/buildSrc/src/main/java` or `*rootProjectDir*/buildSrc/src/main/kotlin 目录，Gradle 会负责编译插件
3. 独立工程（standalone project），可以创建一个独立的工程，生产和发布一个 JAR，这样可以重用。一个 JAR 可能包含一些插件或任务；



## 编写一个简单的插件



创建一个 Gradle 插件，需要写个类实现  Plugin<T> 接口。当这个插件被应用到一个工程，Gradle 会创建一个那个插件的实例，并调用 Plugin.apply() 方法。



编写一个添加`hello`task 的插件，新建个工程，在 app/build.gradle 里添加如下代码：

```groovy
class GreetingPlugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
        target.task("hello"){
            doLast {
                println 'Hello from the GreetingPlugin'
            }
        }
    }
}

apply plugin: GreetingPlugin
```



然后执行`./gradlew -q hello` 就可以看到输出 Hello from the GreetingPlugin。



值得注意的是，会为每个应用插件的项目创建一个插件的新实例；另外 Plugin 是泛型类型，可以接收如下参数；

1. Project，
2. Settings，可以应用在设置脚本中；
3. Gradle，可以用在初始化脚本中；

## 使插件可以配置

插件可以提供配置功能。 Project 类有个 ExtensionContainer 对象，它包含了插件的所有的 setting 和 properties。通过添加一个 extension 对象可以实现配置功能，一个 extension 对象只需是个简单的 Java Bean，它的属性就代表着配置。



修改之前的工程，支持通过插件配置来修改 message，代码如下：

```groovy
class GreetingPluginExtension {
    String message = 'Hello from GreetingPlugin extension'
}

class GreetingPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
			 // 添加一个扩展配置
       def extension = target.extensions.create('greeting', GreetingPluginExtension)

        target.task("hello") {
            doLast {
                println extension.message
            }
        }
    }
}

//通过 dsl 就可以配置
greeting {
    message = "Hi greeting from ext dsl"
}
```



`target.extensions.create(name, classType)` 这个方法就可以添加配置，name 表示配置的名字，对应在 gradle 里的 DSL，classType 就是配置对应的类。这里的对应关系不能乱，所以注意别瞎改。





## bulidSrc 



直接在项目的根目录下新建一个目录（跟 app 目录同级），命名为 buildSrc ，不能改名字。



然后新建必须的目录以及文件，一个 buildSrc 项目文件树大致如此：



```

├── buildSrc.iml
└── src
    ├── build.gradle
    └── main
        ├── groovy
        │   └── me.yifeiyuan.buildsrctest
        │       └── BuildSrcPlugin.groovy
        └── resources
            └── META-INF
                └── gradle-plugins
                    └── me.yifeiyuan.buildsrctest.properties

```



buildSrc 里的插件会被自动添加到 classPath ， 所以项目里的工程可以直接使用它。

### 使用 buildSrc 里的插件



#### 直接引用类名

buildSrc 里，实现了 Plugin 接口的类可以被其他工程直接引用使用。



例如 有个叫 FooPlugin 的类实现了 Plugin 接口，就可以这样用：

```
class FooPlugin implements Plugin<Project>{...}

//App/build.gradle
apply plugin: FooPlugin
```





#### 定义 pluginid

由于在 meta-inf 里定义了插件 id 为可以 `me.yifeiyuan.buildsrctest` ，所以通过 `apply plugin: 'me.yifeiyuan.buildsrctest'` 就可以依赖该插件了。

## 独立工程

独立的工程可以让插件单独发布，并且可以复用。





为了让 Gradle 找到插件，需要在 JAR 的 META-INF/gradle-plugins 目录里提供与插件 ID 相匹配的属性文件。

例如：

**src/main/resources/META-INF/gradle-plugins/org.samples.greeting.properties**

```implementation-class = org.gradle.GreetingPlugin```



需要注意：**属性文件的名字**跟**插件 ID** 是匹配的，并且属性文件里需要指定 `implementation-class`，值为插件的实现类。

使用插件的时候，会是这样：`apply plugin: 'org.samples.greeting'`，跟属性文件名字是一样的。



在 AS 中没有 gradle plugin 类型的项目模板，所以只能借用其他项目模板。



独立工程下的插件项目大概如此：



```
├── build.gradle
├── src
│   └── main
│       ├── groovy
│       │   └── me
│       │       └── yifeiyuan
│       │           └── standalone
│       │               └── plugin
│       │                   └── MyPlugin.groovy
│       └── resources
│           └── META-INF
│               └── gradle-plugins
│                   └── me.yifeiyuan.myplugin.properties
└── standalone-plugin.iml

```



## 



## 笔记



buildSrc 里的插件会被自动添加到 classpath，可以直接使用。

独立工程里的插件需要被发布，然后手动添加到 classpath 才能使用。

## 资料

[Developing Custom Gradle Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)

[Use buildSrc to abstract imperative logic](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)

[项目 HeadFirstGradlePlugin](https://github.com/HeadFirstAndroid/HeadFirstGradlePlugin)

