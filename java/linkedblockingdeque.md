# LinkedBlockingDeque





```java
public static void main(String[] args) {
    BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    queue.add("A");
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.add("B");
        }
    }).start();
    try {
        System.out.println("take "+queue.take());
        System.out.println("=========");
        System.out.println("take " + queue.take());
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

输出A 后停顿一秒再输出 B：
```
take A
=========
take B
```


