# Paging 

> 迁移语雀 2021-2-22


> The Paging Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources.



Paging 库，顾名思义，是用来专门处理`分页`的，可以用来处理上拉加载。



### 添加依赖

Android X :

```groovy
dependencies {
  def paging_version = "2.1.0"

  implementation "androidx.paging:paging-runtime:$paging_version" // For Kotlin use paging-runtime-ktx

  // alternatively - without Android dependencies for testing
  testImplementation "androidx.paging:paging-common:$paging_version" // For Kotlin use paging-common-ktx

  // optional - RxJava support
  implementation "androidx.paging:paging-rxjava2:$paging_version" // For Kotlin use paging-rxjava2-ktx
}
```

Pre-Android X ：

```groovy
dependencies {
    def paging_version = "1.0.1"

    implementation "android.arch.paging:runtime:$paging_version"

    // alternatively - without Android dependencies for testing
    testImplementation "android.arch.paging:common:$paging_version"

    // optional - RxJava support
    implementation "android.arch.paging:rxjava2:$paging_version"
}
```



### PagedList



得配合 LiveData Room 或者 RxJava2 。。



### PagedListAdapter



### 资料

https://developer.android.com/topic/libraries/architecture/paging

依赖：https://developer.android.com/jetpack/androidx/releases/paging#pre-androidx_dependencies