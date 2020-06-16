package com.spring.ioc.bean.assemblyBean.beanObject.condition;


public class WindowsListService implements ListService {

	@Override
	public String showListCmd() {
		return "dir";
	}
}
