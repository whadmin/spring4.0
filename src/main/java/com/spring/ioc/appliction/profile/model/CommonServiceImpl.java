package com.spring.ioc.appliction.profile.model;

import org.springframework.beans.factory.annotation.Value;

public class CommonServiceImpl implements CommonService {

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	@Value("${name}")
	public String commonName;


	@Override
	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				commonName);
	}

}
