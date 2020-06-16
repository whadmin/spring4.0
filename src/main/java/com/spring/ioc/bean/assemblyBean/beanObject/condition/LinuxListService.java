package com.spring.ioc.bean.assemblyBean.beanObject.condition;

public class LinuxListService implements ListService{

	@Override
	public String showListCmd() {
		return "ls";
	}
}
