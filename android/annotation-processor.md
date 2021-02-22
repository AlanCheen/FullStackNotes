# Android Processor

> 迁移语雀 2021-2-22



### 经验

### 配置参数

我们可以通过配置 options 来给 processor 传递参数，达到配置 Processor 的目的。

在引用了 processor 的项目里配置 javaCompileOptions DSL。

例如：
```grovvy
android {
    compileSdkVersion 28
    defaultConfig {
        ...
        javaCompileOptions{
            annotationProcessorOptions{
                //填写键值对，多个用逗号隔开
                arguments = [autoRegister:'true']
            }
        }
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```

在 init 方法里接受参数：
```java
Map<String, String> options = processingEnv.getOptions();
if (options.containsKey("autoRegister")) {
    autoRegisterFactories = Boolean.parseBoolean(options.get("autoRegister"));
}
```

