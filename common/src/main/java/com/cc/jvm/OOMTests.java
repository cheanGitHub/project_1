package com.cc.jvm;

import java.util.ArrayList;
import java.util.List;

public class OOMTests {

    public static void main(String[] args) throws Exception {

        List<OomObj> list = new ArrayList<>();

        for (long i = 0; i < 1000000000L; i++) {
            if (i % 10 == 0) {
                Thread.sleep(2);
            }
            list.add(new OomObj());
        }

    }

    private static class OomObj {
    }

}
