package com.spring.ioc.bean.assemblyBean.beanObject.condition;

public class OnClassConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnClass";
	}
}
