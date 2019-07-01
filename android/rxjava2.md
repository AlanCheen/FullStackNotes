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



### 资料

https://github.com/ReactiveX/RxJava