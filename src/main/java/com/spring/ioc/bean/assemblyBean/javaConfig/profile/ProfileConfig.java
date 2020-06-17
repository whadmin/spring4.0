package com.spring.ioc.bean.assemblyBean.javaConfig.profile;

import com.spring.ioc.bean.assemblyBean.beanObject.profile.CommonService;
import com.spring.ioc.bean.assemblyBean.beanObject.profile.CommonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ DevProFileConfig.class, ProduceProFileConfig.class })
@PropertySource("classpath:ioc/bean/register/profile/common.properties")
public class ProfileConfig {

	@Bean
	public CommonService commonService() {
		return new CommonServiceImpl();
	}
}
