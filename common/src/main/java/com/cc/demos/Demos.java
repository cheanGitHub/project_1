package com.cc.demos;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Demos {

    public static void main(String[] args) {
        dispatch();
    }

    /**
     * 分发任务: id相同的任务给相同的线程来处理
     */
    private static void dispatch() {
        int num = 4;
        BlockingQueue<IndexTask>[] queues = new BlockingQueue[num];
        for (int i = 0; i < num; i++) {
            BlockingQueue<IndexTask> queue = new LinkedBlockingQueue<>(10);
            queues[i] = queue;
            new Thread(() -> {
                while (true) {
                    try {
                        Runnable runnable = queue.take();
                        runnable.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-" + i).start();
        }

        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Long id = (long) random.nextInt();
                    int max = random.nextInt(4);
                    int ver = 0;
                    while (ver++ < max) {
                        queues[id.hashCode() & (num - 1)].put(new IndexTask(id, ver));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class IndexTask implements Runnable {
        Long id;
        int version;

        public IndexTask(Long id, int version) {
            this.id = id;
            this.version = version;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " run indexTask: id = " + id + ", version = " + version);
        }

        public Long getId() {
            return id;
        }

        public IndexTask setId(Long id) {
            this.id = id;
            return this;
        }

        public int getVersion() {
            return version;
        }

        public IndexTask setVersion(int version) {
            this.version = version;
            return this;
        }
    }

}
