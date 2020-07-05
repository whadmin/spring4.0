package com.spring.ioc.bean.getBean.createBeanInstance.testRuning.constructor;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.annotation.AutowireByConstructorQualifierBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructor;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructorMultipleMatches;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/7/5 20:47
 */
public class AutowireByConstructorInjectTest extends BaseTest {

    /**
     * 使用有参数构造函数实例化bean，通过 ByConstructor 自动注入 参数，通过类型自动注入（名称优先，类型之后）
     */
    @Test
    public void testAutowireByConstructor() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/autowireByConstructor.xml");

        // 获取根据参数索引依赖注入的Bean
        AutowireByConstructor bean_true = beanFactory.getBean("autowire_Byconstructor", AutowireByConstructor.class);
        assertThat(bean_true.getMysqlDataSource()).isNotNull();
        assertThat(bean_true.getOracleDataSource()).isNotNull();
        assertThat(bean_true.getMessage()).isNotNull();
    }

    /**
     * 使用有参数构造函数实例化bean，通过 ByConstructor 自动注入 参数通过类型自动注入（名称优先，类型之后）
     * 依赖存在多个相同类型时报错
     */
    @Test(expected = Exception.class)
    public void testAutowireByConstructorError() {
        // 使用构造器
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/createBeanInstance/autowireByConstructorError.xml");
    }

    /**
     * 使用有参数构造函数实例化bean 通过 ByConstructor 自动注入 参数通过类型自动注入（名称优先，类型之后）
     * 依赖存在多个相同类型时报错,通过Primary 设置优先匹配注入规则
     */
    @Test
    public void testAutowireByConstructorPrimary() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/autowireByConstructorPrimary.xml");
        // 获取根据参数索引依赖注入的Bean
        AutowireByConstructorMultipleMatches bean_error = beanFactory.getBean("autowire_Byconstructor_primary", AutowireByConstructorMultipleMatches.class);
        assertThat(bean_error.getDataSource()).isNotNull();
    }


    /**
     * 使用有参数构造函数实例化bean 通过 ByConstructor 自动注入 参数通过类型自动注入（名称优先，类型之后）
     * 依赖存在多个相同类型时报错,通过Qualifier 设置优先匹配注入规则
     * <p>
     * 使用Qualifier功能
     * 1 xml配置<context:annotation-config/>XmlBeanDefinitionReader会读取装配设置自动装配器ContextAnnotationAutowireCandidateResolver
     */
    @Test
    public void testAutowireByConstructorQualifier() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierTrue.xml");

        assertThat(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver);

        AutowireByConstructorQualifierBean bean1 = beanFactory.getBean("autowire_Byconstructor_qualifier",
                AutowireByConstructorQualifierBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();
    }


    /**
     * 使用有参数构造函数实例化bean 通过 ByConstructor 自动注入 参数通过类型自动注入（名称优先，类型之后）
     * 依赖存在多个相同类型时报错,通过Qualifier 设置优先匹配注入规则
     * <p>
     * 使用Qualifier功能
     * 1 设置自动装配器ContextAnnotationAutowireCandidateResolver
     */
    @Test
    public void testAutowireByConstructorQualifier2() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierFalse.xml");

        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        AutowireByConstructorQualifierBean bean1 = beanFactory.getBean("autowire_Byconstructor_qualifier",
                AutowireByConstructorQualifierBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();
    }
}
