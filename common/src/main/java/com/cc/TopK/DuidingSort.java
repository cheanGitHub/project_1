package com.cc.TopK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class DuidingSort {

    private static Random random = new Random();

    public static void main(String[] args) {
        IntStream intStream = random.ints(100, 1, 1000);
//        IntStream intStream = random.ints(1000, 1, 10000);

        ArrayList<Integer> list = new ArrayList<>();
        intStream.forEach(list::add);
        System.out.println(list);

        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }

        int[] duidingsort = duidingsort(arr, 10);
        printArr(duidingsort);

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        System.out.println(list);
    }

    public static int[] duidingsort(int[] arr, int m) {

        int[] ints = new int[m+1];
        for (int i = 0; i < m; i++) {
            ints[i] = arr[i];
        }
        printArr(ints);

        DuiSort.heapSort(ints);
        for (int i = 0; i <= ints.length /2; i++) {
            DuiSort.swap(ints, i, ints.length - 1 - i);
        }
        printArr(ints);

        for (int i = m; i < arr.length; i++) {
            ints[m] = arr[i];
            shiftup(ints, m);
            printArr(ints);
        }

        return ints;
    }

    //大堆尾部 新增加一个元素 重排序
    public static void shiftup(int[] arr, int i) { //传入一个需要向上调整的结点编号i (从0开始)
        int flag = 0; //用来标记是否需要继续向上调整
        if (i == 0) return ; //如果是堆顶，就返回，不需要再调整了
        //不在堆顶，并且当前结点i的值比父节点小的时候就继续向上调整
        while (i != 0 && flag == 0) {
            //判断是否比父节点的大
            if (arr[i] > arr[(i+1)/2 -1]) {
                DuiSort.swap(arr, i, (i+1)/2 -1);
            } else{
                flag = 1;
            }
            i = (i+1)/2 -1;
        }
    }


    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length-1] + "\n\n");
    }
}
