package com.spring.ioc.appliction.conditional.model;

public class LinuxListService implements ListService{

	@Override
	public String showListCmd() {
		return "ls";
	}
}
