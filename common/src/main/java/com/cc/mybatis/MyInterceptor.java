package com.cc.mybatis;

import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Intercepts(
        {
                // @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})
                @Signature(type = Executor.class, method = "update", args = {
                        MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {
                        MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class})
        }
)
@Component
public class MyInterceptor implements Interceptor {
    static {
        System.out.println("AAAA--------------------- MyInterceptor");
    }

    @Autowired
    Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    //1.不间断空格\u00A0,主要用在office中,让一个单词在结尾处不会换行显示,快捷键ctrl+shift+space ;
    //2.半角空格(英文符号)\u0020,代码中常用的;
    //3.全角空格(中文符号)\u3000,中文文章中使用;
    private static final String REGEX = ".*insert .*|.*delete .*|.*update .*|.*replace .*";

//    public static void main(String[] args) {
//        System.out.println("insert into aa".matches(".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*"));
//        System.out.println("insert into aa".matches(".*insert .*|.*delete .*|.*update .*"));
//    }

    private static final Map<String, String> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //常量切换 生产 测试库
        Boolean master = environment.getProperty("db.source", Boolean.class);
        if (master != null && master) {
            MyDataSource.setMaster();
            return invocation.proceed();
        } else if (master != null) {
            MyDataSource.setSlave();
            return invocation.proceed();
        }

        //它首先查看当前是否存在事务管理上下文，并尝试从事务管理上下文获取连接，如果获取失败，直接从数据源中获取连接。
        //在获取连接后，如果当前拥有事务上下文，则将连接绑定到事务上下文中。（此处直接继续下一过程）
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        // TransactionSynchronizationManager.getResource();
        CachingExecutor target = (CachingExecutor) invocation.getTarget();
//        Field field = target.delegate.getTransaction().getClass().getDeclaredField("dataSource");
//        field.setAccessible(true);
//        Object dataSource = field.get(target);
//        System.out.println(dataSource);
//        System.out.println(dataSource.getClass());


        if (!synchronizationActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];

            String key = cacheMap.get(ms.getId());
            if (key == null) {
                //读方法
                if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                    //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
                    if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                        key = MyDataSource.MASTER;
                    } else {
                        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                        if (sql.matches(REGEX)) {
                            key = MyDataSource.MASTER;
                        } else {
                            key = MyDataSource.SLAVE;
                        }
                    }
                } else {
                    key = MyDataSource.MASTER;
                }

                System.out.println(String.format("设置 ms id = [%s], dbkey = [%s], SqlCommandType = [%s]", ms.getId(), key, ms.getSqlCommandType().name()));
                cacheMap.put(ms.getId(), key);
            }

            MyDataSource.setDbKey(key);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
//        return target;
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
