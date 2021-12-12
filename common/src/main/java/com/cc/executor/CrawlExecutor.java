package com.cc.executor;

import com.cc.concurrent.CompletableFutureTest;
import com.cc.crawl.impl.CrawlServiceImpl;
import com.cc.dao.impl.StartUrlDaoImpl;
import com.cc.doamin.StartUrl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

    ExecutorService es = Executors.newFixedThreadPool(3);
    private static Random rand = new Random();

    public static void main(String[] args) {

        new Thread(()-> {
            new CrawlExecutor().crawlCP();
        }).start();

        new Thread(()-> {
            new CrawlExecutor().crawlCP();
        }).start();
    }

    @Test
    public void crawlCP() {
        long a = System.currentTimeMillis();

        List<StartUrl> startUrls = new StartUrlDaoImpl().getStartUrlByStatusCode("2");
        LOGGER.info("startUrls = {}", startUrls);

//        StartUrl startUrl = new StartUrlDaoImpl().getStartUrlById("001");
//        System.out.println(startUrl);

        List<Object> results = new ArrayList<>();
        List<CompletableFuture<?>> cfs = new ArrayList<>();

        for (StartUrl startUrl : startUrls) {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(() -> new CrawlServiceImpl().crawl(startUrl, 1), es);

            // 计算结果完成时
            CompletableFuture<Boolean> f2 = f1.whenComplete((ret, e) -> {
                LOGGER.info("startUrl = {}, ret = {}, e = {}", startUrl, ret, e);
                results.add(ret);
            })
                    .exceptionally((ee) -> {
                        LOGGER.info("ee = " + ee);
                        results.add(false);
                        return false;
                    });
            ;


//            // 转换
//            CompletableFuture<String> f3 = f2.thenApply(s -> {
//                String ss = "AA_" + s.toString();
//                int i = 1/0;
//
//                results.add(ss);
//                return ss;
//            })
//                    // 处理异常
//                    .exceptionally((eee) -> {
//                        LOGGER.info("eee = " + eee);
//                        String sss = "AA_error";
//
//                        results.add(sss);
//                        return sss;
//                    });

            cfs.add(f2);
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