package com.spring.ioc.bean.getBean.createBeanInstance.testRuning;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.javaConfig.ConfigurationBean;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 通过ConfigurationBean配置本质是使用实例工厂实例化bean
 *
 * @Author: wuhao.w
 * @Date: 2020/7/5 11:45
 */
public class InstanceFactoryBuildBeanJavaConfigTest extends BaseTest {


    @Test
    public void testInstanceFactoryBuildBeanJavaConfig() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        annotatedBeanAssembly(beanFactory, ConfigurationBean.class);
        // 获取根据参数名字依赖注入的Bean
        ConstructorNoParamBean constructorNoParamBean = beanFactory.getBean("buildBean", ConstructorNoParamBean.class);
        assertThat(constructorNoParamBean.getMessage()).isNotNull();

        // 获取根据参数名字依赖注入的Bean
        ConstructorHaveParamBean constructorHaveParamBean = beanFactory.getBean("buildBeanHaveParam", ConstructorHaveParamBean.class);
        assertThat(constructorHaveParamBean.getMessage()).isNotNull();

        System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            System.out.println(ReflectionToStringBuilder.toString(beanDefinition, ToStringStyle.MULTI_LINE_STYLE));
        }
    }


    /**
     * 手动构造JavaConfig @Bean注入的bean
     * <p>
     * loadBeanDefinitionsForBeanMethod
     * loadBeanDefinitionsForBeanMethod:179, ConfigurationClassBeanDefinitionReader (org.springframework.context.annotation)
     * loadBeanDefinitionsForConfigurationClass:144, ConfigurationClassBeanDefinitionReader (org.springframework.context.annotation)
     * loadBeanDefinitions:120, ConfigurationClassBeanDefinitionReader (org.springframework.context.annotation)
     * processConfigBeanDefinitions:337, ConfigurationClassPostProcessor (org.springframework.context.annotation)
     */
    @Test
    public void testInstantiatingBeanByConstructor1() throws ClassNotFoundException {

    }
}
