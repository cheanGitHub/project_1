package com.cc.concurrent;

public class ThreadLocalTest {



    public static void main3(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("A");
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    t1.join(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
            }
        };
        t2.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
            }
        }.start();
    }

    public static void main3_2(String[] args) {
        Object obj = new Object();
        new Thread() {
            @Override
            public void run() {
                System.out.println("A");
                synchronized (obj) {
                    obj.notify();
                }
                System.out.println("A1");
            }
        }.start();

        Object obj2 = new Object();
        new Thread() {
            @Override
            public void run() {
                System.out.println("B1");
                try {
                    synchronized (obj) {
                        obj.wait();
                    }
                    System.out.println("B2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                synchronized (obj2) {
                    obj2.notify();
                }
                System.out.println("B3");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("C1");
                    synchronized (obj2) {
                        obj2.wait();
                    }
                    System.out.println("C2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
            }
        }.start();
    }

    public static void main(String[] args) {
        main3(null);
//        int n = 0;
//        for (int i = 0; i < 15; i++) {
//            System.out.println(n & 15);
//            n += 0x61c88647;
//            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
//            threadLocal.set("a");
//    }
    }

    public static void main2(String[] args) {

        final ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
        final ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
//        CompletableFuture.
//        TtlExecutors.getTtlExecutor(null)
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set("A");
                threadLocal2.set(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadLocal1.get());
                System.out.println(threadLocal2.get());

                threadLocal1.set("A1");
                System.out.println(threadLocal1.get());

                System.out.println(Thread.currentThread().getName());
            }
        }).start();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                threadLocal1.set("B");
//                threadLocal2.set(2);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName());
//                System.out.println(threadLocal1.get());
//                System.out.println(threadLocal2.get());
//            }
//        }).start();
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//                System.out.println(threadLocal1.get());
//                System.out.println(threadLocal2.get());
//            }
//        }).start();
    }
}