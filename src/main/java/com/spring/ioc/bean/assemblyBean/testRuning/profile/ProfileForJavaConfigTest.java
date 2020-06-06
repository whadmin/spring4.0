package com.spring.ioc.bean.assemblyBean.testRuning.profile;

import com.spring.ioc.bean.assemblyBean.beanObject.profile.*;
import com.spring.ioc.bean.assemblyBean.javaConfig.profile.ProfileConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ProfileForJavaConfigTest {

    /**
     * 不启动环境配置
     */
    @Test
    public void testProfileDefault() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ProfileConfig.class);
        context.refresh();
        //ProfileConfig 注解类装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //没有开启dev环境 DevProFileConfig 没有满足装配条件，devHelloService并没有装配
        assertFalse(context.containsBeanDefinition("devHelloService"));
        //没有开启dev环境 ProduceProFileConfig 没有满足装配条件，produceHelloService并没有装配
        assertFalse(context.containsBeanDefinition("produceHelloService"));

        //ProfileConfig中@PropertySource会将属性设置到environment
        assertThat(context.getEnvironment().getProperty("name"), equalTo("default"));
        assertThat(context.getEnvironment().getProperty("default.name"), equalTo("default"));

        CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl);
        //CommonServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
        assertThat(((CommonServiceImpl) commonService).getDefaultName(), equalTo("default"));
        assertThat(((CommonServiceImpl) commonService).getDynamicName(), equalTo("default"));
        context.close();
    }

    /**
     * 启动 dev环境
     * VM options设置启动参数 -Dspring.profiles.active=dev
     * Program arguments设置 --spring.profiles.active=dev
     */
    @Test
    public void testProfileDev() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //开启dev环境
        context.getEnvironment().setActiveProfiles("dev");
        context.register(ProfileConfig.class);
        context.refresh();

        //ProfileConfig 注解类装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //开启dev环境 DevProFileConfig 满足装配条件，devHelloService被装配
        assertTrue(context.containsBeanDefinition("devHelloService"));
        //没有开启dev环境 ProduceProFileConfig 没有满足装配条件，produceHelloService并没有装配
        assertFalse(context.containsBeanDefinition("produceHelloService"));

        //ProfileConfig中@PropertySource会将属性设置到environment
        //DevProFileConfig中@PropertySource会将属性设置到environment 被标记为dev环境
        //ProfileConfig中@PropertySource和DevProFileConfig中@PropertySource由于存在相同属性key "name"，被标记为dev环境作为该属性key的值
        assertThat(context.getEnvironment().getProperty("name"), equalTo("dev"));
        assertThat(context.getEnvironment().getProperty("default.name"), equalTo("default"));

        CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl);
        //CommonServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
        assertThat(((CommonServiceImpl) commonService).getDefaultName(), equalTo("default"));
        assertThat(((CommonServiceImpl) commonService).getDynamicName(), equalTo("dev"));

		HelloService helloService = context.getBean(HelloService.class);
		assertTrue(helloService instanceof DevHelloServiceImpl);
        //DevHelloServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
		assertThat(((DevHelloServiceImpl) helloService).getDynamicName(), equalTo("dev"));
        context.close();
    }

    /**
     * 启动 produce环境
     * VM options设置启动参数 -Dspring.profiles.active=produce
     * Program arguments设置 --spring.profiles.active=produce
     */
	@Test
	public void testProfileProduce() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //开启produce环境
		context.getEnvironment().setActiveProfiles("produce");
		context.register(ProfileConfig.class);
		context.refresh();

        //ProfileConfig 注解类装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //没有开启dev环境 DevProFileConfig 没有满足装配条件，devHelloService并没有被装配
        assertFalse(context.containsBeanDefinition("devHelloService"));
        //开启dev环境 ProduceProFileConfig 满足装配条件，produceHelloService被装配
        assertTrue(context.containsBeanDefinition("produceHelloService"));

        //ProfileConfig中@PropertySource会将属性设置到environment
        //ProduceProFileConfig中@PropertySource会将属性设置到environment 被标记为produce环境
        //ProfileConfig中@PropertySource和ProduceProFileConfig中@PropertySource存在相同属性key "name"，被标记为produce环境作为该属性key的值
        assertThat(context.getEnvironment().getProperty("name"), equalTo("produce"));
        assertThat(context.getEnvironment().getProperty("default.name"), equalTo("default"));

		CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl);
        //CommonServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
        assertThat(((CommonServiceImpl) commonService).getDefaultName(), equalTo("default"));
        assertThat(((CommonServiceImpl) commonService).getDynamicName(), equalTo("produce"));

        HelloService helloService = context.getBean(HelloService.class);
        assertTrue(helloService instanceof ProduceHelloServiceImpl);
        //ProduceHelloServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
        assertThat(((ProduceHelloServiceImpl) helloService).getDynamicName(), equalTo("produce"));
		context.close();
	}


    /**
     * 启动 多环境，会已第一个环境作为优先配置
     */
    @Test
    public void testProfileProduceAndDev() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //开启produce，dev环境
        context.getEnvironment().setActiveProfiles("produce","dev");
        context.register(ProfileConfig.class);
        context.refresh();

        //ProfileConfig 注解类装配commonService
        assertTrue(context.containsBeanDefinition("commonService"));
        //开启dev环境 DevProFileConfig 满足装配条件，devHelloService被装配
        assertTrue(context.containsBeanDefinition("devHelloService"));
        //开启dev环境 ProduceProFileConfig 满足装配条件，produceHelloService被装配
        assertTrue(context.containsBeanDefinition("produceHelloService"));

        //ProfileConfig中@PropertySource会将属性设置到environment
        //ProduceProFileConfig中@PropertySource会将属性设置到environment 被标记为produce环境
        //DevProFileConfig中@PropertySource会将属性设置到environment 被标记为dev环境
        //ProfileConfig中@PropertySource,ProduceProFileConfig中@PropertySource,DevProFileConfig中@PropertySource存在相同属性key "name"，
        //被标记为produce环境作为该属性key的值,开启顺序
        assertThat(context.getEnvironment().getProperty("name"), equalTo("produce"));
        assertThat(context.getEnvironment().getProperty("default.name"), equalTo("default"));

        CommonService commonService = context.getBean(CommonService.class);
        assertTrue(commonService instanceof CommonServiceImpl);
        //CommonServiceImpl 属性通过@value("${key}")获取spring容器中environment属性值完成注入
        assertThat(((CommonServiceImpl) commonService).getDefaultName(), equalTo("default"));
        assertThat(((CommonServiceImpl) commonService).getDynamicName(), equalTo("produce"));
        context.close();
    }
}
