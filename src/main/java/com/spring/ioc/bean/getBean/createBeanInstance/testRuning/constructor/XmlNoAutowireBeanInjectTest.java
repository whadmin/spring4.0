package com.spring.ioc.bean.getBean.createBeanInstance.testRuning.constructor;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/7/5 20:45
 */
public class XmlNoAutowireBeanInjectTest extends BaseTest {

    /**
     * 使用有参数构造函数实例化bean,参数手动配置
     */
    @Test
    public void testConstructorHaveParamBean() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/constructorBuildBean.xml");

        // 获取根据参数索引依赖注入的Bean
        ConstructorHaveParamBean byIndex = beanFactory.getBean("constructorParamBean_byIndex", ConstructorHaveParamBean.class);
        System.out.println(byIndex.getMessage().toString());
        assertThat(byIndex.getMessage()).isNotNull();

        // 获取根据参数类型依赖注入的Bean
        ConstructorHaveParamBean byType = beanFactory.getBean("constructorParamBean_byType", ConstructorHaveParamBean.class);
        assertThat(byType.getMessage()).isNotNull();
        System.out.println("byType：" + byType.getMessage().toString());

        // 获取根据参数名字依赖注入的Bean
        ConstructorHaveParamBean byName = beanFactory.getBean("constructorParamBean_byName", ConstructorHaveParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
        System.out.println("byName：" + byType.getMessage().toString());

        ConstructorHaveParamBean bean2_byRef = beanFactory.getBean("constructorParamBean_byRef",
                ConstructorHaveParamBean.class);
        assertThat(bean2_byRef.getMessage()).isNotNull();
        System.out.println("byRef：" + byType.getMessage().toString());
    }
}
