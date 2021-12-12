package com.cc.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyDataSource extends AbstractRoutingDataSource {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String key = threadLocal.get();
        threadLocal.remove();
        return key;
    }

    public static void setDbKey(String key) {
        threadLocal.set(key);
    }

    public static void setMaster() {
        threadLocal.set(MASTER);
    }

    public static void setSlave() {
        threadLocal.set(SLAVE);
    }
}
