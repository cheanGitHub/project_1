package com.cc.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 创建SqlSessionFactoryBuilder对象
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			// 创建核心配置文件的输入流，用于交给build方法使用
			InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
			// 创建SqlSessionFactory对象
			sqlSessionFactory = builder.build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取SqlSessionFactory
	 * @return
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
}
