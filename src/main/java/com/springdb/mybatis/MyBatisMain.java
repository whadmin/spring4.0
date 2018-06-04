package com.springdb.mybatis;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springdb.mybatis.entity.SysUser;
import com.springdb.mybatis.enums.TypeEnum;
import com.springdb.mybatis.mapper.SysUserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mybatis/spring-mybatis.xml" })
// @ContextConfiguration(class = { })
public class MyBatisMain {

	@Autowired
	SysUserMapper sysUserMapper;

	@Test
	public void selectById() {
		System.out.println(sysUserMapper
				.selectByPrimaryKey(1002779174048440321L));
	}

	@Test
	public void insert() {
		SysUser user = new SysUser();
		user.setAge(1);
		user.setName("wuhao.w");
		user.setType(TypeEnum.DISABLED);
		user.setId(12L);
		user.setCtime(new Date());
		user.setUtime(new Date());

		sysUserMapper.insertSelective(user);
		System.out.println(user);

	}

	@Test
	public void updateById() {
		SysUser user = new SysUser();
		user.setAge(2);
		user.setName("wuhao.w");
		user.setId(1002779174048440321L);
		user.setVersion((byte) 1);
		sysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Test
	public void delete() {
		sysUserMapper.deleteByPrimaryKey(1002779174048440321L);
	}

}
