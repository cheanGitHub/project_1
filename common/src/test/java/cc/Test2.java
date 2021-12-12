package cc;

import java.util.*;

public class Test2 {
    public static void main1(String[] args) {
        int N = 1;
        double e2;
        do {
            e2 = (835140 * N + 1) / 835139.0;
            System.out.println(e2 + "\t\t\t\t" + N);
            N++;
        } while (e2 != (long) e2);

        System.out.println(e2 + "\t\t\t\t" + N);
    }

    static class User<T extends Map> {

    }

    public static void main(String[] args) {
//        ArrayList<String> list1 = new ArrayList<String>();
//        ArrayList<Double> list2 = new ArrayList<Double>();
//        ArrayList<TestAnnoMain> list3 = new ArrayList<TestAnnoMain>();
//        System.out.println(list1.getClass());
//        System.out.println(list2.getClass());
//        System.out.println(list3.getClass());
//        System.out.println(new TestAnnoMain().getClass());
//
//        Executors.newFixedThreadPool(10);
//        new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//            }
//        });

        HashMap<String, String> m = new HashMap<>();
        HashMap<String, Object> m2 = new HashMap<>();
        m.put("a", "1");
        m.put("b", "3");
        m.put("c", "2");
        for (Map.Entry<String, String> en : m.entrySet()) {
            m2.put(en.getValue(), en);
        }
        ArrayList<Map.Entry<String, Object>> list = new ArrayList<>(m2.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey());
            }
        });
        System.out.println(list.get(0).getKey() + list.get(0).getValue());
        System.out.println(list.get(1));
        System.out.println(list.get(2));
    }
}
