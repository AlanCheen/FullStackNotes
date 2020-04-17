package me.yifeiyuan.dsalgorithm.self;

/**
 * Created by 程序亦非猿 on 2020/4/16.
 */
public class MyArrayQueue {


    private String[] items;

    //大小
    private int size;

    //队头下标
    private int head;

    //队尾下标
    private int tail;

    public MyArrayQueue(int capacity) {
        this.size = capacity;
        items = new String[capacity];
    }

    //入队
    public boolean enqueue(String value) {
        //满
        if (tail == size) {
            return false;
        }
        items[tail] = value;
        tail++;
        return true;
    }

    public boolean enqueue2(String value) {

        //队列末尾没有空间了
        if (tail == size) {

            //队列满了
            if (head == 0) {
                return false;
            }

            //队列还没满，做数据迁移
            for (int i = head; i < tail; i++) {
                items[i - head] = items[i];
            }
            tail -= head;
            head = 0;
        }
        items[tail] = value;
        tail++;
        return true;
    }


    //出队
    public String dequeue() {

        //空
        if (head == tail) {
            return null;
        }

        String value = items[head];
        head++;
        return value;
    }


    public static void main(String[] args) {

        MyArrayQueue queue = new MyArrayQueue(5);

        System.out.println(queue.dequeue());//null

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i)); //5 true 1 false
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,4,null
        }


        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue2("" + i)); //5 true 1 false
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,4,null
        }

    }
}
