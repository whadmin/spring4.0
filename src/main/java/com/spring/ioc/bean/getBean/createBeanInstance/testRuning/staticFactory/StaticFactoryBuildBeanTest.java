package com.spring.ioc.bean.getBean.createBeanInstance.testRuning.staticFactory;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 用静态工厂创建Bean实例
 * <p>
 * 对应设计模式中 静态工厂方法（Static Factory Method）模式（一个工厂只能生产一个类型Bean实例）
 *
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:52
 */
@Slf4j
public class StaticFactoryBuildBeanTest extends BaseTest {

    /**
     * 使用静态工厂实例化bean
     */
    @Test
    public void testStaticFactoryBuildBean() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/staticFactoryBuildBean.xml");

        // 3 获取静态工厂Bean,其本质会获取调用factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数索引依赖注入参数 Bean
        ConstructorHaveParamBean byIndex = beanFactory.getBean("staticFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(byIndex.getMessage()).isNotNull();

        // 3 获取静态工厂Bean,其本质会获取调用factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数类型依赖注入参数 Bean
        ConstructorHaveParamBean byType = beanFactory.getBean("staticFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(byType.getMessage()).isNotNull();

        // 3 获取静态工厂Bean,其本质会获取调用factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数名字依赖注入参数 Bean
        ConstructorHaveParamBean byName = beanFactory.getBean("staticFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
    }


    /**
     * 创建创建静态工厂"staticFactoryBuildBean_byIndex"的BeanDefinition，注入IOC容器并获取Bean实例
     */
    @Test
    public void testInstantiatingBeanByStaticFactory() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
                null);
        // 2.1 定义其为工厂，并设置创建实例方法
        beanDefinition.setFactoryMethodName("newInstance");

        // 2.2 定义普通属性，并设置为工厂方法的参数（通过参数索引关联）
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(
                0, valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byIndex", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byIndex"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
                null);
        // 2.1 定义其为工厂，并设置创建实例方法
        beanDefinition.setFactoryMethodName("newInstance");

        // 2.2 定义普通属性，并设置为工厂方法的参数（通过参数类型关联）
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
        valueHolder1.setType("java.lang.String");
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byType", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byType"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
                null);
        // 2.1 定义其为工厂，并设置创建实例方法
        beanDefinition.setFactoryMethodName("newInstance");

        //2.2 定义普通属性，并设置为工厂方法的参数（通过参数名称关联）
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
        valueHolder1.setName("message");
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byName", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byName"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }


    @Test
    public void testInstantiatingBeanByStaticFactory4() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
                null);
        // 2.1 定义其为工厂，并设置创建实例方法
        beanDefinition.setFactoryMethodName("newInstance");


        // 2.2 定义映射属性，并设置为工厂方法的参数
        RuntimeBeanReference object1 = new RuntimeBeanReference("message");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);


        // 2.3 定义被映射bean
        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, "java.lang.String", null);
        TypedStringValue object2 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
                object2);
        beanDefinition2.getConstructorArgumentValues().addIndexedArgumentValue(
                0, valueHolder2);


        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byRef", null),
                beanFactory);

        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "message", null),
                beanFactory);

        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byRef"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byRef", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }
}
