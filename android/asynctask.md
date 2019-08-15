# AsyncTask

> AsyncTask enables proper and easy use of the UI thread. This class allows you
> to perform background operations and publish results on the UI thread without
> having to manipulate threads and/or handlers.
>
> AsyncTask is designed to be a helper class around {@link Thread} and {@link Handler}
> and does not constitute a generic threading framework. AsyncTasks should ideally be
> used for short operations (a few seconds at the most.) If you need to keep threads
> running for long periods of time, it is highly recommended you use the various APIs
> provided by the <code>java.util.concurrent</code> package such as {@link Executor},
> {@link ThreadPoolExecutor} and {@link FutureTask}.



AsyncTask ，抽象类， 被设计用于那些 **短时间（几秒）的异步操作拿到结果后需要反馈给UI 线程**的场景。



如果需要长时间的跑线程的话就不合适。



### AsyncTask 的常用方法



- `doInBackground(Params)`，异步执行任务时调用，并且接受参数，与Thread的run方法类似，**与其他方法不同的是, 这个方法必须要重写**（抽象方法）；
- `onPreExecute` ，在任务开始之前调用,通常可以用来做初始化参数,或者判断网络是否连接；
- `onProgressUpdate`， 在doInBackground中调用publishProgress()触发；
- `onPostExecute` ，doInBackground方法执行完成之后会调用，并接受Result；
- `onCancelled(Result)` ，任务被取消时候调用
- `onCancelled()`， 同上



一般情况下 AsyncTask 方法的执行顺序是：

onPreExecute —> doInBackground —> onProgressUpdate —> onPostExecute

它们也被称为是 **四大步骤**。



### 知识点汇总



1. AsyncTask 只能在主线程创建，因为它内部需要一个运行在主线程的 Handler，这需要主线程的 Looper;
2. AsyncTask 只能在主线程执行，并且不能被执行两次；
3. AsyncTask 中只有 `doingBackground`方法是在异步线程执行，其他的都是在主线程；
4. `AsyncTask.cancel(mayInterruptIfRunning)` 方法调用后并不能直接立即取消，而只是设置了个标记位而已，需要自己配合`isCancelled()`方法使用；
5. 取消后的 AsyncTask 会回调 `onCancelled(Object)`而不是 `onPostExecute(Object)`；
6. `推荐配合线程池使用`，因为*某些版本的 AsyncTask 任务是串行的*，一个一个按排序执行，并不支持并发；
7. AsyncTask 需要是静态内部类型，因为有内存泄露的风险，不要保存 context；