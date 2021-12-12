package com.cc;

public class TestAnnoMain {

    public TestAnnoMain() {
    }

    @TestAnno("ta")
    public static void main(String[] args) throws Exception {
        TestAnno testAnno = TestAnnoMain.class.getMethod("main", String[].class).getAnnotation(TestAnno.class);
        System.out.println(testAnno.getClass().getDeclaredMethods());
    }

}
