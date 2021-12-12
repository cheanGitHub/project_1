package com.cc.spi;

public class MySpiInterfaceImpl implements MySpiInterface {

    static {
        System.out.println("MySpiInterfaceImpl.class.getClassLoader() 1 = " + MySpiInterfaceImpl.class.getClassLoader());
        System.out.println("Thread.currentThread().getContextClassLoader() 1 = " + Thread.currentThread().getContextClassLoader());
    }

    public MySpiInterfaceImpl() {
        System.out.println("MySpiInterfaceImpl.class.getClassLoader() 2 = " + MySpiInterfaceImpl.class.getClassLoader());
        System.out.println("Thread.currentThread().getContextClassLoader() 2 = " + Thread.currentThread().getContextClassLoader());
    }
}
