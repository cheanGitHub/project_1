package cc;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JCBCTest {

        public static void main(String[] args) throws ClassNotFoundException, SQLException {
            //连接数据库
            Class.forName("com.mysql.jdbc.Driver");
            // String url = "jdbc:mysql://127.0.0.1:3306/mybatis_test";
            String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
            String user = "root";
            String password = "123456";

            //建立数据库连接
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "select * from t_user";
            Statement stmt = conn.createStatement();    //创建一个statement对象
            ResultSet rs = stmt.executeQuery(sql);        //执行查询

            int id;
            String username;

            while (rs.next()) {        //遍历结果集
                id = rs.getInt("id");
                username = rs.getString("user_name");
                System.out.println(id + "\t" + username + "\t");
            }
        }

    }
