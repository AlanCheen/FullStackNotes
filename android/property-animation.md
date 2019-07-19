# Property Animation(属性动画)



> Since Android 3.0

[View Animation](./view-animation.md)



- ViewPropertyAnimator
- ObjectAnimator
- ValueAnimator
- AnimatorSet
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

