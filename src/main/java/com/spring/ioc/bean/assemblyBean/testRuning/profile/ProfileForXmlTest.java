package com.spring.ioc.bean.assemblyBean.testRuning.profile;

import com.spring.ioc.bean.assemblyBean.beanObject.profile.*;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Properties;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;


public class ProfileForXmlTest {

    /**
     * 不启动环境配置
     */
    @Test
    public void testProfileDefault() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");
        context.refresh();

        //spring-profile-config.xml 装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //没有开启dev环境 spring-dev-config.xml 没有满足装配条件，devHelloService并没有装配
        assertFalse(context.containsBeanDefinition("devHelloService"));
        //没有开启dev环境 spring-produce-config.xml 没有满足装配条件，produceHelloService并没有装配
        assertFalse(context.containsBeanDefinition("produceHelloService"));

        //<util:properties id="config" ... 会创建一个类型为BeanFactory<Properties>Bean
        Properties config = context.getBean("config", Properties.class);
        assertThat(config.getProperty("name"), equalTo("default"));
        assertThat(config.getProperty("default.name"), equalTo("default"));

        CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl2);
        //CommonServiceImpl 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
        assertThat(((CommonServiceImpl2) commonService).getDynamicName(), equalTo("default"));
        context.close();
    }

    /**
     * 启动 dev环境
     * VM options设置启动参数 -Dspring.profiles.active=dev
     * Program arguments设置 --spring.profiles.active=dev
     */
    @Test
    public void testProfileDev() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");
        //开启dev环境
        context.getEnvironment().setActiveProfiles("dev");
        context.refresh();

        //spring-profile-config.xml 装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //开启dev环境 spring-dev-config.xml 满足装配条件，devHelloService 被装配
        assertTrue(context.containsBeanDefinition("devHelloService"));
        //没有开启dev环境 spring-produce-config.xml 没有满足装配条件，produceHelloService并没有装配
        assertFalse(context.containsBeanDefinition("produceHelloService"));

        //spring-profile-config.xml <util:properties id="config" ... 会创建一个类型为BeanFactory<Properties>Bean
        //spring-dev-config.xml <util:properties id="config" ... 会创建一个类型为BeanFactory<Properties>Bean
        //由于名称相同 spring-dev-config.xml Bean配置会覆盖 spring-profile-config.xm 配置
        Properties config = context.getBean("config", Properties.class);
        assertThat(config.getProperty("name"), equalTo("dev"));
        assertThat(config.getProperty("default.name"), nullValue());

        CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl2);
        //CommonServiceImpl 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
        assertThat(((CommonServiceImpl2) commonService).getDynamicName(), equalTo("dev"));

        HelloService helloService = context.getBean(HelloService.class);
        assertTrue(helloService instanceof DevHelloServiceImpl2);
        //DevHelloServiceImpl2 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
        assertThat(((DevHelloServiceImpl2) helloService).getDynamicName(), equalTo("dev"));
        context.close();
    }


    /**
     * 启动 produce环境
     * VM options设置启动参数 -Dspring.profiles.active=produce
     * Program arguments设置 --spring.profiles.active=produce
     */
    @Test
    public void testProfileProduce() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");
		//开启dev环境
		context.getEnvironment().setActiveProfiles("produce");
		context.refresh();

		//spring-profile-config.xml 装配commonService
		assertTrue(context.containsBeanDefinition("commonService"));
		//没有开启dev环境 spring-dev-config.xml 没有满足装配条件，devHelloService 并没有装配
		assertFalse(context.containsBeanDefinition("devHelloService"));
		//开启dev环境 spring-produce-config.xml 满足装配条件，produceHelloService 被装配
		assertTrue(context.containsBeanDefinition("produceHelloService"));

		//spring-profile-config.xml <util:properties id="config" ... 会创建一个类型为BeanFactory<Properties>Bean
		//spring-produce-config.xml <util:properties id="config" ... 会创建一个类型为BeanFactory<Properties>Bean
		//由于名称相同 spring-produce-config.xml Bean配置会覆盖 spring-profile-config.xm 配置
		Properties config = context.getBean("config", Properties.class);
		assertThat(config.getProperty("name"), equalTo("produce"));
		assertThat(config.getProperty("default.name"), nullValue());

		CommonService commonService = context.getBean(CommonService.class);
		assertTrue(commonService instanceof CommonServiceImpl2);
		//CommonServiceImpl 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
		assertThat(((CommonServiceImpl2) commonService).getDynamicName(), equalTo("produce"));

		HelloService helloService = context.getBean(HelloService.class);
		assertTrue(helloService instanceof ProduceHelloServiceImpl2);
		//ProduceHelloServiceImpl2 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
		assertThat(((ProduceHelloServiceImpl2) helloService).getDynamicName(), equalTo("produce"));
		context.close();
    }

	/**
	 * 启动 多环境，会已第一个环境作为优先配置
	 */
	@Test
	public void testProfileProduceAndDev() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");
		//开启dev环境
		context.getEnvironment().setActiveProfiles("produce","dev");
		context.refresh();

		//spring-profile-config.xml 装配commonService
		assertTrue(context.containsBeanDefinition("commonService"));
		//没有开启dev环境 spring-dev-config.xml 没有满足装配条件，devHelloService 并没有装配
		assertTrue(context.containsBeanDefinition("devHelloService"));
		//开启dev环境 spring-produce-config.xml 满足装配条件，produceHelloService 被装配
		assertTrue(context.containsBeanDefinition("produceHelloService"));

		Properties config = context.getBean("config", Properties.class);
		assertThat(config.getProperty("name"), equalTo("produce"));
		assertThat(config.getProperty("default.name"), nullValue());

		CommonService commonService = context.getBean(CommonService.class);
		assertTrue(commonService instanceof CommonServiceImpl2);
		//CommonServiceImpl 属性通过@value("#{bean名称}")或者@value("#{bean名称.属性名}"获取spring容器中的Bean值（包括bean和bean的属性值）进行注入
		assertThat(((CommonServiceImpl2) commonService).getDynamicName(), equalTo("produce"));
	}

}
