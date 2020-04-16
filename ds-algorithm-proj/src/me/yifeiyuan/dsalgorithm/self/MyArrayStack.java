package me.yifeiyuan.dsalgorithm.self;

/**
 * Created by 程序亦非猿 on 2020/4/15.
 * <p>
 * 用数组实现栈
 */
public class MyArrayStack {

    private String[] items;
    private int n;//栈的大小
    private int count;//栈内元素的个数

    public MyArrayStack(int n) {
        this.items = new String[n];
        this.n = n;
    }

    public boolean push(String value) {
        //栈满了
        if (count == n) {
            return false;
        }

        items[count] = value;
        count++;
        return true;
    }

    public String pop() {
        //栈空了
        if (count == 0) {
            return null;
        }

        String value = items[count - 1];
        count--;
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("," + i);
        }
        return sb.length() > 0 ? sb.substring(1) : "";
    }

    public static void main(String[] args) {

        MyArrayStack myArrayStack = new MyArrayStack(5);

        for (int i = 0; i < 6; i++) {
            System.out.println(myArrayStack.push("" + i));
        }

        System.out.println(myArrayStack);

        for (int i = 0; i < 7; i++) {
            System.out.println(myArrayStack.pop());
        }
        System.out.println(myArrayStack);
    }
}
