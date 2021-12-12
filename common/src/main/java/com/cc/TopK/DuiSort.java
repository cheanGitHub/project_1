package com.cc.TopK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DuiSort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DuiSort.class);

    // 堆排序  https://blog.csdn.net/tongdanping/article/details/79555125
    public static void heapSort(int[] arr) {
        LOGGER.info("heapsort " + arr + " start");
        long aa = System.currentTimeMillis();

        /*
         * 思路：首先要对用数组创建一个最大堆，然后把第一个数和最后一个数交换，然后对第一个数
         * 到倒数第二个数调整顺序，创建新的最大堆，然后把新的第一个数和倒数第二个数交换，以此类推
         * */
        int len = arr.length;
        for (int parent = (len - 1) / 2; parent >= 0; parent--) {
            adjustHeap(arr, parent, len);
        }
        int lastLeaf = len - 1;

        while (lastLeaf >= 1) {
            // System.out.println("lastLeaf:" + lastLeaf);
            swap(arr, 0, lastLeaf);
            /*
            for(int parent = (lastLeaf-1)/2; parent>=0;parent--){
                adjustHeap(arr,parent,lastLeaf);
            }*/
            adjustHeap(arr, 0, lastLeaf);
            lastLeaf--;
        }


        LOGGER.info("heapsort " + arr + " [" + arr.length + " int] used time = " + (System.currentTimeMillis() - aa) + " ms");
    }

    /**
     * 将  以序号(从0开始)为parent为父节点  的树，变成大堆
     *
     * @param arr
     * @param parent
     * @param len
     */
    public static void adjustHeap(int[] arr, int parent, int len) {
        /*
         * 思路：从最后一个父节点开始，如果有子节点大于父节点，那么把子节点和父节点交换，
         * 直到交换到叶子结点，然后对前一个父节点执行上述动作，直到对根节点执行完此操作
         * */
        // int len = arr.length;
        while ((parent * 2 + 1) < len) {
            int left = parent * 2 + 1;
            if (parent * 2 + 2 < len) {
                int right = parent * 2 + 2;
                if (arr[left] < arr[right]) {
                    swap(arr, left, right);
                }
            }
            //System.out.println(left);
            if (arr[parent] < arr[left]) {
                swap(arr, left, parent);
            }
            parent = parent * 2 + 1;
        }
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }





    public static void heapSort2(List<Integer> arr) {
        /*
         * 思路：首先要对用数组创建一个最大堆，然后把第一个数和最后一个数交换，然后对第一个数
         * 到倒数第二个数调整顺序，创建新的最大堆，然后把新的第一个数和倒数第二个数交换，以此类推
         * */
        int len = arr.size();
        for (int parent = (len - 1) / 2; parent >= 0; parent--) {
            adjustHeap2(arr, parent, len);
        }
        int lastLeaf = len - 1;

        while (lastLeaf >= 1) {
            // System.out.println("lastLeaf:" + lastLeaf);
            swap2(arr, 0, lastLeaf);
            /*
            for(int parent = (lastLeaf-1)/2; parent>=0;parent--){
                adjustHeap(arr,parent,lastLeaf);
            }*/
            adjustHeap2(arr, 0, lastLeaf);
            lastLeaf--;
        }
    }

    public static void adjustHeap2(List<Integer> arr, int parent, int len) {
        /*
         * 思路：从最后一个父节点开始，如果有子节点大于父节点，那么把子节点和父节点交换，
         * 直到交换到叶子结点，然后对前一个父节点执行上述动作，直到对根节点执行完此操作
         * */
        // int len = arr.length;
        while ((parent * 2 + 1) < len) {
            int left = parent * 2 + 1;
            if (parent * 2 + 2 < len) {
                int right = parent * 2 + 2;
                if (arr.get(left) < arr.get(right)) {
                    swap2(arr, left, right);
                }
            }
            //System.out.println(left);
            if (arr.get(parent) < arr.get(left)) {
                swap2(arr, left, parent);
            }
            parent = parent * 2 + 1;
        }
    }

    public static void swap2(List<Integer> a, int i, int j) {
        Integer temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
}
