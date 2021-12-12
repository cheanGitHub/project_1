package com.cc.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuavaTests {

    public static void main(String[] args) throws Exception {
        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个
        Thread.sleep(5000);
        for (int i = 1; i <= 20; i++) {
            String start = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
            double waitTime = limiter.acquire(1);// 请求RateLimiter, 超过permits会被阻塞
            System.out.println("start = " + start + "  cutTime = " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + " call execute:" + i + " waitTime:" + waitTime);

//            if (waitTime > 0.1/* && RandomUtils.nextInt(1, 4) == 1*/) {
//                Thread.sleep(5000);
//            }
//            AopProxyUtils.getSingletonTarget()
        }
    }

}
