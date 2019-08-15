# SurfaceView

[TOC]

### 概览



SurfaceView 持有一个 `Surface`，可以提供在视图结构里嵌入一个 Surface 进行绘制。

SurfaceView 负责把 Surface 放在正确的位置。

SurfaceView 会在它所在的 Window 挖个坑从而使得它的 Surface 得以展示。

SurfaceView 会有性能问题。

Surface 按 Z 轴排序。



SurfaceView 跟其他的 View 不一样，不受 View 的属性控制，所以要搞什么位移啦 动画啦 都不行喔。

SurfaceView 结合了 Surface 和 View。SurfaceView 的 View 组件由 SurfaceFlinger（而不是应用）合成，从而可以通过单独的线程/进程渲染，并与应用界面渲染隔离。



### SurfaceView 我踩过的坑



#### SurfaceView 的存在影响其他 View 动画范围



具体表现：一个 View 做缩放动画，发现动画过程中被不明黑色区块遮挡了。



本来做一个动画，demo 里都是正常的，然后放到项目里发现动画异常，想来跟 SurfaceView 有关。



然后发现确实如此，移除 SurfaceView 后动画就没问题了。



尝试搜索找寻问题根源，但是没有线索，然后发现动画被限制在了 View 原来的区域内，就想着试一下扩大原来的区域，于是*放了个空的 View，让它的区域足够覆盖动画的效果范围*，然后就好了。



怀疑是绘制的 Surface 跟动画有什么冲突，具体原因尚不明确。



#### SurfaceView 遮挡其他 View

`setZOrderOnTop（boolean）` ，true 的时候 SurfaceView 会在最顶部，遮挡住其他的 View。



#### SurfaceView 造成的黑屏

在某些场景下，比如拍摄照片、切换摄像头、前后台切换 可能会出现一个现象：`SurfaceView 黑屏`。

我遇到的是部分华为手机在切换摄像头后`变黑屏`或者是`页面卡主`。

调试发现大概是 surface 为 null，并且 有时候 debug 发现断开断点后又是 OK 的，猜测可能是有什么异步的操作导致时序混乱。



解法可能有二：

1. 可能只需要在某些操作后面、前面，加个 sleep 的操作即可；
2. 避免让 SurfaceView 进行不必要的重新布局；



#### 播放视频旋转问题

SurfaceView 播放视频的时候不会自动旋转，可以选择用`TextureView`。



### 推荐

https://stackoverflow.com/questions/4576909/understanding-canvas-and-surface-concepts

https://source.android.com/devices/graphics/arch-sv-glsv