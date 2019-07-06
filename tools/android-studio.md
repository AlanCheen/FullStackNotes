# Android Studio





### Live Templates



- `$className$`，当前类的类名；
- `$CURSOR$`，编辑器的光标；



```java
private static class $className$Handler extends Handler {

    private final WeakReference<$className$> ref ;

    public $className$Handler($className$ target) {
        this.ref = new WeakReference<$className$>(target);
    }

    @Override
    public void handleMessage(Message msg) {
        $className$ target = ref.get();
        if (null == target) {
            return ;
        }
        target.$CURSOR$();
    }
}
```





#### 自定义

