package me.yifeiyuan.dsalgorithm.self;

/**
 * Created by 程序亦非猿 on 2020/4/16.
 * <p>
 * todo
 */
public class MyLinkedQueue {


    private Node head;
    private Node tail;

    private int size;

    public MyLinkedQueue() {
    }

    //入队
    public boolean enqueue(String value) {

        Node item = new Node(value);

        //空
        if (size == 0) {
            head = item;
        } else {
            tail.next = item;
        }
        tail = item;
        size++;

        return true;
    }

    //出队
    public String dequeue() {

        //空队列
        if (size == 0) {
            return null;
        }

        String value = head.value;
        //head 指向 next
        head = head.next;

        size--;

        return value;
    }

    public static void main(String[] args) {

        MyLinkedQueue queue = new MyLinkedQueue();

        System.out.println(queue.dequeue());//null

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i)); //6 true
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,5
        }


        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i)); //6 true
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());//0,1,2,3,4,5
        }

    }
}
