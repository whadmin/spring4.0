package com.spring.ioc.bean.getBean.createBeanInstance.application;

import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:52
 *
 * 使用静态工厂实例化bean
 */
public class StaticFactoryBuildBeanTest {

    /**
     * 使用静态工厂实例化bean
     */
    @Test
    public void testStaticFactoryBuildBean() {
        // 使用静态工厂方法
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/createBeanInstance/staticFactoryBuildBean.xml");
        // 获取根据参数索引依赖注入的Bean
        ConstructorHaveParamBean byIndex = context.getBean("staticFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(byIndex.getMessage()).isNotNull();

        // 获取根据参数类型依赖注入的Bean
        ConstructorHaveParamBean byType = context.getBean("staticFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(byType.getMessage()).isNotNull();

        // 获取根据参数名字依赖注入的Bean
        ConstructorHaveParamBean byName = context.getBean("staticFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
    }
}
