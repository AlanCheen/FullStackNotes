# 【源码分析】Retrofit

> 注：本文基于 Retrofit2.0版本，并配合 RxJava 来分析。<br />com.squareup.retrofit2:retrofit:2.0.0<br />com.squareup.retrofit2:converter-gson:2.0.0<br />com.squareup.retrofit2:adapter-rxjava:2.0.0



	[Retrofit](https://square.github.io/retrofit/) adapts a Java interface to HTTP calls by using annotations on the declared 	methods to how requests are made.

本文主要通过分析 **Retrofit 与 RxJava 的合作流程** 来深入理解 Retrofit的工作原理，并且解答自己心中的疑惑。

<a name="ec573404"></a>

#### [](#p4ptwn)疑惑

1. 我们调用接口的方法后是怎么发送请求的？这背后发生了什么？

2. Retrofit 与 OkHttp 是怎么合作的？

3. Retrofit 中的数据究竟是怎么处理的？它是怎么返回  RxJava.Observable 的？


<a name="4119c0dc"></a>
## [](#cfx8kd)Retrofit 的基本使用

```java
public interface ApiService{
  @GET("data/Android/"+ GankConfig.PAGE_COUNT+"/{page}")
    Observable<GankResponse> getAndroid(@Path("page") int page);
}

// Builder 模式来构建 retrofit
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
// 通过 retrofit.create 方法来生成 service(非四大组件中的 Service)
ApiService service = retrofit.create(ApiService.class);
// 发起请求 获取数据
Observable<GankResponse> observable= service.getAndroid(1);
observable....
```

Retrofit 就这样经过简单的配置后就可以向服务器请求数据了，超级简单。

<a name="ae40b911"></a>
## [](#mlt9qt)Retrofit.create 方法分析

Retrofit的`create`方法作为 Retrofit 的入口，当然得第一个分析。

```java
public <T> T create(final Class<T> service) {
    //验证接口是否合理
    Utils.validateServiceInterface(service);
    //默认 false
    if (validateEagerly) {
      eagerlyValidateMethods(service);
    }
    // 动态代理
    return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          // 平台的抽象,指定默认的 CallbackExecutor CallAdapterFactory用，	这里 Android 平台是 Android (Java8 iOS 咱不管)
          private final Platform platform = Platform.get();
		  //ApiService 中的方法调用都会走到这里
          @Override public Object invoke(Object proxy, Method method, Object... args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            // 注释已经说明 Object 的方法不管
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            // java8 的默认方法，Android暂不支持默认方法，所以暂时也不需要管
            if (platform.isDefaultMethod(method)) {
              return platform.invokeDefaultMethod(method, service, proxy, args);
            }
            // 重点了 后面分析
            // 为 Method 生成一个 ServiceMethod
            ServiceMethod serviceMethod = loadServiceMethod(method);
            // 再包装成 OkHttpCall
            OkHttpCall okHttpCall = new OkHttpCall<>(serviceMethod, args);      // 请求
            return serviceMethod.callAdapter.adapt(okHttpCall);
          }
        });
  }
```

在上面的代码中可以看到，Retrofit 的主要原理是利用了 Java 的**动态代理技术**，把 ApiService 的方法调用集中到了`InvocationHandler.invoke`,再构建了ServiceMethod ,OKHttpCall，返回 `callAdapter.adapt` 的结果。

要弄清楚，还需要分析那最后三行代码。

一步一步来。

<a name="d5e0040e"></a>
## [](#i699vf)ServiceMethod的职责以及 loadServiceMethod分析

我认为 ServiceMethod 是接口**具体方法的抽象**，它主要负责解析它对应的 `method` 的各种参数（它有各种如 `parseHeaders` 的方法），比如注解（@Get），入参，另外还负责获取 callAdapter,responseConverter等Retrofit配置，好为后面的`okhttp3.Request`做好参数准备，它的`toRequest`为 OkHttp 提供 Request，可以说它承载了后续 Http 请求所需的一切参数。

再分析`loadServiceMethod`，比较简单。

```java
// serviceMethodCache 的定义
private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();
 // 获取method对应的 ServiceMethod
 ServiceMethod loadServiceMethod(Method method) {
    ServiceMethod result;
    synchronized (serviceMethodCache) {
      // 先从缓存去获取
      result = serviceMethodCache.get(method);
      if (result == null) {
        //缓存中没有 则新建，并存入缓存
        result = new ServiceMethod.Builder(this, method).build();
        serviceMethodCache.put(method, result);
      }
    }
    return result;
  }
```

`loadServiceMethod`方法，负责 为 `method` 生成一个 ServiceMethod ，并且给 ServiceMethod 做了缓存。

动态代理是有一定的性能损耗的，并且ServiceMethod 的创建伴随着各种注解参数解析，这也是耗时间的，在加上一个 App 调用接口是非常频繁的，如果每次接口请求都需要重新生成那么有浪费资源损害性能的可能，所以这里做了一份缓存来提高效率。

<a name="OkHttpCall"></a>
## [](#dnm6yt)OkHttpCall

再接下去往后看`OkHttpCall okHttpCall = new OkHttpCall<>(serviceMethod, args);`，是再为 ServiceMethod 以及 args(参数)生成了一个 `OkHttpCall`。

从 `OkHttpCall` 这个名字来看就能猜到，它是对 `OkHttp3.Call` 的组合包装,事实上，它也确实是。(`OkHttpCall`中有一个成员`okhttp3.Call rawCall`)。

<a name="1aa5284c"></a>
## [](#te8dbi)callAdapter.adapt流程分析

最后`return serviceMethod.callAdapter.adapt(okHttpCall)` 似乎是走到了最后一步。

如果说前面的都是准备的话，那么到这里就是真的要行动了。

来分析一下，这里涉及到的 `callAdapter`,是由我们配置 Retrofit 的 `addCallAdapterFactory`方法中传入的`RxJavaCallAdapterFactory.create()`生成，实例为`RxJavaCallAdapterFactory`。

实例的生成大致流程为：

ServiceMethod.Bulider.Build()

->ServiceMethod.createCallAdapter()

->retrofit.callAdapter()

->adapterFactories遍历

    ->最终到RxJavaCallAdapterFactory.get()#getCallAdapter()

      ->return `return new SimpleCallAdapter(observableType, scheduler);`

由于使用了 RxJava ，我们最终得到的 `callAdapter` 为 `SimpleCallAdapter`，所以接下去分析`SimpleCallAdapter`的 `adapt` 方法：

这里涉及到的 `CallOnSubscriber` 后面有给出：

```java
@Override public <R> Observable<R> adapt(Call<R> call) {
      // 这里的 call 是 OkHttpCall okHttpCall = new OkHttpCall<>(serviceMethod, args) 生成的 okHttpCall
      Observable<R> observable = Observable.create(new CallOnSubscribe<>(call)) //
          .flatMap(new Func1<Response<R>, Observable<R>>() {
            @Override public Observable<R> call(Response<R> response) {
              if (response.isSuccessful()) {
                return Observable.just(response.body());
              }
              return Observable.error(new HttpException(response));
            }
          });
      if (scheduler != null) {
        return observable.subscribeOn(scheduler);
      }
      return observable;
    }
  }
```

```java
static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    CallOnSubscribe(Call<T> originalCall) {
      this.originalCall = originalCall;
    }

    @Override public void call(final Subscriber<? super Response<T>> subscriber) {
      // Since Call is a one-shot type, clone it for each new subscriber.
      final Call<T> call = originalCall.clone();
      // Attempt to cancel the call if it is still in-flight on unsubscription.
      // 当我们取消订阅的时候 会取消请求 棒棒哒
      subscriber.add(Subscriptions.create(new Action0() {
        @Override public void call() {
          call.cancel();
        }
      }));

      try {
        // call 是 OkHttpCall 的实例
        Response<T> response = call.execute();
        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(response);
        }
      } catch (Throwable t) {
        Exceptions.throwIfFatal(t);
        if (!subscriber.isUnsubscribed()) {
          subscriber.onError(t);
        }
        return;
      }

      if (!subscriber.isUnsubscribed()) {
        subscriber.onCompleted();
      }
    }
  }
```

SimpleCallAdapter.adapt 很简单，创建一个 Observable获取CallOnSubscribe中的Response 通过 flatMap转成Observable后返回。这里去发送请求获取数据的任务在CallOnSubscribe.call 方法之中。并且最后走到了 okHttpCall.execute 中去了。

```java
// OkHttpCall.execute 
  
  @Override public Response<T> execute() throws IOException {
    okhttp3.Call call;

    synchronized (this) {
      //同一个请求 不能执行两次
      if (executed) throw new IllegalStateException("Already executed.");
      executed = true;
	 // ...省略 Execption 处理
     
      call = rawCall;
      if (call == null) {
        try {
          // 创建 okhttp3.call 
          call = rawCall = createRawCall();
        } catch (IOException | RuntimeException e) {
          creationFailure = e;
          throw e;
        }
      }
    }
    if (canceled) {
      call.cancel();
    }
	// 请求并解析response 这个 call 是 okhttp3.call 是真交给 OkHttp 去发送请求了 
    return parseResponse(call.execute());
  }

// 解析 response
  Response<T> parseResponse(okhttp3.Response rawResponse) throws IOException {
	//... 省略一些处理 只显示关键代码
    try {
      T body = serviceMethod.toResponse(catchingBody);
      return Response.success(body, rawResponse);
    } catch (RuntimeException e) {
      catchingBody.throwIfCaught();
      throw e;
    }
  }

// serviceMethod.toResponse
  T toResponse(ResponseBody body) throws IOException {
    // 还记得吗？这就是我们配置Retrofit时候的 converter
    return responseConverter.convert(body);
  }
```

经过一连串的处理，最终在 OkHttpCall.execute() 的方法中生成 okhttp3.call 交给 OkHttpClient 去发送请求，再由我们配置的 Converter(本文为GsonConverterFactory) 处理 Response,返回给SimpleCallAdapter处理，返回我们最终所需要的Observable。

<a name="6fd82d5d"></a>
## [](#xdv1hc)流程分析流程图总结

总体的流程图整理如下：

![](http://ww4.sinaimg.cn/large/006tNc79ly1g5gudd15svj30c80irmxp.jpg)

<a name="5cb4bb70"></a>

## [](#7gqavd)解答疑问

对于之前的疑问可以作答了。

<a name="0135a839"></a>
#### [](#p1rsrm)第一个疑问: 我们调用接口的方法后是怎么发送请求的？这背后发生了什么？

Retrofit 使用了动态代理给我们定义的接口设置了代理，当我们调用接口的方法时，Retrofit 会拦截下来，然后经过一系列处理，比如解析方法的注解等，生成了 Call Request 等OKHttp所需的资源，最后交给 OkHttp 去发送请求， 此间经过 callAdapter,convertr 的处理，最后拿到我们所需要的数据。

<a name="4218c8b0"></a>
#### [](#cg6vox)第二个疑问: Retrofit 与 OkHttp 是怎么合作的？

在Retrofit 中，ServiceMethod 承载了一个 Http 请求的所有参数，OkHttpCall 为 okhttp3.call 的组合包装，由它们俩合作，生成用于 OkHttp所需的 Request以及okhttp3.Call，交给 OkHttp 去发送请求。(在本文环境下具体用的是 `call.execute()`)

可以说 Retrofit 为 OkHttp 再封装了一层，并增添了不少功能以及扩展，减少了开发使用成本。

<a name="aeb5b350"></a>
#### [](#l2zsas)第三个疑问: Retrofit 中的数据究竟是怎么处理的？它是怎么返回  RxJava.Observable 的？

Retrofit 中的数据其实是交给了 callAdapter 以及 converter 去处理，callAdapter 负责把 okHttpCall 转成我们所需的 Observable类型(本文环境),converter负责把服务器返回的数据转成具体的实体类。

<a name="5db9fd7c"></a>
## [](#4efyxr)小结

Retrofit 的源码其实非常好跟也非常好理解，不像看 framework 的代码，跟着跟着就不见了。

另外 Retrofit的代码确实非常漂亮，将设计模式运用的可以说是炉火纯青，非常值得学习。
