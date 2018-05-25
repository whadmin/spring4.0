package com.spring.ioc.javaConig.propertySource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
public class ValueOtherConfig {
	
	//设置常量属性
	@Value("wuhao") 
    private String name;
		
	//设置env中属性
	@Value("#{systemProperties['os.name']}") //②
	private String osName;
	
	//设置bean中的属性
	@Value("#{propertySourceService.another}") //④
	private String fromAnother;
	
	//spring自带随机数组件
	@Value("#{ T(java.lang.Math).random() * 100.0 }") //③
    private double randomNumber;

	//spring自带资源文件属性
	@Value("classpath:env/test.txt") //⑤
	private Resource testFile;

	//spring自带资源文件属性
	@Value("http://www.baidu.com") //⑥
	private Resource testUrl;
	
	public void outputResource() {
		try {
			System.out.println(name);
			System.out.println(osName);
			System.out.println(randomNumber);
			System.out.println(fromAnother);
			System.out.println(IOUtils.toString(testFile.getInputStream()));
			System.out.println(IOUtils.toString(testUrl.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
