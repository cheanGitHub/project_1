package com.cc.reflections;

import com.google.common.base.Stopwatch;
import org.reflections.Reflections;

import java.io.InputStream;
import java.util.Set;

/**
 * @date 2018/5/20
 **/
public class ReflectionTest {

    public static void main(String[] args) {
        Reflections reflections = new Reflections("java.");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Class<? extends InputStream>> allTypes = reflections.getSubTypesOf(InputStream.class);
        System.out.println(stopwatch.toString());
        for (Class type : allTypes) {
            System.out.println(type.getName());
        }
    }
}
