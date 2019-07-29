# Property Animation(属性动画)



> Since Android 3.0

[View Animation](./view-animation.md)



- ViewPropertyAnimator，
- ObjectAnimator，
- ValueAnimator，
- AnimatorSet，
- 插值器
- TypeEvaluator
  - ArgbEvaluator  可以做颜色变化的动画
- PropertyValuesHolder





### ViewPropertyAnimator



专门针对 View 的属性做动画，可以通过`view.animate()`方法做相关动画。

```java
view.animate().translationY(300);
```



### ObjectAnimatior



注意：

1. 需要属性有 getter setter 方法，自定义 View 需要特别注意；



```java
ObjectAnimatior.ofXXX
```



### 实现原理



属性动画靠反射来修改 View 的真实属性来实现动画效果。





### 实战


#### View 从上往下弹出动画

利用 `translationY`属性可以做 Y 轴方向的动画，常见的一种就是 View 从下往上做一个弹出动画。

代码很简单：

```java
ObjectAnimator.ofFloat(targetView, View.TRANSLATION_Y, targetView.getHeight(), 0).setDuration(300).start();
```

注意点：

1. targetView 需**要有高度**，所以最开始**可见性不能为 GONE**，可以设置成 `INVISIBLE`；
2. translationY 应该从它的高度到 0 变化，这样才算是从底部到顶部完整的一个过程；

#### 颜色渐变动画

有时候我们需要做一个颜色渐变的动画，从色值 A 变化到色值 B。

系统有个 ArgbEvaluator 类，可以用来实现这个效果，但是并不对外开放调用，所以我们可以把带抠出来使用。

`ArgbEvaluator` 类如下：

```java
public class ArgbEvaluator implements TypeEvaluator {

    private static final ArgbEvaluator sInstance = new ArgbEvaluator();

    public ArgbEvaluator() {
    }

    public static ArgbEvaluator getInstance() {
        return sInstance;
    }

    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        float startA = (float) (startInt >> 24 & 255) / 255.0F;
        float startR = (float) (startInt >> 16 & 255) / 255.0F;
        float startG = (float) (startInt >> 8 & 255) / 255.0F;
        float startB = (float) (startInt & 255) / 255.0F;
        int endInt = (Integer) endValue;
        float endA = (float) (endInt >> 24 & 255) / 255.0F;
        float endR = (float) (endInt >> 16 & 255) / 255.0F;
        float endG = (float) (endInt >> 8 & 255) / 255.0F;
        float endB = (float) (endInt & 255) / 255.0F;
        startR = (float) Math.pow((double) startR, 2.2D);
        startG = (float) Math.pow((double) startG, 2.2D);
        startB = (float) Math.pow((double) startB, 2.2D);
        endR = (float) Math.pow((double) endR, 2.2D);
        endG = (float) Math.pow((double) endG, 2.2D);
        endB = (float) Math.pow((double) endB, 2.2D);
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);
        a *= 255.0F;
        r = (float) Math.pow((double) r, 0.45454545454545453D) * 255.0F;
        g = (float) Math.pow((double) g, 0.45454545454545453D) * 255.0F;
        b = (float) Math.pow((double) b, 0.45454545454545453D) * 255.0F;
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
```

具体使用示例如下：

1. 获取目标的背景 Drawable,这里是 shape ，对应的是 GradientDrawable；
2. 构建 ValueAnimator 并设置 evaluator 为我们的 ArgbEvaluator；
3. 添加 update 监听，将结果值设置到 drawable；
4. 开始动画即可。

```java
TextView ad = findViewById(R.id.tv_2);
final GradientDrawable drawable = (GradientDrawable) ad.getBackground();
int start = Color.parseColor("#333333");
int end = Color.parseColor("#FF6D2B");
//每次执行动画都需要重置一下
drawable。setColor(start);

final ValueAnimator anim = new ValueAnimator();
anim.setIntValues(start, end);
anim.setEvaluator(ArgbEvaluator.getInstance());
anim.setDuration(3000);
anim.addUpdateListener(new AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        drawable.setColor((Integer) anim.getAnimatedValue());
    }
});
anim.start();
```

注意：修改 Drawable 后会影响




