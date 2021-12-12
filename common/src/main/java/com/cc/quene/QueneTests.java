package com.cc.quene;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.*;

public class QueneTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueneTests.class);

    // private static ThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS,
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS,
            // new LinkedBlockingQueue<>(3);
             new ArrayBlockingQueue<>(4, true)
            // new MyArrayBlockingQueue<>(4, true)
           /* , Executors.defaultThreadFactory(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    while (true) {
                        try {
                            System.out.println("rej " + r.toString());
                            executor.getQueue().put(r);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }*/
    );

    public static void now(Object obj) {
        System.out.println(obj + " " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(8, true); // new LinkedBlockingQueue<>(3);


//        new Thread(() -> {
//            try {
//                while (true) {
//                    now("   blockingQueue " + blockingQueue.toString());
//                    now("   executorQueue " + executor.getQueue());
//                    Thread.sleep(100);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();


        new Thread(() -> {
            try {
                while (true) {
//                    executor.getQueue().put(blockingQueue.take());
//                    executor.execute(executor.getQueue().take());
                    executor.execute(blockingQueue.take());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                for (int i = 1; i < 20; i++) {
                    int n = i;
                    Thread.sleep(RandomUtils.nextInt(1, 2) * 100);

                    /*executor.execute*/
                    /*executor.getQueue().put*/
                    blockingQueue.put
                            (new Runnable() {
                                @Override
                                public void run() {
                                    now("    " + n + " start  " + Thread.currentThread().getName());
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    now("    " + n + " end  ");
                                }

                                @Override
                                public String toString() {
                                    return "task_" + n;
                                }
                            });
                    now("added " + i);

//                    executor.execute(executor.getQueue().take());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    static class MyThreadPoolExecutor extends ThreadPoolExecutor{

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        public void execute(Runnable command) {
            try {
                super.execute(command);
            } catch (Exception e) {
                try {
                    getQueue().put(command);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    static class MyArrayBlockingQueue<E> extends ArrayBlockingQueue<E>{

        public MyArrayBlockingQueue(int capacity) {
            super(capacity);
        }

        public MyArrayBlockingQueue(int capacity, boolean fair) {
            super(capacity, fair);
        }

        public MyArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c) {
            super(capacity, fair, c);
        }

        @Override
        public boolean offer(E e) {
            try {
                put(e);
                return true;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

}
