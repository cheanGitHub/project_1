package com.cc.TopK;

public class GuibinSort {

    public static void mergeSort(int[] arr) {
        /*
         * 思路：先递归的调用sort函数把数组平均分为两组，然后用merge函数把分开的每个组排序并合并起来
         * */
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int meddle = (begin + end) / 2;
        sort(arr, begin, meddle);
        sort(arr, meddle + 1, end);
        merge(arr, begin, meddle, end);
    }

    public static void merge(int[] arr, int begin, int meddle, int end) {
        int[] newArr = new int[end - begin + 1];
        //从begin到meddle，和meddle+1到end中，把小的数放进newArr中
        int newIndex = 0;
        int i = begin;
        int j = meddle + 1;
        while (i <= meddle && j <= end) {
            if (arr[i] > arr[j]) {
                newArr[newIndex++] = arr[j++];
            } else {
                newArr[newIndex++] = arr[i++];
            }
        }
        while (i <= meddle) {
            newArr[newIndex++] = arr[i++];
        }
        while (j <= end) {
            newArr[newIndex++] = arr[j++];
        }
        //更新把排序好的newArr更新到arr对应的位置中
        for (int k = begin; k <= end; k++) {
            arr[k] = newArr[k - begin];
        }
    }

}
