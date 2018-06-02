package com.mybatis;

import org.apache.ibatis.session.SqlSession;
import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import com.mybatis.utils.MybatisUtil;

/**
 * MyBatisExample description create by lxj 2018/4/26
 **/
public class MyBatisExample {
	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			User user = mapper.getOne(1L);
			System.out.println("user==>" + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
}