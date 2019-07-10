# RxJava 2.x





### 基础的类



- **Flowable**：0..N flows，supporting backpressure，支持背压，搭配 Subscriber
- **Observable**：0..N flows，no backpressure，不支持背压，性能更好些，搭配 Observer
- **Single**：1 item or an error，



#### Single



```java
Disposable disposable = Single.create(new SingleOnSubscribe<String>() {
    @Override
    public void subscribe(final SingleEmitter<String> emitter) throws Exception {
        emitter.onSuccess("Hello world");
    }
}).subscribe(new BiConsumer<String, Throwable>() {
    @Override
    public void accept(final String s, final Throwable throwable) throws Exception {

    }
});
```



- SingleOnSubscribe

- SingleEmitter<T>
  - onSuccess(@NonNull T t)
  - onError(@NonNull Throwable t)

- 





### RxJava2 的坑



#### 取消订阅后的 Observable 不会捕获异常，导致 App crash

RxJava2 不会吃掉任何一个异常，导致*下游取消订阅后，上游抛出的异常不会被下游捕获，导致 crash*。



**场景**：下游取消订阅了，但是上游异步任务依然在执行，并且抛了异常，这个时候下游不会 catch 到这个异常。

异常堆栈：

```java
io.reactivex.exceptions.UndeliverableException: java.lang.RuntimeException: Task failure:UploadTaskError{code='100', subcode='4', info='connect error'}
	at io.reactivex.plugins.RxJavaPlugins.onError(RxJavaPlugins.java:367)
	at io.reactivex.internal.operators.observable.ObservableCreate$CreateEmitter.onError(ObservableCreate.java:74)
	at com.xxx.android.videoenable.module.upload.Task$1$1.onFailure(Task.java:89)
	at com.xxx.android.videoenable.module.upload.DefaultTaskListener.onFailure(DefaultTaskListener.java:56)
	at com.uploader.implement.a.b.run(ActionNotifiable.java:72)
	at android.os.Handler.handleCallback(Handler.java:873)
	at android.os.Handler.dispatchMessage(Handler.java:99)
	at android.os.Looper.loop(Looper.java:214)
	at android.app.ActivityThread.main(ActivityThread.java:7032)
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:494)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:965)
```



修复方法，`RxJavaPlugins.setErrorHandler` 设置异常的 Consumer 来处理异常：

```java
RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(final Throwable throwable) throws Exception {
                Log.e(TAG,"RxJava2 Error",throwable);
            }
        });
```







### 资料

https://github.com/ReactiveX/RxJava