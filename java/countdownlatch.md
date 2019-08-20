# CountDownLatch



### 示例

```java
    private static void countdownLatchTest() {

        final CountDownLatch latch = new CountDownLatch(2);

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread a done");
                latch.countDown();
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread a done");
                latch.countDown();
            }
        });

        a.start();
        b.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads are done~");
    }
```
输出：
```
thread a done
thread b done
All threads are done~
```

