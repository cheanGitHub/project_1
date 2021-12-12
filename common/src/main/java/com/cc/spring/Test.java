package com.cc.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan("com.cc.spring")
//@ComponentScan("b")

//@ComponentScans({
//        @ComponentScan("a"),
//        @ComponentScan("b")
//})

@Import(SpringTest.class)
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("");
        Object bean = applicationContext.getBean("");

        // new AnnotationConfigApplicationContext(MyFactoryBean.class);
    }
}
