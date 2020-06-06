package com.spring.ioc.bean.assemblyBean.beanObject.profile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CommonServiceImpl2 implements CommonService {

    //@value获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
	//通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"
	@Value("#{config.name}")
	public String dynamicName;


	@Override
	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				dynamicName);
	}
}
