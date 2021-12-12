package com.cc.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamTest {

    public static void main0(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000000 * 2; i++) {
            int nextInt = random.nextInt(2000);
            list1.add(nextInt);
            list2.add(nextInt);
        }

//        System.out.println(list.size() + "  " + list1.size() );
//        Collections.copy(list1, list);
//        Collections.copy(list2, list);
//        System.out.println(list1);
//        System.out.println(list2);

        long start1 = System.currentTimeMillis();
        main1(list1);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        main2(list2);
        long end2 = System.currentTimeMillis();

        System.out.println("1: " + (end1 - start1));
        System.out.println("2: " + (end2 - start2));

    }

    public static void main1(List<Integer> list) {
        ArrayList<Integer> integers = new ArrayList<>();

        for (Integer t : list) {
            if (t > 1000) {
                integers.add(t);
            }
        }

        Collections.sort(integers, new Comparator<Integer>() {
            public int compare(Integer t1, Integer t2) {
                return t1.compareTo(t2);
            }
        });

        List<Integer> integers_2 = new ArrayList<>();
        for (Integer t : integers) {
            integers_2.add(t * 10);
        }

//        for (Integer t : integers_2) {
//            System.out.println(t);
//        }

    }

    public static void main2(List<Integer> integers) {
        integers.parallelStream()
                .filter(t -> t > 1000)
                .sorted(comparing(Integer::intValue)/*.reversed()*/)
                .map(t -> t * 10)
                .collect(toList())
//                .forEach(System.out::println)
        ;
    }




    public static void main3() throws IOException {

//        IntStream.average();
        BufferedReader br = new BufferedReader(new FileReader("D:\\JavaTools\\IdeaProjects\\project_1\\src\\main\\resources\\extra_main_10.dic"));
        String line = null;
        List<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.length() > 3) {
                list.add(line);
            }
        }
//        int size = list.size();
//        System.out.println(size);

    }

    public static void main4() throws FileNotFoundException {

//        IntStream.average();
        BufferedReader br = new BufferedReader(new FileReader("D:\\JavaTools\\IdeaProjects\\project_1\\src\\main\\resources\\extra_main_10.dic"));
        /*int size = */br.lines().filter(line -> line.length() > 3).collect(toList())/*.size()*/;
//        System.out.println(size);

    }


    public static void main(String[] args) throws IOException {
        long start1 = System.currentTimeMillis();
        main3();
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        main4();
        long end2 = System.currentTimeMillis();

        System.out.println("3: " + (end1 - start1));
        System.out.println("4: " + (end2 - start2));
    }


}
