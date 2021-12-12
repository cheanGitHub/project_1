package com.cc.concurrent;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        List<Integer> list = forkJoinPool.invoke(new MyRecursiveTask(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7)));
        System.out.println(list);
    }

    private static class MyRecursiveTask extends RecursiveTask<List<Integer>> {

        List<Integer> list;

        public MyRecursiveTask(List<Integer> list) {
            this.list = list;
        }

        @Override
        protected List<Integer> compute() {
            if (list.size() > 3) {
                int n = list.size() / 2;
                List<Integer> list1 = list.subList(0, n);
                List<Integer> list2 = list.subList(n, list.size());

                MyRecursiveTask task1 = new MyRecursiveTask(list1);
                task1.fork();
                MyRecursiveTask task2 = new MyRecursiveTask(list2);
                task2.fork();

                List<Integer> ret = new ArrayList<>();
                ret.addAll(task1.join());
                ret.addAll(task2.join());
                return ret;
            }
            System.out.println(list);
            return list;
        }
    }

}
