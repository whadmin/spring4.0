package com.spring.ioc.core.common.beanWrapper;


import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.spring.ioc.core.common.model.UserModel;

public class BeanWrapperTest {
	
	@Test
	public void test() {
		UserModel obj=new UserModel();
		BeanWrapper beanWrapper = new BeanWrapperImpl(obj);  
	    beanWrapper.setPropertyValue("username","hello");  
	    System.out.println(beanWrapper.getPropertyValue("username"));  
	}

}
