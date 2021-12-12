package com.cc;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class Tests {

    public static void main(String[] args) {
        Object[] strings = new String[10];
        //strings[0] = 1;

        ArrayList<String> list = new ArrayList<>();
        // list.add(3);
        list.add("abc");
        System.out.println(list.get(0).getClass().getName());

        HashMap<String, Integer> map = new HashMap<>();
        //map.get("a").intValue();

        Integer[] integers = new Integer[5];
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        int sum = 0;
        for (int i : integerList) {
            sum += i;
        }
        System.out.println(sum);

        new ArrayList<String>().stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String cs) {
                return StringUtils.isNotBlank(cs);
            }
        });

        new Tests();
        new Tests();
    }

    public static void main2(String[] args) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            System.out.println(new Date());
            Thread.sleep(5000);
        }
    }

    public static void main1(String[] args) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 1000 * 150; i++) {
//            builder.append("01234ABCDE");
//        }
//        Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\a.txt"), builder.toString().getBytes(StandardCharsets.UTF_8));

        System.out.println(MyEnum.class.getSuperclass());
        System.out.println(Arrays.asList(MyEnum.class.getInterfaces()));
        System.out.println(Arrays.asList(MyEnum.class.getDeclaredMethods()));
        System.out.println(Arrays.asList(MyEnum.class.getSuperclass().getDeclaredMethods()).toString().replaceAll(",", ",\n"));
        MyEnum.values();
//        MyEnum.valueOf("");
//        MyEnum.valueOf(MyEnum.class, "");
    }
}
