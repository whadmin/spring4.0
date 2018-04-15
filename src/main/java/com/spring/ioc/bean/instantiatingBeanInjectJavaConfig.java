package com.spring.ioc.bean;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.ArrayTestBean;
import com.spring.ioc.model.BooleanTestBean;
import com.spring.ioc.model.CollectionTestBean;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.IdRefTestBean;
import com.spring.ioc.model.ListTestBean;
import com.spring.ioc.model.MapTestBean;
import com.spring.ioc.model.PropertiesTestBean;
import com.spring.ioc.model.SetTestBean;

public class instantiatingBeanInjectJavaConfig {
	
	@Test
	public void testSetterDependencyInject() {
		// 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean5", HelloApi.class);
        bean1.sayHello();

        HelloApi bean2 = context.getBean("bean5_ref", HelloApi.class);
        bean2.sayHello();
        
	}

	// =====================依赖注入扩展部分=================================

	// 注入Boolean值
	@Test
	public void testBooleanInject() {

		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");

		BooleanTestBean bean1 = beanFactory.getBean("bean6_1", BooleanTestBean.class);
		System.out.println(bean1.isSuccess());

		BooleanTestBean bean2 = beanFactory.getBean("bean6_2", BooleanTestBean.class);
		System.out.println(bean2.isSuccess());

		BooleanTestBean bean3 = beanFactory.getBean("bean6_3", BooleanTestBean.class);
		System.out.println(bean3.isSuccess());

		BooleanTestBean bean4 = beanFactory.getBean("bean6_4", BooleanTestBean.class);
		System.out.println(bean4.isSuccess());
	}

	/**
	 * 注入Bean ID 用于注入Bean的ID，ID是一个常量不是引用，且类似于注入常量，但提供错误验证功能
	 * 第二种方式（<idref bean="bean1"/>） 可以在容器初始化时校验被引用的Bean是否存在，如果不存在将抛出异常，
	 * 而第一种方式（<idref local="bean2"/>）
	 * 只有在Bean实际使用时才能发现传入的Bean的ID是否正确，可能发生不可预料的错误。因此如果想注入Bean的ID，推荐使用第二种方式。
	 */
	@Test
	public void testIdRefInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");

		IdRefTestBean bean1 = beanFactory.getBean("bean7_1", IdRefTestBean.class);
		System.out.println(bean1.getId());

		IdRefTestBean bean2 = beanFactory.getBean("bean7_2", IdRefTestBean.class);
		System.out.println(bean2.getId());
	}

	// 注入java.util.List值
	@Test
	public void testListInject() {

		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		ListTestBean listBean = beanFactory.getBean("bean8", ListTestBean.class);
		System.out.println(listBean.getValues().size());
		Assert.assertEquals(3, listBean.getValues().size());

	}

	// 注入java.util.Set值
	@Test
	public void testSetInject() {

		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		SetTestBean setBean = beanFactory.getBean("bean9", SetTestBean.class);
		System.out.println(setBean.getValues().size());
		Assert.assertEquals(3, setBean.getValues().size());

	}

	// 注入java.util.Collection值
	@Test
	public void testCollectionInject() {

		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");

		// 本质是List类型
		CollectionTestBean collectionBean1 = beanFactory.getBean("bean10_1", CollectionTestBean.class);
		System.out.println("Type:" + collectionBean1.getValues().getClass().getName());
		System.out.println(collectionBean1.getValues().size());
		Assert.assertEquals(3, collectionBean1.getValues().size());

		// 本质是Set类型
		CollectionTestBean collectionBean2 = beanFactory.getBean("bean10_2", CollectionTestBean.class);
		System.out.println("Type:" + collectionBean2.getValues().getClass().getName());
		System.out.println(collectionBean2.getValues().size());
		Assert.assertEquals(3, collectionBean2.getValues().size());

	}

	// 注入数组类型值
	@Test
	public void testArrayInject() {

		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		ArrayTestBean arrayBean = beanFactory.getBean("bean11", ArrayTestBean.class);
		System.out.println(arrayBean.getArray().length);
		System.out.println(arrayBean.getArray2().length);
		System.out.println(arrayBean.getArray2()[0].length);
		System.out.println(arrayBean.getArray2()[1].length);

	}

	// 注入Map类型值
	@Test
	public void testMapInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		MapTestBean mapBean = beanFactory.getBean("bean12", MapTestBean.class);
		System.out.println(mapBean.getValues().size());
	}

	// 注入Properties类型值
	@Test
	public void testPropertiesInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		PropertiesTestBean propertiesBean = beanFactory.getBean("bean13_1", PropertiesTestBean.class);
		System.out.println(propertiesBean.getValues().size());
		System.out.println(propertiesBean.getValues().containsValue("22"));

		PropertiesTestBean propertiesBean2 = beanFactory.getBean("bean13_2", PropertiesTestBean.class);
		System.out.println(propertiesBean2.getValues().size());
		System.out.println(propertiesBean2.getValues().containsKey("1"));
		System.out.println(propertiesBean2.getValues().containsKey("2"));
		System.out.println(propertiesBean2.getValues().containsKey("3"));
		System.out.println(propertiesBean2.getValues().containsKey("4"));
		System.out.println(propertiesBean2.getValues().containsKey("5"));
		System.out.println(propertiesBean2.getValues().containsValue("11"));
	}

	// 注入其他Bean类型值 通过ref bean
	@Test
	public void testBeanInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		// 通过setter方式注入
		HelloApi bean2 = beanFactory.getBean("bean14_1", HelloApi.class);
		bean2.sayHello();
		// 通过构造器方式注入
		HelloApi bean1 = beanFactory.getBean("bean14_2", HelloApi.class);
		bean1.sayHello();

	}

	// 注入其他Bean类型值 通过ref local/parent
	@Test
	public void testLocalAndparentBeanInject() {
		// 初始化父容器
		ApplicationContext parentBeanContext = new ClassPathXmlApplicationContext("ioc/bean/parentBeanInject.xml");
		// 初始化当前容器
		ApplicationContext beanContext = new ClassPathXmlApplicationContext(
				new String[] { "ioc/instantiatingBeanInject.xml" }, parentBeanContext);
		// 该Bean引用local bean
		HelloApi bean1 = beanContext.getBean("bean15_1", HelloApi.class);
		bean1.sayHello();
		// 该Bean引用parent bean
		HelloApi bean2 = beanContext.getBean("bean15_2", HelloApi.class);
		bean2.sayHello();
	}

	// 注入其他Bean类型值 通过inner bean
	@Test
	public void testInnerBeanInject() {
		ApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");

		HelloApi bean = context.getBean("bean17", HelloApi.class);
		bean.sayHello();

	}

	@Test
	public void testPNamespaceBeanInject() {
		ApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInject.xml");
		Assert.assertEquals("value", context.getBean("idrefBean1", IdRefTestBean.class).getId());
		Assert.assertEquals("test", context.getBean("idrefBean2", IdRefTestBean.class).getId());

	}

}
