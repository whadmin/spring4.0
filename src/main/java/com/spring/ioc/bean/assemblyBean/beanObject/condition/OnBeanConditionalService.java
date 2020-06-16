package com.spring.ioc.bean.assemblyBean.beanObject.condition;


public class OnBeanConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnBean";
	}
}
