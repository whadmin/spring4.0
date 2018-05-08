package com.spring.ioc.appliction.conditional.model;

public class OnClassConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnClass";
	}
}
