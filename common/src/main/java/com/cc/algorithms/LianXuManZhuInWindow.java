package com.cc.algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class LianXuManZhuInWindow {

    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0}; // 只能输入 0 / 1
        solution(arr, 1, 1);
    }

    /**
     * 计算连续满足最小窗口总和的窗口个数
     *
     * @param arr         数组
     * @param winLen      单个窗口的长度
     * @param minWinTotal 窗口内数据的最小总和
     */
    private static void solution(int[] arr, int winLen, int minWinTotal) {
        System.out.println("arr = " + Arrays.toString(arr));
        Cir cir = new Cir(360, winLen, minWinTotal);

        ArrayList<Integer> cons = new ArrayList<>(); // 已连续个数
        for (int value : arr) {
            int continued = cir.add(value);
            cons.add(continued);
        }

        System.out.println("cons= " + cons);
    }

    private static class Cir {
        int[] arr;
        int len;
        int index = 0; // 下一次写入的下表
        int winLen;
        int currWinStart = 0;
        int minWinTotal;
        int continued = 0;

        Cir(int len, int winLen, int minWinTotal) {
            this.len = len;
            this.winLen = winLen;
            this.minWinTotal = minWinTotal;
            arr = new int[len];
        }

        public int add(int n) {
            arr[index] = n;
            index = (index + 1) % len;

            // 当前窗口被写满
            if (index >= currWinStart && index - currWinStart >= winLen ||
                    index < currWinStart && index + len - currWinStart >= winLen) {

                // 更新continued
                if (getCurrWinTotal() < minWinTotal) {
                    continued = 0;
                } else {
                    continued = Math.min(continued + 1, len / winLen);
                }
                currWinStart = index;
            }

            return continued;
        }

        /**
         * 计算当前窗口的total
         */
        private int getCurrWinTotal() {
            int total = 0;
            int from = currWinStart;
            for (int i = 0; i < winLen; i++) {
                total += arr[from];
                from = (from + 1) % len;
            }
            return total;
        }
    }
}
