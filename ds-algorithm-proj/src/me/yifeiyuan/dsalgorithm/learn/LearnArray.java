package me.yifeiyuan.dsalgorithm.learn;

/**
 * Created by 程序亦非猿 on 2019/12/30.
 */
public class LearnArray {


    public static void main(String[] args) {

//        accessArray();
//        insertArray();
        deleteArray();
    }

    private static void accessArray() {
        int[] array = new int[5];

        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        array[3] = 4;
        array[4] = 5;

        //for
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

        //foreach
        for (int i : array) {
            System.out.println(i);
        }
    }

    private static void deleteArray() {
        int[] array = new int[4];

        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        array[3] = 4;
        //1 2 3 4，尝试删除 2，变成 1 3 4

        int deleteIndex = 1;

        //元素都往前移动
        for (int i = deleteIndex; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }

        array[array.length - 1] = 0;//最后一位数置为 0

        for (int i : array) {
            System.out.println(i);
        }

    }

    //数组中插入元素，省略了边界条件判断
    private static void insertArray() {
        int[] array = new int[4];

        array[0] = 1;
        array[1] = 3;
        array[2] = 4;

        //1,3,4,0,0
        for (int i : array) {
            System.out.println(i);
        }

        System.out.println("==========");

        //尝试在 1 3 之间插入 2，变成 1 2 3 4 0
        int targetIndex = 1;
        int insertValue = 2;

        //从末尾开始，都赋值为前面元素的值，就完成了后移
        for (int i = array.length - 1; i > targetIndex; i--) {
            array[i] = array[i - 1];
        }
        array[targetIndex] = insertValue;

        //1 2 3 4 0
        for (int i : array) {
            System.out.println(i);
        }
    }


}
