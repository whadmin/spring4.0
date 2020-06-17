package com.spring.ioc.appliction.environment;

import com.spring.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.context.support.XmlWebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Environment 本身是一个属性解析器实现PropertyResolver
 *
 * 其内部包含多个PropertySource，每一个PropertySource对应一个外部配置，
 */
@Slf4j
public class EnvironmentTest extends BaseTest {

    @Configuration
    @org.springframework.context.annotation.PropertySource(name = "p1", value = "classpath:propertySource/p1.properties")
    static class ConfigurationAnnotationPropertySource {
    }

    /**
     * 不同类型ApplicationContext实现，以及对应Environment
     */
    @Test
    public void environmentType() {
        //xml装配 非web环境 ApplicationContext实现
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext();
        //java注解配置 非web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
        //非web环境IOC Environment 类型为 StandardEnvironment
        assertThat(context1.getEnvironment() instanceof StandardEnvironment).isTrue();
        assertThat(context2.getEnvironment() instanceof StandardEnvironment).isTrue();

        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigWebApplicationContext webcontext1 = new AnnotationConfigWebApplicationContext();
        //xml装配 web环境 ApplicationContext实现
        XmlWebApplicationContext webcontext2 = new XmlWebApplicationContext();
        //web环境IOC Environment 类型为 StandardServletEnvironment,其是StandardEnvironment子类，对web环境配置进行扩展
        assertThat(webcontext1.getEnvironment() instanceof StandardServletEnvironment).isTrue();
        assertThat(webcontext2.getEnvironment() instanceof StandardServletEnvironment).isTrue();
    }


    /**
     * 测试获取系统属性 System.getProperties()
     *
     * 自定义VM options设置启动参数 -Dkey=testgetSystemProperties
     */
    @Test
    public void testgetSystemProperties() {
        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getSystemProperties().forEach((k, v) -> {
            log.info(String.format("%s:%s", k, v));
        });
        assertThat(context.getEnvironment().getProperty("key")).isEqualTo("testgetSystemProperties");
    }

    /**
     * Environment 本身是一个属性解析器实现PropertyResolver
     *
     * 其内部包含多个PropertySource，每一个PropertySource对应一个外部配置，
     *
     * System.getSystemProperties()属性配置在名称为
     * StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME的PropertySource对象中
     *
     * 自定义VM options设置启动参数 -Dkey=testgetSystemProperties2
     */
    @Test
    public void testgetSystemProperties2() {
        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //通过StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME PropertySource获取所有系统属性
        context.getEnvironment().getPropertySources().forEach(p->{
            if(p.getName().equals(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME)){
                context.getEnvironment().getSystemProperties().forEach((k, v) -> {
                    log.info(String.format("%s:%s", k, p.getProperty(k)));
                });
            }
        });
        assertThat(context.getEnvironment().getProperty("key")).isEqualTo("testgetSystemProperties2");
    }


    /**
     * 获取系统环境 System.getSystemEnvironment()
     */
    @Test
    public void testgetSystemEnvironment() {
        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //通过getSystemEnvironment()获取所有环境配置
        context.getEnvironment().getSystemEnvironment().forEach((k, v) -> {
            log.info(String.format("%s:%s", k, v));
        });
        //通过 environment 直接获取某个环境配置key对应的属性值
        context.getEnvironment().getProperty("JAVA_HOME");
    }


    /**
     * Environment 本身是一个属性解析器实现PropertyResolver
     *
     * 其内部包含多个PropertySource，每一个PropertySource对应一个外部配置，
     *
     * System.getSystemEnvironment()属性配置在名称为
     * StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME的PropertySource对象中
     */
    @Test
    public void testgetSystemEnvironment2() {
        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //通过StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME PropertySource获取所有环境配置
        context.getEnvironment().getPropertySources().forEach(p->{
            if(p.getName().equals(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)){
                context.getEnvironment().getSystemEnvironment().forEach((k, v) -> {
                    log.info(String.format("%s:%s", k, p.getProperty(k)));
                });
            }
        });
        //通过 environment 直接获取某个环境配置key对应的属性值
        context.getEnvironment().getProperty("JAVA_HOME");
    }

    /**
     * 添加SimpleCommandLinePropertySource，获取启动参数--key=testgetProgramarguments
     */
    public static void main(String[] args) {
        //java注解配置 web环境 ApplicationContext实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        CommandLinePropertySource<?> ps =
                new SimpleCommandLinePropertySource(args);
        context.getEnvironment().getPropertySources().addFirst(ps);
        //通过 environment 直接获取某个环境配置key对应的属性值
        assertThat(context.getEnvironment().getProperty("key")).isEqualTo("testgetProgramarguments");
    }


    @Test
    public void testXmlConfigProperties(){
        //xml装配 非web环境 ApplicationContext实现
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:ioc/bean/register/profile/spring-profile-config.xml");

        //读取 <util:properties id="config" location="classpath:profile/common.properties"/> 配置
        assertThat(context.getEnvironment().getProperty("name")).isEqualTo("default");
        assertThat(context.getEnvironment().getProperty("default.name")).isEqualTo("default");
    }






}
