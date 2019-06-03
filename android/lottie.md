# 【源码分析】Lottie 实现炫酷动画背后的原理

![mat-reding-1525395-unsplash.jpg](https://cdn.nlark.com/yuque/0/2019/jpeg/138547/1555938410737-fe6c0155-e2c9-4a5a-93bf-7b260d05e800.jpeg#align=left&display=inline&height=1119&name=mat-reding-1525395-unsplash.jpg&originHeight=4890&originWidth=3260&size=559591&status=done&width=746)
<a name="2a1d9fae"></a>
## 0. 前言

自我在内网发布了一篇关于 Lottie 的原理分析的文章之后，就不断有同事来找我询问关于 Lottie 的各种东西，最近又有同事来问，就想着可能对大家也会有所帮助，就稍作处理后分享出来。

需要注意的是，这文章写于两年前，基本版本 2.0.0-beta3，虽然我看过最新版本，主要的类没有什么差别，不过可能还是会存在一些差异。

可以感受一下我两年前的实力。:-D

<a name="87409575"></a>
## 1. Lottie 是什么？

> Render After Effects animations natively on Android and iOS


Lottie 是 airbnb 发布的库，它可以将 AE 制作的动画 在 Android&iOS上以 native 代码渲染出来，目前还支持了 RN 平台。

来看几个官方给出的动画效果案例：

![](https://cdn.nlark.com/yuque/0/2019/gif/138547/1553765030763-eea6acb9-c1e8-44cb-8709-fb3e30010caf.gif#align=left&display=inline&height=373&originHeight=400&originWidth=800&size=0&status=done&width=746)

![](https://cdn.nlark.com/yuque/0/2019/gif/138547/1553765031719-0f6fe612-c467-4fe0-9b36-61a6077817a3.gif#align=left&display=inline&height=373&originHeight=400&originWidth=800&size=0&status=done&width=746)

有没有很炫酷？

就拿第一个动画 Jump-through 举例，如果让你来实现它，你能在多少时间内完成？三天？一个礼拜？ google 的 Nick Butcher 刚好有一篇文章写 Jump-through 的动画实现，讲述了整个实现过程，从文章里可以看出实现这个动画并不容易，有兴趣的可以看看 [Animation: Jump-through](https://medium.com/google-developers/animation-jump-through-861f4f5b3de4)。

但是现在有了 Lottie，只要设计师用 AE 设计动画，利用 [bodymovin](https://github.com/bodymovin/bodymovin) 导出 ，导入到 assets, 再写下面那么点代码就可以实现了！

```java
LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
animationView.setAnimation("PinJump.json");
animationView.loop(true);
animationView.playAnimation();
```

不用写自定义 View！不用画 Path！不用去计算这个点那个点！

是不是超级方便？！！！

这么方便的背后，原理是什么呢？

<a name="489878a0"></a>
## 2. TL;DR

bodymovin 将 AE 动画导出为 ,该  描述了该动画，而 lottie-android 的原理就是将  描述的动画用 native code 翻译出来， 其核心原理是  **canvas 绘制**。对，lottie 的动画是靠纯 canvas 画出来的！！！动起来则是靠的属性动画。(`ValueAnimator.ofFloat(0f, 1f);` )

说具体点就是 lottie 随属性动画修改 progress，每一个 Layer 根据当前的 progress 绘制所对应的帧内容，progress 值变为1，动画结束。（有点类似于帧动画）

当然说说简单，lottie其实做了非常多的工作，后续将详细解析 [lottie-android](https://github.com/airbnb/lottie-android) 的实现原理。

<a name="efc22b8e"></a>
## 3. Lottie 关键类介绍

Lottie 提供了一个 LottieAnimationView 给用户使用，而实际 Lottie 的核心是 LottieDrawable，它承载了所有的绘制工作，LottieAnimationView则是对LottieDrawable 的封装，再附加了一些例如 解析  的功能。

- **LottieComposition** 是  对应的 Model，承载  的所有信息。
- **CompositionLayer** 是 layer 的集合。
- **ImageAssetBitmapManager** 负责管理动画所需的图片资源。

它们的关系：

![](https://cdn.nlark.com/yuque/0/2019/jpg/138547/1553765029902-cc2d01de-1175-4fc9-b36a-d5b9cff0b6a1.jpg#align=left&display=inline&height=722&originHeight=852&originWidth=880&size=0&status=done&width=746)

<a name="4879d65c"></a>
## 4. 文件的属性含义

[bodymovin](https://github.com/bodymovin/bodymovin)  导出的  包含了动画的一切信息, 动画的关键帧信息，动画该怎么做，做什么都包含在 里，Lottie 里所有的 Model 的数据都来自于这个 ( 该 对应的 Model 是LottieComposition)，所以要理解 Lottie 的原理，理解  的属性是第一步。

属性非常多，而且不同的动画的  也有很大的差别，所以这里只讲解部分重要的属性。

<a name="424b1c61"></a>
### 4.1 文件最外部的结构

的最外层长这样：

```
{
  "v": "4.5.9",
  "fr": 15,
  "ip": 0,
  "op": 75,
  "w": 500,
  "h": 500,
  "ddd": 0,
  "assets":[]
  "layers":[]
 }
```

属性的含义：

| 属性 | 含义 |
| --- | --- |
| v | bodymovin的版本 |
| fr | 帧率 |
| ip | 起始关键帧 |
| op | 结束关键帧 |
| w | 动画宽度 |
| h | 动画高度 |
| assets | 动画图片资源信息 |
| layers | 动画图层信息 |


从这里可以获取 设计的动画的宽高，帧相关的信息，动画所需要的图片资源的信息以及图层信息。

<a name="854203e2"></a>
#### a) assets

图片资源信息, 相关类 LottieImageAsset、 ImageAssetBitmapManager。

```
"assets": [
    {
      "id": "image_0",
      "w": 500,
      "h": 500,
      "u": "images/",
      "p": "voice_thinking_image_0.png"
    }
  ]
```

属性的含义：

| 属性 | 含义 |
| --- | --- |
| id | 图片 id |
| w | 图片宽度 |
| h | 图片高度 |
| p | 图片名称 |


<a name="beb42690"></a>
#### b) layers

图层信息，相关类：Layer、BaseLayer以及 BaseLayer 的实现类。

```
{
    "ddd": 0,
    "ind": 0,
    "ty": 2,
    "nm": "btnSlice.png",
    "cl": "png",
    "refId": "image_0",
    "ks": {....},
    "ao": 0,
    "ip": 0,
    "op": 90.0000036657751,
    "st": 0,
    "bm": 0,
    "sr": 1
}
```

属性的含义：

| 属性 | 含义 |
| --- | --- |
| nm | layerName 图层信息 |
| refId | 引用的资源 id,如果是 ImageLayer 那么就是图片的id |
| ty | layertype 图层类型 |
| ip | inFrame 该图层起始关键帧 |
| op | outFrame 该图层结束关键帧 |
| st | startFrame 开始 |
| ind | layer id  图层 id |


Layer 可以理解为图层，跟 PS 等工具的概念相同，每个 Layer 负责绘制自己的内容。

在 Lottie 里拥有不同的 Layer，目前有 PreComp,Solid,Image,Null,Shape,Text ，各个 Layer 拥有的属性各不相同，这里只指出共有的属性。

下图为 Layer 相关类图：

![](https://cdn.nlark.com/yuque/0/2019/jpg/138547/1553765029894-1ec26008-6859-4f9f-b7f8-fa00d790a39c.jpg#align=left&display=inline&height=790&originHeight=890&originWidth=840&size=0&status=done&width=746)

<a name="4afd9264"></a>
## 5. Lottie 的适配原理

在开始使用 Lottie 的时候，我们团队设计动画走的跟设计图片一样的路子，想设计2x,3x 多份资源进行适配。但是，通过阅读源码发现其实 Lottie本身在 Android 平台已经做了适配工作，而且适配原理很简单，解析  时，从  读取宽高之后 会再乘以手机的密度。再在使用的时候判断适配后的宽高是否超过屏幕的宽高，如果超过则再进行缩放。以此保障 Lottie 在 Android 平台的显示效果。

核心代码如下：

```java
//LottieComposition.fromSync
  float scale = res.getDisplayMetrics().density;
  int width = .optInt("w", -1);
  int height = .optInt("h", -1);

  if (width != -1 && height != -1) {
    int scaledWidth = (int) (width * scale);
    int scaledHeight = (int) (height * scale);
    bounds = new Rect(0, 0, scaledWidth, scaledHeight);
  }
  
  //LottieAnimationView.setComposition 

    int screenWidth = Utils.getScreenWidth(getContext());
    int screenHeight = Utils.getScreenHeight(getContext());
    int compWidth = composition.getBounds().width();
    int compHeight = composition.getBounds().height();
    if (compWidth > screenWidth ||
        compHeight > screenHeight) {
      float xScale = screenWidth / (float) compWidth;
      float yScale = screenHeight / (float) compHeight;
      setScale(Math.min(xScale, yScale));
      Log.w(L.TAG, String.format(
          "Composition larger than the screen %dx%d vs %dx%d. Scaling down.",
          compWidth, compHeight, screenWidth, screenHeight));
    }
```

这里值得一提的是，设计师在设计动画时要注意，**需要设计的是1X 的动画**，而不是2X or 3X or 4X。

目前手淘用的方案是 按4X 来设计(1X看不清元素)，然后**再缩小为1X**，**图片资源是4X**。

<a name="2dc96526"></a>
## 6. Lottie的绘制原理

LottieAnimationView 本身是个 ImageView，所以它的绘制流程跟 ImageView 一样，所有的绘制其实在 LottieDrawable 控制的。

接下去看看它的源码实现：

```java
// LottieDrawable
@Override public void draw(@NonNull Canvas canvas) {
    if (compositionLayer == null) {
      return;
    }
    float scale = this.scale;
    if (compositionLayer.hasMatte()) {
      scale = Math.min(this.scale, getMaxScale(canvas));
    }

    matrix.reset();
    matrix.preScale(scale, scale);
    compositionLayer.draw(canvas, matrix, alpha);
  }
```

可以看到在 `draw`方法里调用了 `compositionLayer.draw`方法，由于 CompositionLayer 继承了 BaseLayer，所以需要跟进 BaseLayer ，继续跟踪：

```java
// BaseLayer.draw
  @Override
  public void draw(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
    if (!visible) {
      return;
    }
    buildParentLayerListIfNeeded();
    //矩阵变换处理
    //....
    if (!hasMatteOnThisLayer() && !hasMasksOnThisLayer()) {
      matrix.preConcat(transform.getMatrix());
      //绘制 layer
      drawLayer(canvas, matrix, alpha);
      return;
    }
	//draw matteLayer& maskLayer
	//...
    canvas.restore();
  }
```

删除了多余代码，只保留核心代码，可以看到 draw 方法里调用了抽象方法 `drawLayer`，在这里的实现在 CompositionLayer ，一起来看看：

```java
@Override void drawLayer(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
    //...
    for (int i = layers.size() - 1; i >= 0 ; i--) {
      boolean nonEmptyClip = true;
      if (!newClipRect.isEmpty()) {
        nonEmptyClip = canvas.clipRect(newClipRect);
      }
      if (nonEmptyClip) {
        layers.get(i).draw(canvas, parentMatrix, parentAlpha);
      }
    }
    //...
  }
```

上面的代码中的 layers 是该动画所包含的层，在 CompositionLayer 的 drawLayer 方法里遍历了动画所有的层，并调用layers 的 draw 方法，这样就完成了所有的绘制。

<a name="df5cc73c"></a>
## 7. Lottie的动画原理

上一小节讲了 Lottie 的绘制原理，但是 Lottie 是用来做动画的，光理解它的绘制原理是不够的，对于动画，更重要的是它怎么动起来的。

接下来就分析一下 Lottie 的动画原理。

Lottie 动画起始于 `LottieAnimationView.playAnimation`，接着调用 LottieDrawable 的同名方法，与绘制相同，动画也是 LottieDrawable 控制的，来看看代码：

```java
//     animator 的申明
private final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);

  private void playAnimation(boolean setStartTime) {
    if (compositionLayer == null) {
      playAnimationWhenCompositionAdded = true;
      reverseAnimationWhenCompositionAdded = false;
      return;
    }
    if (setStartTime) {
      animator.setCurrentPlayTime((long) (progress * animator.getDuration()));
    }
    animator.start();
  }
```

playAnimation 方法其实只是开启了一个属性动画，那么后续动画是怎么动起来的呢？这就必须要看动画的监听了：

```java
animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        if (systemAnimationsAreDisabled) {
          animator.cancel();
          setProgress(1f);
        } else {
          setProgress((float) animation.getAnimatedValue());
        }
      }
    });
```

在 animator 进行的过程中回去调用 `setProgress`方法,下面跟踪一下代码：

```java
//LottieDrawable
  public void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
    this.progress = progress;
    if (compositionLayer != null) {
      compositionLayer.setProgress(progress);
    }
  }
  
  //CompositionLayer
  @Override public void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
    super.setProgress(progress);
    progress -= layerModel.getStartProgress();
    for (int i = layers.size() - 1; i >= 0; i--) {
      layers.get(i).setProgress(progress);
    }
  }

//BaseLayer
  void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
    //...
    for (int i = 0; i < animations.size(); i++) {
      animations.get(i).setProgress(progress);
    }
  }

//BaseKeyframeAnimation
  void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
    if (progress < getStartDelayProgress()) {
      progress = 0f;
    } else if (progress > getEndProgress()) {
      progress = 1f;
    }

    if (progress == this.progress) {
      return;
    }
    this.progress = progress;

    for (int i = 0; i < listeners.size(); i++) {
      listeners.get(i).onValueChanged();
    }
  }

//BaseLayer
  @Override public void onValueChanged() {
    invalidateSelf();
  }

//BaseLayer
  private void invalidateSelf() {
    lottieDrawable.invalidateSelf();
  }
```

上面列出了后续流程的主要代码，可以看到，setProgress 的最后触发了每个 layer 的 invalidateSelf，这都会让 lottieDrawable 重新绘制，然后重走一遍绘制流程，这样随着 animator 动画的进行，lottieDrawable 不断的绘制，就展现出了一个完整的动画。

PS: 动画过程中的一些变量比如 scale，都是由BaseKeyframeAnimation控制，但这个偏于细节，这里就不讲了。

动画原理流程稍微有点长，也稍微有些复杂，我绘制了一张图梳理了一下整体的流程，方便理解：

![](https://cdn.nlark.com/yuque/0/2019/jpg/138547/1553765029868-f5efbbd3-052c-4c15-af6f-0da8979252c4.jpg#align=left&display=inline&height=428&originHeight=904&originWidth=1576&size=0&status=done&width=746)

BaseKeyframeAnimation 类图：

![](https://cdn.nlark.com/yuque/0/2019/jpg/138547/1553765029875-19353495-fded-4c29-aefb-1e454418e924.jpg#align=left&display=inline&height=694&originHeight=884&originWidth=950&size=0&status=done&width=746)

<a name="f92f39cb"></a>
## 8. 总结

个人觉得 Lottie 是个非常非常棒的项目，甚至可以说是个伟大的项目。

Lottie 极大的缩减了动画的开发成本，给 APP 增加非常强力的动画能力，不需要各个端再自己去实现，而且目前 Lottie 已经支持了非常多的 AE 动画效果，通过 Lottie 可以轻松实现很多酷炫的效果，所以现在做动效考验的是设计同学的设计能力了，哈哈。

本文只针对重点原理进行分析，欢迎留言讨论交流。

<a name="01413423"></a>
## 9. 资料

1. lottie-android : [https://github.com/airbnb/lottie-android](https://github.com/airbnb/lottie-android)
1. Introducing Lottie: [https://medium.com/airbnb-engineering/introducing-lottie-4ff4a0afac0e](https://medium.com/airbnb-engineering/introducing-lottie-4ff4a0afac0e)
1. design-lottie: [https://airbnb.design/lottie/](https://airbnb.design/lottie/)
1. bodymovin: [https://github.com/bodymovin/bodymovin](https://github.com/bodymovin/bodymovin)
1. Animation: Jump-through: [https://medium.com/google-developers/animation-jump-through-861f4f5b3de4](https://medium.com/google-developers/animation-jump-through-861f4f5b3de4)

