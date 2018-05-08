package com.spring.ioc.appliction.conditional.model;

public class OnPropertyConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnProperty";
	}
}
