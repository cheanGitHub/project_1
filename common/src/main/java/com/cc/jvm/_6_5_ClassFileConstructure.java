package com.cc.jvm;

import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service(value = "abbb")
public class _6_5_ClassFileConstructure<T> {

    public int inc(@NonNull int p) {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }

    public void testSync(Object obj) {
        synchronized (obj) {
            int i = 0;
        }
    }
}
