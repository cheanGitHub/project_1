package com.cc.suanfa;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
        work(new int[]{2, 1, 5, 6, 2, 3});
    }

    /**
     * 最大矩形
     */
    private static void work(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] maxs = Arrays.copyOf(arr, arr.length);
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = 0;

        int first = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((stack.empty() || arr[i] >= arr[stack.firstElement()])) {
                stack.push(i);
                System.out.println("1 i = " + i + ", stack = " + stack);
            } else {
                first = stack.firstElement();
                System.out.println("2 i = " + i + ", stack = " + stack + ", first = " + first);
                maxs[first] = arr[first] * (stack.peek() - first + 1);
                stack.clear();
                stack.push(first + 1);
                i = first + 1;
                System.out.println("2 i = " + i + ", stack = " + stack + ", arr = " + Arrays.toString(maxs));
            }
//            while (!stack.empty() && arr[i] < stack.peek()){
//
//            }
        }

        System.out.println(Arrays.toString(maxs));
        Arrays.sort(maxs);
        System.out.println("max = " + maxs[maxs.length - 1]);
    }
}
