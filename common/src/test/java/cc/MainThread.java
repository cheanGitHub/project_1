package cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LiftOff implements Runnable {

    Logger logger = LoggerFactory.getLogger(LiftOff.class);

    protected int countDown = 10; // Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" +
                (countDown > 0 ? countDown : "Liftoff!") + "), ";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            logger.info(status());
            Thread.yield();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MainThread {

    Logger logger = LoggerFactory.getLogger(MainThread.class);

    @Test
    public void test() {
        Thread t = new Thread(new LiftOff());
        t.start();
        t.setName("lift off thread");
        logger.info("waiting for liftoff");
    }
}
