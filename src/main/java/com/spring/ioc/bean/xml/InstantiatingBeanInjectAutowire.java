package com.spring.ioc.bean.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApiDataSource;
import com.spring.ioc.model.ListBean;
import com.spring.ioc.model.qualifier.DataBase;
import com.spring.ioc.model.qualifier.DataSourceType;
import com.spring.ioc.model.qualifier.Mysql;

import junit.framework.Assert;

/**
 * Spring 可以通过通过XMl 设置初始化属性,和设置注入属性 当我们的配置中存在大量ref注入，能否让属性自动按名称或类型自动注入
 * 
 *
 */
public class InstantiatingBeanInjectAutowire {

	/**
	 * 
	 <bean id="helloApi" class="com.spring.ioc.model.HelloImpl" primary="true"
	 * />
	 * 
	 * <bean id="message" class="java.lang.String"> <constructor-arg index="0"
	 * value="Hello World!" /> </bean> <!-- 通过属性名称自动注入 --> <bean
	 * id="bean_byName" class="com.spring.ioc.model.HelloApiDecorator"
	 * autowire="byName" />
	 * 
	 * HelloApiDecorator中所有属性只要在bean容器中存在相同名称且类型匹配都会注入到其属性中 helloApi，message
	 * 通过名称自动注入
	 */
	@Test
	public void testAutowireByName() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		HelloApiDataSource helloApi = context.getBean("bean_byName",
				HelloApiDataSource.class);
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();
	}

	/**
	 * 
	 <!-- primary表示该类型的bean注入其他对象的属性过程中存在竞争者(多个类型的bean匹配)优先 --> <bean
	 * id="helloApi" class="com.spring.ioc.model.HelloImpl" primary="true" />
	 * 
	 * <!-- autowire-candidate表示该类型的bean注入其他对象的属性过程中存在竞争者(多个类型的bean匹配)放弃 -->
	 * <bean class="com.spring.ioc.model.HelloImpl" autowire-candidate="false"
	 * /> <!-- 通过属性名称自动注入 --> <bean id="bean_byName"
	 * class="com.spring.ioc.model.HelloApiDecorator" autowire="byName" />
	 * 
	 * <bean id="mysqlDataSourceBean"
	 * class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	 * <qualifier value="mysqlDataSource" /> <qualifier
	 * type="com.spring.ioc.model.qualifier.Mysql" /> <qualifier
	 * type="com.spring.ioc.model.qualifier.DataSourceType"> <attribute key="ip"
	 * value="localhost" /> <attribute key="database" value="MYSQL" />
	 * </qualifier> </bean>
	 * 
	 * <bean id="oracleDataSourceBean"
	 * class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	 * <qualifier value="oracleDataSource" /> <qualifier
	 * type="com.spring.ioc.model.qualifier.Oracle" /> <qualifier
	 * type="com.spring.ioc.model.qualifier.DataSourceType"> <attribute key="ip"
	 * value="localhost" /> <attribute key="database" value="ORACLE" />
	 * </qualifier> </bean>
	 * 
	 * //使用qualifier在属性匹配多个对象时选择注入 public void setDataSource(
	 * 
	 * @Qualifier("mysqlDataSource") DataSource dataSource) { this.dataSource =
	 *                               dataSource; }
	 * 
	 *                               //使用qualifier在属性匹配多个对象时选择注入 public void
	 *                               setDataSource1(@Mysql DataSource
	 *                               dataSource1) { this.dataSource1 =
	 *                               dataSource1; }
	 * 
	 *                               //使用qualifier在属性匹配多个对象时选择注入 public void
	 *                               setDataSource2(
	 * @DataSourceType(ip = "localhost", database = DataBase.MYSQL) DataSource
	 *                    dataSource2) { this.dataSource2 = dataSource2; }
	 * 
	 *                    HelloApiDecorator中所有属性只要在bean容器中存在相同类型的bean会注入到其属性中,
	 *                    如遇到竞争通过qualifier,primary,autowire-candidate赛选
	 */
	@Test
	public void testAutowireByType() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		HelloApiDataSource helloApi = context.getBean("bean_byType",
				HelloApiDataSource.class);
		System.out.println(ReflectionToStringBuilder.toString(context
				.getBeanFactory().getBeanDefinition("bean_byType"),
				ToStringStyle.MULTI_LINE_STYLE));

		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		helloApi.getHelloApi().sayHello();
	}

	/**
	 * 针对bean初始化参数的按类型注入
	 */
	@Test
	public void testAutowireByConstructor() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		HelloApiDataSource helloApi = context.getBean("bean_constructor",
				HelloApiDataSource.class);
		helloApi.getHelloApi().sayHello();
	}

	/**
	 * 当注入的属性类型为List 注入其泛型类型的多个对象 当注入的属性为ArrayList，注入的List类型的对象
	 */
	@Test
	public void testAutowireByType2() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		ListBean listBean = context.getBean("listBean", ListBean.class);
		// 对于集合接口根据类型注入将自动查找所有匹配的注入
		Assert.assertTrue(listBean.getList1().size() > 0);
		// 对于集合具体类型将根据具体类型进行注入，而不是选择所有泛型类型信息匹配的Bean
		Assert.assertTrue(listBean.getList2() == null);
	}
	
	
	@Test
	public void testAutowireByAutowired() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		HelloApiDataSource helloApi = context.getBean("bean_Autowired",
				HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();
	}
	
	


}
