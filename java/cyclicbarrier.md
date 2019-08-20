# CyclicBarrier


CyclicBarrier 类实现了一个集结点(rendezvous) 称为障栅(barrier)。考虑大量线程运行 在一次计算的不同部分的情形。 当所有部分都准备好时， 需要把结果组合在一起。当一个线 程完成了它的那部分任务后， 我们让它运行到障栅处。一旦所有的线程都到达了这个障栅， 障栅就撤销， 线程就可以继续运行。

障栅被称为是循环的(cyclic), 因为可以在所有等待线程被释放后被重用。在这一点上，
有别于 CountDownLatch, CountDownLatch 只能被使用一次。

当线程做完了一部分事情后，调用 barrier.await() 方法进行等待，等到所有的线程都准备完，都调用了 await 后，就可以认为所有的线程都准备好了 ，此时，barrier action 会执行，然后所有的线程也可以开始执行。


设置屏障，当调用 barrier.await() 方法的线程数量到达限制，就会执行 barrier action。然后线程


例如：
```java
    private static void cyclicBarrier() {
    	//设定屏障为两个线程
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("CyclicBarrier run");
            }
        });

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("A is done.");
                //做完了 调用 barrier.await 等待其他线程
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //等 B 好了就可以执行了
                System.out.println("A after barrier");

            }
        });

        a.start();

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("B is done.");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("B after barrier");

            }
        });

        b.start();

        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads are done.");
    }
```

输出：
```
A is done.
B is done.
CyclicBarrier run
B after barrier
A after barrier
All threads are done.
```


