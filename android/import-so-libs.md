# Android 中引入 so 文件



Android Studio 引入 so 文件方法有二。



### 一 通过 jniLibs 引入



简单、方便，适合符合 Android Studio 工程目录的项目，比如用 Android Studio 新建的项目。



1. 在 `src/mian/`目录下新建`jniLibs`文件夹，跟`java`、`res`目录是同级的；
2. Copy `.so` 文件到`jniLibs`目录；
3. 编译即可。



举个🌰：

![image-20190702154102448](/Users/mingjue/self/FullStackNotes/android/assets/image-20190702154102448.png)

### 二 通过 libs 引入



相对复杂，适合非 AndroidStudio 工程项目，比如 Eclipse 结构的项目、自定义了 `sourceSets` 的项目。



1. 在 module 下新建跟`src`目录*同级*的`libs`目录；

2. Copy `.so` 文件到`libs`目录；

3. 配置 `sourceSets` :

   1. ```groovy
      android {
      	
      	sourceSets{
      		main {
      		    ...
      			jniLibs.srcDirs = ['libs']
      		}
      	}
      }
      ```

4. 编译。



举个🌰：

![image-20190702170503415](/Users/mingjue/self/FullStackNotes/android/assets/image-20190702170503415.png)







