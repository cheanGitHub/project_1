package com.cc.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class SpringTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));

//        TestAnnoMain bean = applicationContext.getBean(TestAnnoMain.class);
//        TestAnnoMain bean = xmlBeanFactory.getBean(TestAnnoMain.class);
//        Object bean = xmlBeanFactory.getBean("ss", CharSequence.class);
//        Object bean = xmlBeanFactory.getBean(MyFactoryBean.class);
        applicationContext.getBean(Worker.class).work();
//        xmlBeanFactory.getBean(AopTestWorker.class).work();

    }

    public static void main2(String[] args) {
        System.out.println(Arrays.toString(StringUtils.tokenizeToStringArray("classpath:applicationContext.xml \n\n   " +
                "classpath:applicationContext.xml", ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS, true, true)));
    }
}
