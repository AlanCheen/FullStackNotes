package me.yifeiyuan.dsalgorithm.learn;

/**
 * Created by 程序亦非猿 on 2019/12/30.
 */
public class LearnLinkedList {


    public static void main(String[] args) {

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

    }


    class LinkedList {


        Node header;

        Node tail;

        public void add(int value) {

        }

    }

    public static class Node {

        Node next;
        int value;

        public Node(int value) {
            this.value = value;
        }

        Node next() {
            return next;
        }
    }
}
