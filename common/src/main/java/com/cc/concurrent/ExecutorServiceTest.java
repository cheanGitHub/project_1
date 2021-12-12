package com.cc.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServiceTest.class);

    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Test
    public void test() {

    }

    public static void main(String[] args) {
        LOGGER.info("ret = " + work());
    }

    private static String work() {
        LOGGER.info("start");
        executorService.execute(new Runnable() {
            public void run() {
                int n = 10;
                while (n > 0) {
                    LOGGER.info("Asynchronous working = " + n--);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                LOGGER.info("Asynchronous working = end");
            }
        });
        executorService.shutdown(); // 不会立即关闭，执行完所有任务就关闭（shutdownNow() 立即关闭）

        return "aa";
    }

}
