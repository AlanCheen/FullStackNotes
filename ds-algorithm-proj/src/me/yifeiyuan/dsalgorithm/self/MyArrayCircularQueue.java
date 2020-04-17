package me.yifeiyuan.dsalgorithm.self;

/**
 * Created by 程序亦非猿 on 2020/4/16.
 * <p>
 *
 * 目前的实现会让费一个位置。
 */
public class MyArrayCircularQueue {


    private String[] items;

    private int size;

    private int head;
    private int tail;

    public MyArrayCircularQueue(int capacity) {
        this.size = capacity;
        items = new String[capacity];
    }

    public boolean enqueue(String value) {

        //队列满了
        if ((tail + 1) % size == head) {
            return false;
        }

        items[tail] = value;

        tail = (tail + 1) % size;

        return true;
    }

    public String dequeue() {

        if (tail==head){
            return null;
        }

        String value = items[head];
        head = (head+1)%size;
        return value;
    }


    public static void main(String[] args) {

        MyArrayCircularQueue queue = new MyArrayCircularQueue(5);

        System.out.println(queue.dequeue());//null

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i)); //4 true 2 false
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,null,null
        }


        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i)); //4 true 2 false
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,null,null
        }
    }
}

