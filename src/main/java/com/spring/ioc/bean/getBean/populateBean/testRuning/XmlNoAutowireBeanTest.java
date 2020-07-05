package com.spring.ioc.bean.getBean.populateBean.testRuning;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过xml手动设置bean的对象之间的依赖关系，在构造bean的时候方便注入
 * @author wuhao.w
 *
 */
public class XmlNoAutowireBeanTest extends BaseTest {

	/**
	 * 通过 <property></property> 注入Integer
	 */
	@Test
	public void testStringInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

		IntegerTestBean bean4_1 = beanFactory.getBean("bean4_1", IntegerTestBean.class);
		bean4_1.sayHello();

		IntegerTestBean bean4_2 = beanFactory.getBean("bean4_2", IntegerTestBean.class);
		bean4_2.sayHello();
	}

	/**
	 * 通过 <property></property> 注入String
	 */
	@Test
	public void testIntegerInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

		StringTestBean bean5_1 = beanFactory.getBean("bean5_1", StringTestBean.class);
		bean5_1.sayHello();

		StringTestBean bean5_2 = beanFactory.getBean("bean5_2", StringTestBean.class);
		bean5_2.sayHello();
	}

	/**
	 * 通过 <property></property> 注入Boolean
	 */
	@Test
	public void testBooleanInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

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
	 * 通过 <property></property> 注入java.util.List值
	 */
	@Test
	public void testListInject() {
        // 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");


		ListTestBean listBean = beanFactory.getBean("bean8", ListTestBean.class);
		System.out.println(listBean.getValues().size());
		Assert.assertEquals(3, listBean.getValues().size());

	}

	/**
	 * 通过 <property></property> 注入java.util.Set值
	 */
	@Test
	public void testSetInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

		SetTestBean setBean = beanFactory.getBean("bean9", SetTestBean.class);
		System.out.println(setBean.getValues().size());
		Assert.assertEquals(3, setBean.getValues().size());

	}

	/**
	 * 通过 <property></property> 注入java.util.Collection值
	 */
	@Test
	public void testCollectionInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

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

	/**
	 * 通过 <property></property> 注入数组类型值
	 */
	@Test
	public void testArrayInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

		ArrayTestBean arrayBean = beanFactory.getBean("bean11", ArrayTestBean.class);
		System.out.println(arrayBean.getArray().length);
		System.out.println(arrayBean.getArray2().length);
		System.out.println(arrayBean.getArray2()[0].length);
		System.out.println(arrayBean.getArray2()[1].length);

	}

	/**
	 * 通过 <property></property> 注入Map类型值
	 */
	@Test
	public void testMapInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

		MapTestBean mapBean = beanFactory.getBean("bean12", MapTestBean.class);
		System.out.println(mapBean.getValues().size());
	}

	/**
	 * 通过 <property></property> 注入Properties类型值
	 */
	@Test
	public void testPropertiesInject() {
		// 1.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 2 装配Bean
		xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/noAutowireBean.xml");

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
}
