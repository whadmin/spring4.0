package com.spring.ioc.bean.ability.alias;

import com.spring.BaseTest;
import com.spring.ioc.bean.ability.alias.javaConfig.AliasBeanConfig;
import com.spring.ioc.bean.ability.scope.javaConfig.PrototypeBeanConfig;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;


public class AliasBeanTest extends BaseTest {

    static <T> T[] asArray(T... arr) {
        return arr;
    }

    @Test
    public void testXmlConfigAlias() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/alias/aliasBean.xml");

        assertThat(beanFactory.getAliases("alias_bean1")).isEqualTo(asArray("bean1_alias"));
        assertThat(beanFactory.getAliases("alias_bean2")).isEmpty();
        assertThat(beanFactory.getAliases("alias_bean3")).isEqualTo(asArray("bean3_alias4","bean3_alias1","bean3_alias2","bean3_alias3"));
        assertThat(beanFactory.getAliases("alias_bean4")).isEqualTo(asArray("bean4_alias2","bean4_alias1"));
        assertThat(beanFactory.getAliases("alias_bean5")).isEqualTo(asArray("bean5_alias2","bean5_alias1"));
    }

    @Test
    public void testAnnotationConfigAlias() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        annotatedBeanAssembly(beanFactory, AliasBeanConfig.class);

        assertThat(beanFactory.containsBean("alias_bean1")).isTrue();
        assertThat(beanFactory.getAliases("alias_bean1")).isEmpty();

        assertThat(beanFactory.containsBean("bean2")).isFalse();
        assertThat(beanFactory.containsBean("alias_bean2")).isTrue();
        assertThat(beanFactory.getAliases("alias_bean2")).isEmpty();

        assertThat(beanFactory.containsBean("bean3")).isFalse();
        assertThat(beanFactory.containsBean("alias_bean3")).isTrue();
        assertThat(beanFactory.getAliases("alias_bean3")).isEqualTo(asArray("bean3_alias1","bean3_alias2"));

        assertThat(beanFactory.containsBean("alias_bean4")).isTrue();
        assertThat(beanFactory.getAliases("alias_bean4")).isEqualTo(asArray("bean4_alias2","bean4_alias1"));
    }



}
