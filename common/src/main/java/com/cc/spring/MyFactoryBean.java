package com.cc.spring;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return "MyFactoryBean";
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
