package com.cc.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class CompletableFutureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

    ExecutorService es = Executors.newFixedThreadPool(3);
    private static Random rand = new Random();

    private static Integer getData(Integer in) {

        // CompletableFuture<Integer> future = new CompletableFuture<>();
        LOGGER.info("compute in = {} start", in);
        long t = System.currentTimeMillis();

        try {
            Thread.sleep(1000 * rand.nextInt(4));
            if (rand.nextInt(2) == 1) {
                // int i = 1 / 0;
            }

            // 告诉completableFuture任务已经完成
            // future.complete(rand.nextInt(in));
        } catch (Exception e) {
            LOGGER.error("compute error in = {}, error = {}, used {} ms", in, e.getMessage(), (System.currentTimeMillis() - t));
            return -1;
            // throw new RuntimeException(e);
            // 告诉completableFuture任务发生异常了
            // future.completeExceptionally(e);
        }

//        Integer ret = null;
//        try {
//            ret = future.get();
//        } catch (Exception e) {
//            LOGGER.info("compute error: ", e);
//        }

        LOGGER.info("compute in = {} end, used {} ms", in,  (System.currentTimeMillis() - t));

        // return rand.nextInt(in);
        return in * in;
    }

    @Test
    public void test1() throws Exception {
        long a = System.currentTimeMillis();

        List<Integer> taskList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            taskList.add(i);
        }

        List<Object> results = new ArrayList<>();
        List<CompletableFuture<?>> cfs = new ArrayList<>();

        for (Integer in : taskList) {
            CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> getData(in), es);

            // 计算结果完成时
            CompletableFuture<Integer> f2 = f1.whenComplete((s, e) -> {
                LOGGER.info("in = {}, s = {}, e = {}", in, s, e);
//                results.add(s);
            });

            // 转换
            CompletableFuture<String> f3 = f2.thenApply(s -> {
                String ss = "AA_" + s.toString();
                int i = 1/0;

                results.add(ss);
                return ss;
            })
                    // 处理异常
                    .exceptionally((eee) -> {
                        LOGGER.info("eee = " + eee);
                        String sss = "AA_error";

                        results.add(sss);
                        return sss;
                    });

            cfs.add(f3);
        }

        CompletableFuture.allOf(cfs.toArray(new CompletableFuture[0])).join();
        LOGGER.info("results = {}", results);
        LOGGER.info("total used time = {} ms", System.currentTimeMillis() - a);




//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(CompletableFutureTest::getData);
//        Future<Integer> f2 = future1.whenComplete((v, e) -> {
//            LOGGER.info("v = {}, e = ", v, e);
//        });
//
//        LOGGER.info("" + f2.get());
    }
}

