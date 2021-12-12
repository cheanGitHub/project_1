package com.cc.jvm;

public class _7_3_ClassLoading {
    //        RequestAbortedException.class;
    static {
        System.out.println("_7_3_ClassLoading init");
    }

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(obj instanceof _7_3_ClassLoading);

        System.out.println(_7_3_ClassLoading.A);
        System.out.println(Sub.A);

        System.out.println(MyInter.a);
        System.out.println(MySubInter.a);
    }

    static String A = "a";

    static class Sub extends _7_3_ClassLoading {
        static String A = "b";
    }

    interface MyInter {
        Integer a = 1;
    }
    interface MySubInter extends MyInter {
        Integer a = 2;
    }

}

