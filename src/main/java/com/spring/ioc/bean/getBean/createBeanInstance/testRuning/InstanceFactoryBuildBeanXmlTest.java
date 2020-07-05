package com.spring.ioc.bean.getBean.createBeanInstance.testRuning;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.javaConfig.ConfigurationBean;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 使用实例工厂实例化bean
 * <p>
 * 对应设计模式中 工厂方法（Static Factory Method）模式（一个工厂生产多个类型Bean实例）
 *
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:59
 */
public class InstanceFactoryBuildBeanXmlTest extends BaseTest {


    /**
     * 通过xml配置，使用实例工厂实例化bean
     */
    @Test
    public void testInstanceFactoryBuildBeanXml() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/instanceFactoryBuildBean.xml");

        // 3 获取工厂实例Bean,其本质会获取调用factory-bean="instanceFactory"工厂对应的factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数索引依赖注入参数 Bean
        ConstructorHaveParamBean byIndex = beanFactory.getBean("instanceFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(byIndex.getMessage()).isNotNull();

        // 3 获取工厂实例Bean,其本质会获取调用factory-bean="instanceFactory"工厂对应的factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数类型依赖注入参数 Bean
        ConstructorHaveParamBean byType = beanFactory.getBean("instanceFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(byType.getMessage()).isNotNull();

        // 3 获取工厂实例Bean,其本质会获取调用factory-bean="instanceFactory"工厂对应的factory-method="newInstance"，获得工厂创建的实例，
        // "newInstance"方法获得工厂创建实例时，存在方法参数，IOC容器会根据参数名字依赖注入参数 Bean
        ConstructorHaveParamBean byName = beanFactory.getBean("instanceFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
    }


    @Test
    public void testInstantiatingBeanByStaticFactory() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

        // 2.1 定义实例工厂beanDefinition，工厂实例beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
                null);
        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
                null);

        // 定义普通属性
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);

        // 2.2 定义工厂实例beanDefinition关联工厂，和创建方法，参数（通过参数索引关联普通属性）
        beanDefinition2.setFactoryBeanName("instanceFactory");
        beanDefinition2.setFactoryMethodName("newInstance");
        beanDefinition2.getConstructorArgumentValues().addIndexedArgumentValue(
                0, valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null),
                beanFactory);

        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byIndex", null),
                beanFactory);

        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byIndex"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

        // 2.1 定义实例工厂beanDefinition，工厂实例beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
                null);
        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
                null);

        // 定义普通属性
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        valueHolder1.setType("java.lang.String");

        // 2.2 定义工厂实例beanDefinition关联工厂，和创建方法，参数（通过参数类型关联普通属性）
        beanDefinition2.setFactoryBeanName("instanceFactory");
        beanDefinition2.setFactoryMethodName("newInstance");
        beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null),
                beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byType", null),
                beanFactory);

        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byType"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        // 2.1 定义实例工厂beanDefinition，工厂实例beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
                null);

        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
                null);

        // 定义普通属性
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        valueHolder1.setName("message");

        // 2.2 定义工厂实例beanDefinition关联工厂，和创建方法，参数（通过参数名称关联普通属性）
        beanDefinition2.setFactoryBeanName("instanceFactory");
        beanDefinition2.setFactoryMethodName("newInstance");
        beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null),
                beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byName", null),
                beanFactory);

        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byName"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory4() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        // 2.1 定义实例工厂beanDefinition，工厂实例beanDefinition，被依赖的beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
                null);

        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
                null);

        AbstractBeanDefinition beanDefinition3 = BeanDefinitionReaderUtils.createBeanDefinition(null, "java.lang.String",
                null);

        // 定义映射属性
        RuntimeBeanReference object1 = new RuntimeBeanReference("message");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);

        // 2.2 定义工厂实例beanDefinition关联工厂，和创建方法，参数（通过映射名称关联普通属性）
        beanDefinition2.setFactoryBeanName("instanceFactory");
        beanDefinition2.setFactoryMethodName("newInstance");
        beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 2.3 定义被依赖Bean
        TypedStringValue object2 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
                object2);
        beanDefinition3.getConstructorArgumentValues().addIndexedArgumentValue(
                0, valueHolder2);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null),
                beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byRef", null),
                beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition3, "message", null),
                beanFactory);

        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byRef"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byRef", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }



}
