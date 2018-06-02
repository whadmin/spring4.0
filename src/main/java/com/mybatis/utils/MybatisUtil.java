package com.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * MybatisUtil description TODO create by lxj 2018/4/26
 **/
public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory = null;

	public static SqlSessionFactory getSqlSessionFactory() {
		InputStream inputStream = null;
		if (sqlSessionFactory == null) {
			try {
				String resource = "mybatis/mybatis.cfg.xml";
				sqlSessionFactory = new SqlSessionFactoryBuilder()
						.build(Resources.getResourceAsReader(resource));
				//sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
				return sqlSessionFactory;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return sqlSessionFactory;
	}
}
