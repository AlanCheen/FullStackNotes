package me.yifeiyuan.dsalgorithm.learn;

import java.util.ArrayList;

/**
 * Created by 程序亦非猿 on 2020/4/15.
 */
public class LearnArrayList {


    public static void main(String[] args) {

        ArrayList<Integer> intList = new ArrayList<Integer>();


        intList.add(1);
        intList.add(1,2);

        System.out.println(intList);//1,2

        intList.remove(new Integer(1));
        System.out.println(intList);//2

//        intList.remove(1);
//        System.out.println(intList);//1

    }
}
