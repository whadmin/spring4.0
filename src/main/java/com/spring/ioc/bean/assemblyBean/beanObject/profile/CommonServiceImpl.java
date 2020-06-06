package com.spring.ioc.bean.assemblyBean.beanObject.profile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CommonServiceImpl implements CommonService {

	//@value获取spring容器中environment属性值完成注入
	//通过@value("${key}")
	@Value("${name}")
	public String dynamicName;

	//@value获取spring容器中environment属性值完成注入
	//通过@value("${key}")
	@Value("${default.name}")
	public String defaultName;

	@Override
	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				dynamicName);
	}
}
