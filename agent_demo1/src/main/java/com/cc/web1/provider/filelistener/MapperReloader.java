package com.cc.web1.provider.filelistener;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapperReloader implements BeanPostProcessor {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MybatisAutoConfiguration mybatisAutoConfiguration;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public static SqlSessionFactoryBean sqlSessionFactoryBean;

    // @Autowired(required = false)
    // private MybatisAutoConfiguration mybatisAutoConfigurationImpl;
    private final Map<Object, Field> beanAndMapperField = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //if ("userService".equals(beanName)) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.getDeclaringClass();
            if (field.getType().isAnnotationPresent(Mapper.class)) {
                field.setAccessible(true);
                beanAndMapperField.put(bean, field);
            }
        }
        //}
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void reloadMapper() {
        try {
//            Method method = mybatisAutoConfiguration.getClass().getDeclaredMethods()[1];
//            Method method = MybatisAutoConfiguration.class.getDeclaredMethods()[2];
//            Object obj = method.invoke(mybatisAutoConfiguration, dataSource);

            // mybatisAutoConfiguration.sqlSessionFactory(dataSource);
            // sqlSessionFactoryBean.getObject();
            Method method = sqlSessionFactoryBean.getClass().getDeclaredMethod("buildSqlSessionFactory");
            method.setAccessible(true);
            SqlSessionFactory sqlSessionFactoryOwn = (SqlSessionFactory) method.invoke(sqlSessionFactoryBean);
            SqlSession sqlSessionOwn = sqlSessionFactoryOwn.openSession(ExecutorType.SIMPLE);
            Configuration configuration = sqlSessionFactoryOwn.getConfiguration();
            MapperRegistry mapperRegistry = configuration.getMapperRegistry();

            clearField(mapperRegistry, "knownMappers");
            clearField(configuration, "loadedResources");
            clearField(configuration, "resultMaps");
            clearField(configuration, "mappedStatements");

            beanAndMapperField.forEach((bean, mapperField) -> {
                try {
                    Class<?> mapperClass = mapperField.getType();
                    mapperRegistry.addMapper(mapperClass);
                    Object mapper = sqlSessionOwn.getMapper(mapperClass);
                    mapperField.set(bean, mapper);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            });
            System.out.println("reload end ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(obj);
        if (value instanceof Map) {
            ((Map<?, ?>) value).clear();
        }
        if (value instanceof Collection) {
            ((Collection<?>) value).clear();
        }
    }
}
