# Graphics Concepts



记录一些关于图形架构的概念性东西。



- [x] **`Surface`**，表示通常（但并非总是！）由 SurfaceFlinger 消耗的缓冲区队列的生产方
- [ ] `SurfaceHolder`
- [ ] `EGLSurface`
- [x] **`SurfaceView`**，SurfaceView 结合了 Surface 和 View。SurfaceView 的 View 组件由 SurfaceFlinger（而不是应用）合成，从而可以通过单独的线程/进程渲染，并与应用界面渲染隔离。
- [ ] `GLSurfaceView`，GLSurfaceView 提供帮助程序类来管理 EGL 上下文、线程间通信以及与“Activity 生命周期”的交互（但使用 GLES 时并不需要 GLSurfaceView）
- [ ] `SurfaceTexture`，SurfaceTexture 将 Surface 和 GLES 纹理相结合来创建 BufferQueue，而您的应用是 BufferQueue 的消费者。当生产者将新的缓冲区排入队列时，它会通知您的应用。您的应用会依次释放先前占有的缓冲区，从队列中获取新缓冲区并执行 EGL 调用，从而使 GLES 可将此缓冲区作为外部纹理使用。Android 7.0 增加了对安全纹理视频播放的支持，以便用户能够对受保护的视频内容进行 GPU 后处理。
- [ ] `TextureView`，TextureView 结合了 View 和 SurfaceTexture。TextureView 对 SurfaceTexture 进行包装，并负责响应回调以及获取新的缓冲区。在绘图时，TextureView 使用最近收到的缓冲区的内容作为其数据源，根据 View 状态指示，在它应该渲染的任何位置和以它应该采用的任何渲染方式进行渲染。View 合成始终通过 GLES 来执行，这意味着内容更新可能会导致其他 View 元素重绘。
- [x] **`BufferQueue`**，BufferQueue 类`是 Android 中所有图形处理操作的核心`。它的作用很简单：`将生成图形数据缓冲区的一方（生产方）连接到接受数据以进行显示或进一步处理的一方（消耗方`）。几乎所有在系统中移动图形数据缓冲区的内容都依赖于 BufferQueue。
- [x] **`SurfaceFlinger`**，SurfaceFlinger 接受来自多个源的数据缓冲区，然后将它们进行合成并发送到显示屏。
- [ ] **`Vulkan`**，Vulkan 是一种用于高性能 3D 图形的低开销、跨平台 API。与 OpenGL ES 一样，Vulkan 提供用于在应用中创建高质量实时图形的工具。
- [ ] `VSYNC`，



### Android 图形组件 概览

应用开发者可通过两种方式将图像绘制到屏幕上：使用 `Canvas` 或 `OpenGL`。



无论开发者使用什么渲染 API，`一切内容都会渲染到“Surface”`。Surface 表示缓冲队列中的生产方，而缓冲队列通常会被 SurfaceFlinger 消耗。在 Android 平台上创建的每个窗口都由 Surface 提供支持。所有被渲染的可见 Surface 都被 SurfaceFlinger 合成到显示部分。

