package com.cc.concurrent;

import java.util.concurrent.Semaphore;

public class Main {
    // 2个线程交替执行
    static class SolutionTask implements Runnable {
        static int value = 0;
        @Override
        public void run() {
            while (value <= 100) {
                synchronized (SolutionTask.class) {
                    System.out.println(Thread.currentThread().getName() + ":" + value++);
                    SolutionTask.class.notify();
                    System.out.println(Thread.currentThread().getName() + ": notify");
                    try {
                        System.out.println(Thread.currentThread().getName() + ": wait start");
                        SolutionTask.class.wait();
                        System.out.println(Thread.currentThread().getName() + ": wait end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main1(String[] args) {
        new Thread(new SolutionTask(), "偶数").start();
        new Thread(new SolutionTask(), "奇数").start();
    }








    // N个线程循环执行
    static int result = 0;
    public static void main(String[] args) throws InterruptedException {
        int N = 3;
        Thread[] threads = new Thread[N];
        final Semaphore[] syncObjects = new Semaphore[N];
        for (int i = 0; i < N; i++) {
            syncObjects[i] = new Semaphore(1);
            if (i != N-1){
                syncObjects[i].acquire();
            }
        }
        for (int i = 0; i < N; i++) {
            final Semaphore lastSemphore = i == 0 ? syncObjects[N - 1] : syncObjects[i - 1];
            final Semaphore curSemphore = syncObjects[i];
            final int index = i;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            lastSemphore.acquire();
                            System.out.println("thread" + index + ": " + result++);
                            if (result > 100){
                                System.exit(0);
                            }
                            curSemphore.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }
    }
}