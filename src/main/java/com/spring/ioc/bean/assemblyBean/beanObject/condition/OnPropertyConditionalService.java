package com.spring.ioc.bean.assemblyBean.beanObject.condition;

public class OnPropertyConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnProperty";
	}
}