![Surface 如何被渲染](http://ww4.sinaimg.cn/large/006tNc79ly1g60ev8bxvpj30fs0e2t9i.jpg)



**图像流生产方**：可以是生成图形缓冲区以供消耗的任何内容。例如 OpenGL ES、Canvas 2D 和 mediaserver 视频解码器。

**图像流消耗方**：图像流的最常见消耗方是 `SurfaceFlinger`，该系统服务会消耗当前可见的 Surface，并使用窗口管理器中提供的信息将它们合成到显示部分。SurfaceFlinger 是可以修改所显示部分内容的唯一服务。`SurfaceFlinger 使用 OpenGL 和 Hardware Composer 来合成一组 Surface`。

其他 OpenGL ES 应用也可以消耗图像流，例如相机应用会消耗相机预览图像流。非 GL 应用也可以是消耗方，例如 ImageReader 类。

**窗口管理器**：控制窗口的 Android 系统服务，它是视图容器。窗口总是由 Surface 提供支持。该服务会监督生命周期、输入和聚焦事件、屏幕方向、转换、动画、位置、变形、Z-Order 以及窗口的其他许多方面。`窗口管理器会将所有窗口元数据发送到 SurfaceFlinge`r，以便 SurfaceFlinger 可以使用该数据在显示部分合成 Surface。

**硬件混合渲染器**：显示子系统的硬件抽象实现。SurfaceFlinger 可以将某些合成工作委托给 Hardware Composer，以分担 OpenGL 和 GPU 上的工作量。`SurfaceFlinger 只是充当另一个 OpenGL ES 客户端`。因此，在 SurfaceFlinger 将一个或两个缓冲区合成到第三个缓冲区中的过程中，它会使用 OpenGL ES。这样使合成的功耗比通过 GPU 执行所有计算更低。[Hardware Composer HAL](https://source.android.com/devices/graphics/architecture.html#hwcomposer) 则进行另一半的工作，并且是所有 Android 图形渲染的核心。Hardware Composer 必须支持事件，其中之一是 VSYNC（另一个是支持即插即用 HDMI 的热插拔）。

**Gralloc**：需要使用图形内存分配器 (Gralloc) 来分配图像生产方请求的内存。



**图形管道的数据流**：

![å¾å½¢æ°æ®æµ](http://ww2.sinaimg.cn/large/006tNc79ly1g60f2b81hfj30ex07awex.jpg)

左侧的对象是生成图形缓冲区的渲染器，如主屏幕、状态栏和系统界面。SurfaceFlinger 是合成器，而硬件混合渲染器是制作器。



#### BufferQueue

BufferQueues 是 **Android 图形组件之间的粘合剂**。**它们是一对队列，可以调解缓冲区从生产方到消耗方的固定周期。一旦生产方移交其缓冲区，SurfaceFlinger 便会负责将所有内容合成到显示部分**。

有关 BufferQueue 通信过程，请参见下图。

![BufferQueue 通信过程](http://ww1.sinaimg.cn/large/006tNc79ly1g60f482pgej30dd0453yk.jpg)

BufferQueue 包含将图像流生产方与图像流消耗方结合在一起的逻辑。图像生产方的一些示例包括由相机 HAL 或 OpenGL ES 游戏生成的相机预览。图像消耗方的一些示例包括 SurfaceFlinger 或显示 OpenGL ES 流的另一个应用，如显示相机取景器的相机应用。

`BufferQueue 是将缓冲区池与队列相结合的数据结构`，它使用 Binder IPC 在进程之间传递缓冲区。生产方接口，或者您传递给想要生成图形缓冲区的某个人的内容，即是 IGraphicBufferProducer（[SurfaceTexture](http://developer.android.com/reference/android/graphics/SurfaceTexture.html) 的一部分）。BufferQueue 通常用于渲染到 Surface，并且与 GL 消耗方及其他任务一起消耗内容。BufferQueue 可以在三种不同的模式下运行：

1. **类同步模式** - 默认情况下，BufferQueue 在类同步模式下运行，在该模式下，从生产方进入的每个缓冲区都在消耗方那退出。在此模式下不会舍弃任何缓冲区。如果生产方速度太快，创建缓冲区的速度比消耗缓冲区的速度更快，它将阻塞并等待可用的缓冲区。

2. **非阻塞模式** - BufferQueue 还可以在非阻塞模式下运行，在此类情况下，它会生成错误，而不是等待缓冲区。在此模式下也不会舍弃缓冲区。这有助于避免可能不了解图形框架的复杂依赖项的应用软件出现潜在死锁现象。

3. **舍弃模式** - 最后，BufferQueue 可以配置为丢弃旧缓冲区，而不是生成错误或进行等待。例如，如果对纹理视图执行 GL 渲染并尽快绘制，则必须丢弃缓冲区。

为了执行这项工作的大部分环节，**SurfaceFlinger 就像另一个 OpenGL ES 客户端一样工作**。例如，当 SurfaceFlinger 正在积极地将一个缓冲区或两个缓冲区合成到第三个缓冲区中时，它使用的是 OpenGL ES。

Hardware Composer HAL 执行另一半工作。该 HAL 充当所有 Android 图形渲染的中心点。



### Surface？

它的描述简单如下：“在由屏幕合成器管理的原始缓冲区上进行处理”。这句描述在最初编写时是准确的，但在现代系统上却远远不足。



`Surface 表示通常（但并非总是！）由 SurfaceFlinger 消耗的缓冲区队列的生产方`。当您渲染到 Surface 上时，产生的结果将进入相关缓冲区，该缓冲区被传递给消耗方。Surface 不仅仅是您可以随意擦写的原始内存数据块。

用于显示 Surface 的 BufferQueue 通常配置为三重缓冲；但按需分配缓冲区。因此，如果生产方足够缓慢地生成缓冲区 - 也许是以 30fps 的速度在 60fps 的显示屏上播放动画 - 队列中可能只有两个分配的缓冲区。这有助于最小化内存消耗。您可以看到与 `adb shell dumpsys SurfaceFlinger` 输出中每个层级相关的缓冲区的摘要。



`lockCanvas()` 和`unlockCanvasAndPost()`。



Surface 是一个对象用来`保存那些将会被合成到屏幕的像素`。我们在屏幕上看到的每一个 Window（对话框、全屏 Activity、状态栏）都有自己的 Surface。Surface Flinger 会按照 Z 轴排序绘制这些 Surface。Surface 具有多个缓冲区（一般来说是两个）用来进行双缓冲（double-buffered）。所以 App 可以绘制下一个 UI 状态， Surface Flinger 可以使用最后一个 buffer 来合成屏幕，而不需要等待应用完成绘制。



### Window？

Window 基本上就跟桌面的窗口一样，每个 Window 都有一个 Surface，用来绘制它的内容。应用通过 跟 Window  Manager 打交道来创建 Window，而 Window Manager `为每一个 Window 创建一个 Surface`（）。应用可以绘制任何想要的东西到 Surface 上，对于 WIndowManager 来说只是个不透明的矩形。



### View？

View 是 Window 的可交互的 UI 元素。每个 Window 都有跟它自己绑定的一个视图结构（view hierarchy），它提供了所有 Window 的行为。`View 跟 它所在的 Window 内的所有的 View 共享一个 Surface`（SurfaceView 例外）。

每当 Window 需要重绘时（例如视图本身无效了），这都在 Window 的 Surface 内完成。

这`整个重绘流程`大概这样：

1. 锁住 Surface，并返回一个能够用于绘制的 Canvas；
2. 沿着视图结构自顶向下遍历绘制（draw traversal），让结构里的每个 View 都绘制自己的那部分（onDraw）；
3. 解锁 Surface，并把刚才绘制的缓冲（drawn  buffer）提交；
4. Surface Flinger 把缓冲合成到屏幕。



`Surface 生产绘制缓冲，Surface Flinger 用来消费，合成到屏幕展示`。

### SurfaceView？

Android 应用框架界面是以使用 View 开头的对象层次结构为基础。所有界面元素都会经过一个复杂的测量和布局过程，该过程会将这些元素融入到矩形区域中，并且`所有可见 View 对象都会渲染到一个由 SurfaceFlinger 创建的 Surface（在应用置于前台时，由 WindowManager 进行设置）`。应用的界面线程会执行布局并渲染到单个缓冲区（不考虑 Layout 和 View 的数量以及 View 是否已经过硬件加速）。

SurfaceView 采用与其他视图相同的参数，因此您可以为 SurfaceView 设置位置和大小，并在其周围填充其他元素。但是，当需要渲染时，`内容会变得完全透明`；`SurfaceView 的 View 部分只是一个透明的占位符`。

当 SurfaceView 的 View 组件即将变得可见时，框架会`要求 WindowManager 命令 SurfaceFlinger 创建一个新的 Surface`。（这个过程并非同步发生，因此您应该提供回调，以便在 Surface 创建完毕后收到通知。）`默认情况下，新的 Surface 将放置在应用界面 Surface 的后面`，但可以替换默认的 Z 排序，将 Surface 放在顶层。

`渲染到该 Surface 上的内容将会由 SurfaceFlinger（而非应用）进行合成`。这是 SurfaceView 的真正强大之处：您获得的 Surface 可以由单独的线程或单独的进程进行渲染，并与应用界面执行的任何渲染隔离开，而缓冲区可直接转至 SurfaceFlinger。您不能完全忽略界面线程，因为您仍然需要与 Activity 生命周期相协调，并且如果 View 的大小或位置发生变化，您可能需要调整某些内容，但是您可以拥有整个 Surface。与应用界面和其他图层的混合由 Hardware Composer 处理。

新的 Surface 是 BufferQueue 的生产者端，其消费者是 SurfaceFlinger 层。您可以使用任意提供 BufferQueue 的机制（例如，提供 Surface 的 Canvas 函数）来更新 Surface，附加 EGLSurface 并使用 GLES 进行绘制，或者配置 MediaCodec 视频解码器以便于写入。



SurfaceView 是 View 的一个特殊的实现，它自己`持有一个它独有的 Surface 以供应用程序直接绘制`。它并不在普通的视图结构中，不像其他的 View 那要跟 Window 内的 View 共享同一个 Surface。

SurfaceView 的 `View 组件由 SurfaceFlinger（而不是应用）合成`，从而`可以通过单独的线程/进程渲染`，并与应用界面渲染隔离。

SurfaceView 的原理是：让 WindowManager 创建一个新的 Window，告诉它 Z-order 在 SurfaceView 的 Window 的前面还是后面，并且将该 Window 放置到 SurfaceView 所在的那个 Window 的位置。如果这个 Surface 在主窗口的后面，SurfaceView 也会在主窗口以透明来填充，以便可以看到 SurfaceView。



SurfaceView 很重，有性能问题，建议最多加 1 个。

一般说 Activity 的 Window 是主窗口（main window）。

### Canvas？





### Bitmap？

Bitmap 只是某些像素数据的接口，像素的集合。Bitmap 只是封装了下而已。

当你直接创建 Bitmap 的时候，像素可能由 Bitmap 本身分配。或者它可能指向不属于它的像素，例如 hook 属于 Surface 的 Canvas（此时 Bitmap 属于 Surface 的 draw buffer）



### SurfaceFlinger？



SurfaceFlinger 接受来自多个来源的数据缓冲区，对它们进行合成，然后发送到显示设备。

当应用进入前台时，WindowManager 服务会向 SurfaceFlinger 请求一个绘图 Surface。`SurfaceFlinger 会创建一个其主要组件为 BufferQueue 的层，而 SurfaceFlinger 是其消耗方`。生产方端的 Binder 对象通过 WindowManager 传递到应用，然后应用可以开始直接将帧发送到 SurfaceFlinger。



大多数应用在屏幕上一次显示三个层：`屏幕顶部的状态栏`、`底部或侧面的导航栏`以及`应用界面`。有些应用会拥有更多或更少的层（例如，默认主屏幕应用有一个单独的壁纸层，而全屏游戏可能会隐藏状态栏）。`每个层都可以单独更新`。状态栏和导航栏由系统进程渲染，而`应用层由应用渲染`，两者之间不进行协调。

设备显示会按一定速率刷新，在手机和平板电脑上通常为 60 fps。如果显示内容在刷新期间更新，则会出现撕裂现象；因此，请务必只在周期之间更新内容。在可以安全更新内容时，系统便会收到来自显示设备的信号。由于历史原因，我们将该信号称为 `VSYNC 信号`。

刷新率可能会随时间而变化，例如，一些移动设备的帧率范围在 58 fps 到 62 fps 之间，具体要视当前条件而定。对于连接了 HDMI 的电视，刷新率在理论上可以下降到 24 Hz 或 48 Hz，以便与视频相匹配。由于每个刷新周期只能更新屏幕一次，因此以 200 fps 的帧率为显示设备提交缓冲区就是一种资源浪费，因为大多数帧会被舍弃掉。`SurfaceFlinger 不会在应用每次提交缓冲区时都执行操作，而是在显示设备准备好接收新的缓冲区时才会唤醒`。

`当 VSYNC 信号到达时，SurfaceFlinger 会遍历它的层列表，以寻找新的缓冲区。如果找到新的缓冲区，它会获取该缓冲区；否则，它会继续使用以前获取的缓冲区。SurfaceFlinger 必须始终显示内容，因此它会保留一个缓冲区。如果在某个层上没有提交缓冲区，则该层会被忽略。`

SurfaceFlinger 在收集可见层的所有缓冲区之后，便会询问 Hardware Composer 应如何进行合成。



### VSYNC

VSYNC 可将某些事件同步到显示设备的刷新周期。`应用总是在 VSYNC 边界上开始绘制，而 SurfaceFlinger 总是在 VSYNC 边界上进行合成。`这样可以消除卡顿，并提升图形的视觉表现。

VSYNC 偏移：在 VSYNC 事件中，显示设备开始显示帧 N，而 SurfaceFlinger 开始为帧 N+1 合成窗口。应用处理等待的输入并生成帧 N+2。通过 VSYNC 偏移，SurfaceFlinger 接收缓冲区并合成帧，而应用处理输入内容并渲染帧，所有这些操作都在一个时间段内完成。



### 自己对屏幕绘制的理解与总结



对于整个手机屏幕而言，它分为多个层(layer)，一般为状态栏层、导航栏层和应用界面层，这些层都是由 SurfaceFlinger 创建，并且每个层都有它的 BufferQueue，用来接收缓冲数据。

每个层都有它的 Window，每个 Window 都有自己的 Surface（这些 Surface 是在应用在前台时 WinderManager 向 SurfaceFlinger 申请而创建，WinderManager 负责设置），同时有自己的视图结构（各种 View），然后绘制的时候 Window 向 Surface 索要一个 Canvas（`lockCanvas()`），然后自顶向下遍历传递这个 Canvas 到 View 的 onDraw() 方法，每个 View 都绘制自己的部分，当每个 View 都绘制完成，生成一个视图缓冲交给 Surface，然后 Surface 把这个交给 BufferQueue（`unlockCanvasAndPost()`），然后 SurfaceFlinger 从这个 BufferQueue 获取缓冲用来合成（通过 Hardware Compositor，HWC），再交给屏幕渲染。

当收到 VSYNC 信号时，SurfaceFlinger 会遍历它的层列表，查找新的缓冲区，如果没有则使用旧的缓冲区进行绘制，因为它必须显示内容。

注意：Surface 是由 SurfaceFlinger 创建的而不是 WindowManager！



### 资料

图形架构：https://source.android.com/devices/graphics/architecture.html

https://stackoverflow.com/questions/4576909/understanding-canvas-and-surface-concepts