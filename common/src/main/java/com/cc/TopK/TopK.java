package com.cc.TopK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TopK {

    private static Random random = new Random();

    public static void main(String[] args) {
        IntStream intStream = random.ints(100000, 1, 1000000);
//        IntStream intStream = random.ints(1000, 1, 10000);
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        intStream.forEach(list::add);
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                list2.add(new Integer(integer));
                list3.add(new Integer(integer));
            }
        });


        long a = System.currentTimeMillis();
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        System.out.println("list.sort = " + (System.currentTimeMillis() - a));




        int[] ints1 = new int[list2.size()];
        int[] ints2 = new int[list2.size()];
        for (int i = 0; i < list2.size(); i++) {
            ints1[i] = list2.get(i);
            ints2[i] = list2.get(i);
        }

        DuiSort.heapSort2(list);
        long c = System.currentTimeMillis();
        DuiSort.heapSort2(list3);
        System.out.println("heapSort List<Integer> = " + (System.currentTimeMillis() - c));

        long b = System.currentTimeMillis();
        DuiSort.heapSort(ints1);
        System.out.println("heapSort int[] = " + (System.currentTimeMillis() - b));

//        for (int i = 0; i < ints1.length; i++) {
//            System.out.print(ints1[i] + ", ");
//        }
//        System.out.println();
//        System.out.println(list3);




//        long d = System.currentTimeMillis();
//        GuibinSort.mergeSort(ints2);
//        System.out.println(System.currentTimeMillis() - d);








//        System.out.println(list.size());
//        System.out.println(list);

    }



    public static void main2(String[] args) {
        String S1 =
        "12301866845301177551304949" +
        "58384962720772853569595334" +
        "79219732245215172640050726" +
        "36575187452021997864693899" +
        "56474942774063845925192557" +
        "32630345373154826850791702" +
        "61221429134616704292143116" +
        "02221240479274737794080665" +
        "351419597459856902143413";

        String S2 = "33478071698956898786044169" +
                "84821269081770479498371376" +
                "85689124313889828837938780" +
                "02287614711652531743087737" +
                "814467999489";

        String S3 = "36746043666799590428244633" +
                "79962795263227915816434308" +
                "76426760322838157396665112" +
                "79233373417143396810270092" +
                "798736308917";

        System.out.println(Double.valueOf(S1));
        System.out.println(Double.valueOf(S2));
        System.out.println(Double.valueOf(S3));
        System.out.println("-------------------");

         System.out.println((double)Double.valueOf(S1) == Double.valueOf(S2) * Double.valueOf(S3));

        double d = Double.valueOf(S1);
        double d1 = 2.0;
        double d2;
        while (d1 < d) {
            d2 = d / d1;
            System.out.println(d2);
            System.out.println(Math.floor(d2));
            if (Math.floor(d2) == d2) {
                System.out.println(d1);
                System.out.println(d2);
                break;
            }
            d1++;
        }
    }

}
