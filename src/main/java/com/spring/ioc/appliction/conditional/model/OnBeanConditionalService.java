package com.spring.ioc.appliction.conditional.model;

import org.springframework.stereotype.Component;


public class OnBeanConditionalService implements ConditionalService{

	@Override
	public String showListCmd() {
		return "OnBean";
	}
}
