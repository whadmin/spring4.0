package com.springdb.mybatisplus;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mybatis.enums.TypeEnum;
import com.springdb.mybatisplus.entity.SysUser;
import com.springdb.mybatisplus.service.MPSysUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mybatis/plus/spring-mybatisPlus.xml" })
public class myBatisPlusMain {

	@Autowired
	MPSysUserService userService;


	@Test
	public void selectById() {
		System.out.println(userService.selectById(1002779174048440321L));
	}

	@Test
	public void insert() {
		SysUser user = new SysUser();
		user.setAge(1);
		user.setName("wuhao.w");
		user.setType(1);

		userService.insertAllColumn(user);
		System.out.println(user);

	}

	@Test
	public void updateById() {
		SysUser user = new SysUser();
		user.setAge(2);
		user.setName("wuhao.w");
		user.setId(1002779174048440321L);
		user.setVersion(1);
		userService.updateById(user);
	}

	@Test
	public void delete() {
		userService.deleteById(1002779174048440321L);
	}
	
	
	@Test
	public void testTSQL1() {
	    /*
	     * 实体带查询使用方法  输出看结果
	     */
	    EntityWrapper<SysUser> ew = new EntityWrapper<SysUser>();
	    ew.setEntity(new SysUser());
	    ew.where("user_name={0}", "'zhangsan'").and("id=1")
	            .orNew("user_status={0}", "0").or("status=1")
	            .notLike("user_nickname", "notvalue")
	            .andNew("new=xx").like("hhh", "ddd")
	            .andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
	            .groupBy("x1").groupBy("x2,x3")
	            .having("x1=11").having("x3=433")
	            .orderBy("dd").orderBy("d1,d2");
	    System.out.println(ew.getSqlSegment());
	}
	
	@Test
	public void testTSQL2() {
		System.out.println(Condition.create().setSqlSelect("sum(quantity)")
				.isNull("order_id").eq("user_id", 1).eq("type", 1)
				.in("status", new Integer[] { 0, 1 }).eq("product_id", 1)
				.between("created_time", "2018-06-02", "2018-06-03")
				.eq("weal", 1).getSqlSegment());
	}

	@Test
	public void query1() {
		/*
		 * 实体带查询使用方法 输出看结果
		 */
		SysUser user = new SysUser();
		user.setName("wuhao.w");
		user.setId(1002779174048440321L);

		List<SysUser> list = userService.selectList(new EntityWrapper<SysUser>(user).setSqlSelect("name"));
		System.out.println("****************************************");
		for (SysUser u : list) {
			System.out.println(u.getType());
			Assert.assertNotNull("TypeEnum should not null", u.getType());
		}
		System.out.println("****************************************");
	}
	
	@Test
	public void query2() {
		EntityWrapper<SysUser> ew = new EntityWrapper<SysUser>();
	    ew.where("name={0}", "wuhao.w").and("id=1002778981244678146");

		List<SysUser> list = userService.selectList(ew);
		System.out.println("****************************************");
		for (SysUser u : list) {
			System.out.println(u.getType());
			Assert.assertNotNull("TypeEnum should not null", u.getType());
		}
		System.out.println("****************************************");
	}
	
	@Test
	public void query3() {
		List<SysUser> list = userService.selectList(Condition.create().eq("name", "wuhao.w").eq("id", "1002778981244678146"));
		System.out.println("****************************************");
		for (SysUser u : list) {
			System.out.println(u.getType());
			Assert.assertNotNull("TypeEnum should not null", u.getType());
		}
		System.out.println("****************************************");
	}

}
