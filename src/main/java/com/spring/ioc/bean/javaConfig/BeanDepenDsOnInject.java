package com.spring.ioc.bean.javaConfig;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.javaConig.DependsOn;
import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.DependentBean;
import com.spring.ioc.model.HelloApi;

/**
 * depends-on是指指定Bean初始化及销毁时的顺序， 使用depends-on属性指定的Bean要先初始化完毕后才初始化当前Bean，
 * 由于只有“singleton”Bean能被Spring管理销毁，所以当指定的Bean都是“singleton”时，
 * 使用depends-on属性指定的Bean要在指定的Bean之后销毁。
 * 
 * @author xu.jianguo
 *
 */
public class BeanDepenDsOnInject {

	@Test
	public void testDependOn() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DependsOn.class);
		context.registerShutdownHook();
		DependentBean dependentBean = context.getBean("dependentBean",
				DependentBean.class);
		dependentBean.write("aaa");
	}
}
