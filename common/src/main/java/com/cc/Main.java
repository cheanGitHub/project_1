package com.cc;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        get();
    }

    private static void get() {
        //创建字符串布隆过滤器，使用编码UTF-8
        //创建时需要传入四个参数，但我们只要关心前三个就行
        //Funnel，这是Guava中定义的一个接口，它和PrimitiveSink配套使用，主要是把任意类型的数据转化成Java基本数据类型（primitive value，如char，byte，int……），默认用java.nio.ByteBuffer实现，最终均转化为byte数组
        //expectedInsertions 期望插入数据数，int或long
        //fpp期望误判率，比如1E-7（千万分之一）
        //Strategy 策略，默认选取64位哈希映射函数，BloomFilterStrategies.MURMUR128_MITZ_64
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 100000, 1E-3);

        //置入元素，其实也有boolean类型返回，但是尊重注解，就不返回值了
        for (int i = 0; i < 400000; i++) {
            if (i != 1100 && i != 120001) {
                bloomFilter.put(i); // bloomFilter.put("测试测试");}
            }
        }

        //判断元素是否存在，true存在，false不存在。
        System.out.println(bloomFilter.mightContain(1100));
        System.out.println(bloomFilter.mightContain(120001));

        //ArrayDeque会比LinkedList在除了删除元素这一点外会快一点
        //参考：https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist
        ArrayDeque<Character> deque = new ArrayDeque<>();
        new LinkedList().pollFirst();
        new PriorityQueue();
    }

}