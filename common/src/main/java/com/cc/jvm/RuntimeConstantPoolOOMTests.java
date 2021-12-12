package com.cc.jvm;

import java.util.HashSet;
import java.util.Set;

public class RuntimeConstantPoolOOMTests {

    public int m;

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        Set<String> set = new HashSet<String>();
        Integer n = 0;
        while (true) {
            set.add(String.valueOf(n++).intern());
        }

    }

}
