package com.cc.TopK;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FenziSort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FenziSort.class);

    ExecutorService es = Executors.newFixedThreadPool(10);
    private static Random random = new Random();


    @Test
    public void test1() throws Exception {
        long aa = System.currentTimeMillis();
        IntStream intStream = random.ints(100000, 1, 1000000);
//        IntStream intStream = random.ints(100, 1, 1000);
        ArrayList<Integer> list = new ArrayList<>();
        intStream.forEach(list::add);

        List<int[]> taskList = new ArrayList<>();
        int pageSize = list.size() / 10;
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - aa);


        long bb = System.currentTimeMillis();
        for (int pageIndex = 1; pageIndex <= 10; pageIndex++) {
            taskList.add(
                    getArr(list.subList(pageSize * (pageIndex - 1), pageSize * (pageIndex - 1) + pageSize)));
        }

        List<int[]> results = new ArrayList<>();
        List<CompletableFuture<int[]>> cfs = new ArrayList<>();
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - bb);


        long a = System.currentTimeMillis();
        for (int[] ins : taskList) {
            CompletableFuture<int[]> f1 = CompletableFuture.supplyAsync(() -> {
                DuiSort.heapSort(ins);
                return ins;
            }, es);

           // 计算结果完成时
            CompletableFuture<int[]> f2 = f1.whenComplete((s, e) -> {
                // LOGGER.info("ins = {}, \ns = {}, \ne = {}", ins, s, e);
                results.add(s);
            });

/*             // 转换
            CompletableFuture<String> f3 = f2.thenApply(s -> {
                String ss = "AA_" + s.toString();
                int i = 1 / 0;

                results.add(ss);
                return ss;
            })
                    // 处理异常
                    .exceptionally((eee) -> {
                        LOGGER.info("eee = " + eee);
                        String sss = "AA_error";

                        results.add(sss);
                        return sss;
                    });*/

            cfs.add(f2);
        }
        CompletableFuture.allOf(cfs.toArray(new CompletableFuture[0])).join();
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - a);

        long b = System.currentTimeMillis();
        int[] ss = new int[10 * 100];
        int n = 0;
        for (int[] s : results) {
            for (int i = 0; i < 100; i++) {
                ss[n++] = s[i];
            }
        }
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - b);

        long c = System.currentTimeMillis();
        DuiSort.heapSort(ss);
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - c);
        //LOGGER.info("results = {}", results);

//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(CompletableFutureTest::getData);
//        Future<Integer> f2 = future1.whenComplete((v, e) -> {
//            LOGGER.info("v = {}, e = ", v, e);
//        });
//
//        LOGGER.info("" + f2.get());
    }

    private int[] getArr(List<Integer> integers) {
        int[] ints = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            ints[i] = integers.get(i);
        }
        return ints;
    }
}
