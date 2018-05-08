package com.spring.ioc.appliction.conditional.model;


public class WindowsListService implements ListService {

	@Override
	public String showListCmd() {
		return "dir";
	}
}
