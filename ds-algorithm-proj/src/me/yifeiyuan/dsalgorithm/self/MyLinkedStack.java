package me.yifeiyuan.dsalgorithm.self;

/**
 * Created by 程序亦非猿 on 2020/4/15.
 * <p>
 * todo
 */
public class MyLinkedStack {

    private Node top;

    public boolean push(String value) {
        Node item = new Node(value);

        if (top == null) {
            top = item;
        }else{
            item.next=top;
            top = item;
        }

        return true;
    }

    public String pop() {

        if (top == null) {
            return null;
        }

        String value = top.value;

        if (top.next != null) {
            top = top.next;
        }else{
            top = null;
        }

        return value;
    }


    public static void main(String[] args) {

        MyLinkedStack myArrayStack = new MyLinkedStack();

        for (int i = 0; i < 6; i++) {
            System.out.println(myArrayStack.push("" + i));
        }

        for (int i = 0; i < 7; i++) {
            System.out.println(myArrayStack.pop());
        }
    }
}
