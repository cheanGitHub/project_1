package com.cc.spi;

import com.sun.media.sound.SoftTuning;
import sun.misc.Launcher;

import javax.xml.ws.Service;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ServiceLoader;

public class SpiTests {

    public static void main(String[] args) throws Exception {
        System.out.println("SpiTests.class.getClassLoader() SpiTests 1 = " + SpiTests.class.getClassLoader());
        System.out.println("Thread.currentThread().getContextClassLoader() SpiTests 1 = " + Thread.currentThread().getContextClassLoader());


        // https://blog.csdn.net/lemon89/article/details/79189475

        //注:从jdbc4.0之后无需这个操作,spi机制会自动找到相关的驱动实现
        //Class.forName(driver);
        //1.getConnection()方法，连接MySQL数据库。有可能注册了多个Driver，这里通过遍历成功连接后返回。
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
//        //2.创建statement类对象，用来执行SQL语句！！
//        Statement statement = con.createStatement();
//        //3.ResultSet类，用来存放获取的结果集！！
//        ResultSet rs = statement.executeQuery("select * from t_user");
//        System.out.println(rs);

        ClassLoader loader = new MyClassLoader(); // SpiTests.class.getClassLoader().getParent();
        System.out.println("loader = " + loader);
        Thread.currentThread().setContextClassLoader(loader);
        ServiceLoader<MySpiInterface> serviceLoader = ServiceLoader.load(MySpiInterface.class);
//        serviceLoader.forEach(v -> {
//            //System.out.println(v);
//        });

        System.out.println("MySpiInterface.class.getClassLoader() = " + MySpiInterface.class.getClassLoader());
        System.out.println("ServiceLoader.class.getClassLoader() = " + ServiceLoader.class.getClassLoader());
    }

    static class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if (inputStream == null) {
                    return super.loadClass(name);
                }

                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}


