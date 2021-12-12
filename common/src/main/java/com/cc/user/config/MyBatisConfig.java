package com.cc.user.config;

import com.cc.mybatis.MyDataSource;
import com.cc.mybatis.MyInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.HashMap;

@Configuration
@MapperScan(basePackages = "com.cc.user.dao")
public class MyBatisConfig {

//    @Autowired
//    DataSource dataSource_master;
//    @Autowired
//    DataSource dataSource_slave;
//    @Autowired
//    DataSource myDataSource;

    @Bean
    public DataSourceTransactionManager transactionManager(MyDataSource myDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(myDataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(MyInterceptor myInterceptor, MyDataSource myDataSource) throws IOException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        bean.setDataSource(myDataSource);
        // bean.setDataSource(dataSource_master);
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(patternResolver.getResources("classpath:mapper/*.xml"));
        bean.setConfigLocation(patternResolver.getResource("classpath:SqlMapConfig.xml"));
        // bean.setConfigurationProperties();
        bean.setTypeAliasesPackage("com.cc.user.domain");
        bean.setPlugins(new Interceptor[]{myInterceptor});

        return bean;
    }

    @Bean
    public MyDataSource myDataSource(DataSource dataSource_master, DataSource dataSource_slave) {
        MyDataSource myDataSource = new MyDataSource();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("master", dataSource_master);
        map.put("slave", dataSource_slave);
        myDataSource.setTargetDataSources(map);

        return myDataSource;
    }

    @Value("${jdbc.driver:xx1}")
    private String driverClass;
    @Value("${jdbc.url:xx2}")
    private String jdbcUrl;
    @Value("${jdbc.url_slave:xx2}")
    private String jdbcUrlSlave;
    @Value("${jdbc.username:xx3}")
    private String jdbcUsername;
    @Value("${jdbc.password:xx4}")
    private String jdbcPassword;

    @Bean
    public DataSource dataSource_master() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        dataSource.setAcquireIncrement(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);

        return dataSource;
    }

    @Bean
    public DataSource dataSource_slave() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrlSlave);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        dataSource.setAcquireIncrement(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);

        return dataSource;
    }
}
