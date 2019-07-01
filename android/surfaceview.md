# SurfaceView









### SurfaceView 的坑





#### SurfaceView 的存在影响其他 View 动画范围



具体表现：一个 View 做缩放动画，发现动画过程中被不明黑色区块遮挡了。



本来做一个动画，demo 里都是正常的，然后放到项目里发现动画异常，想来跟 SurfaceView 有关。



然后发现确实如此，移除 SurfaceView 后动画就没问题了。





尝试搜索找寻问题根源，但是没有线索，然后发现动画被限制在了 View 原来的区域内，就想着试一下扩大原来的区域，于是放了个空的 View，让它的区域足够覆盖动画的效果范围，然后就好了。



怀疑是绘制的 Surface 跟动画有什么冲突，具体原因尚不明确。



### SurfaceView 遮挡其他 View



`setZOrderOnTop（boolean）` ，true 的时候 SurfaceView 会在最顶部，遮挡住其他的 View。