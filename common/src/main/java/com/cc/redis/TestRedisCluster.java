package com.cc.redis;

import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestRedisCluster {

    private static JedisCluster jedis;

    static {
        init();
    }

    /**
     * 初始化redis连接池
     */
    private static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);   //最大空闲数
        config.setMaxWaitMillis(60000); //连接时最大的等待时间（毫秒）
        config.setMaxTotal(500);    //最大连接数
        config.setTestOnBorrow(true);   //在提取一个jedis实例时，是否提前进行验证操作；如果为true，则得到的jedis实例均是可用的

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 7001));
        nodes.add(new HostAndPort("127.0.0.1", 7002));
        nodes.add(new HostAndPort("127.0.0.1", 7003));
        jedis = new JedisCluster(nodes, config);
    }

    public static JedisCluster getJedis() {
        return jedis;
    }

    public static void returnJedis(JedisCluster jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main1(String[] args) {
        JedisCluster jedis = TestRedisCluster.getJedis();

        System.out.println(jedis.get("k1"));
        System.out.println(jedis.get("k2"));
        System.out.println(jedis.get("k3"));

        jedis.set("name", "zwin");
        jedis.set("age", "29");

        jedis = TestRedisCluster.getJedis();
        String name = jedis.get("name");
        String age = jedis.get("age");
        System.out.println("name[" + name + "], age[" + age + "]");

        //JedisCluster为单例模式，需最后再关闭
        returnJedis(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = new JedisPool("127.0.0.1", 6379).getResource();
        jedis.select(0);
        System.out.println(jedis.rename("k11", "k1"));
        System.out.println(jedis.set("k2", "v2"));
        System.out.println(jedis.keys("*"));
        jedis.close();

        jedis.flushDB();
        jedis.flushAll();
    }

}