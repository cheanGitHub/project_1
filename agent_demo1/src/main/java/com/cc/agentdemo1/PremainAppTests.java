package com.cc.agentdemo1;

public class PremainAppTests {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main");
        for (int i = 0; i < 200; i++) {
            Thread.sleep(1000);
        }
    }
}
