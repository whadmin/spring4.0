package com.spring.ioc.bean.getBean.populateBean.testRuning.core;

import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean;
import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnQualifierBean;
import com.spring.ioc.bean.getBean.PostProcessorRegistrationDelegate;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 20:29
 */
public class AutowireByAnnInjectCore {

    @Test
    public void testInstantiatingBeanByAutowired() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.populateBean.beanObject.enable.AutowireByAnnBean", null);
        // 设置自动注入的方式
        beanDefinition1.setAutowireCandidate(true);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "bean_annotation_at", null), beanFactory);

        BeanDefinition bean_Autowired_at = beanFactory.getBeanDefinition("bean_annotation_at");
        System.err.println(ReflectionToStringBuilder.toString(bean_Autowired_at, ToStringStyle.MULTI_LINE_STYLE));

        Resource resource = new ClassPathResource("ioc/bean/getBean/createBeanInstance/autowireByAnnotationTrue.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bean_byType = beanFactory.getBeanDefinition("bean_annotation");
        System.err.println(ReflectionToStringBuilder.toString(bean_byType, ToStringStyle.MULTI_LINE_STYLE));


        /**
         * 使用 @Resource 注入 注解需要添加 CommonAnnotationBeanPostProcessor
         * 使用 @Autowired  注入 注解需要添加  AutowiredAnnotationBeanPostProcessor
         *
         * 方式一 编程方式手动添加
         *
         *
         * 方式二 使用 AbstractApplicationContext
         *
         *    定义手动bean
         *
         *      <bean class="org.springframework.beans.factory.enable.AutowiredAnnotationBeanPostProcessor"></bean>
         *      <bean class="org.springframework.context.enable.CommonAnnotationBeanPostProcessor"></bean>
         *
         *    或xml 配置
         *      <!-- 注解注入需要添加此标签，BeanFactory回通过此标签添加注解标签的处理器 .beanProcessor -->
         *                 <context:enable-config/> 或  <context:component-scan base-package=""></context:component-scan>
         *    或使用注解
         *
         *    在装配时注入
         *       AnnotationConfigUtils.registerAnnotationConfigProcessors(BeanDefinitionRegistry, Object)  (org.springframework.context.enable)
         *              ComponentScanBeanDefinitionParser.registerComponents(XmlReaderContext, Set<BeanDefinitionHolder>, Element)  (org.springframework.context.enable)
         *              ComponentScanBeanDefinitionParser.parse(Element, ParserContext)  (org.springframework.context.enable)
         *              AnnotationConfigUtils.registerAnnotationConfigProcessors(BeanDefinitionRegistry)  (org.springframework.context.enable)
         *              AnnotationConfigBeanDefinitionParser.parse(Element, ParserContext)  (org.springframework.context.enable)
         *
         *   refresh 过程中注册
         *      PostProcessorRegistrationDelegate.registerBeanPostProcessors(ConfigurableListableBeanFactory, AbstractApplicationContext)  (org.springframework.context.support)
         *           AbstractApplicationContext.registerBeanPostProcessors(ConfigurableListableBeanFactory)  (org.springframework.context.support)
         *                AbstractApplicationContext.refresh()  (org.springframework.context.support)

         * **/
//        core.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
//        core.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
//        core.addBeanPostProcessor(new RequiredAnnotationBeanPostProcessor());
        registerAnnotationConfigProcessors(beanFactory);
        PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory,null);

        AutowireByAnnBean bean1 = beanFactory.getBean("bean_annotation", AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();

        AutowireByAnnBean bean2 = beanFactory.getBean("bean_annotation_at", AutowireByAnnBean.class);
        assertThat(bean2.getDataSource2()).isNotNull();
    }



    @Test
    public void testInstantiatingBeanByAutowired2() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.populateBean.beanObject.enable.AutowireByAnnQualifierBean", null);
        // 设置自动注入的方式
        beanDefinition1.setAutowireCandidate(true);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "bean_byType_qualifier_at", null), beanFactory);

        BeanDefinition bean_Autowired_at = beanFactory.getBeanDefinition("bean_byType_qualifier_at");
        System.err.println(ReflectionToStringBuilder.toString(bean_Autowired_at, ToStringStyle.MULTI_LINE_STYLE));

        Resource resource = new ClassPathResource("ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierFalse.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bean_byType_qualifier = beanFactory.getBeanDefinition("bean_byType_qualifier");
        System.err.println(ReflectionToStringBuilder.toString(bean_byType_qualifier, ToStringStyle.MULTI_LINE_STYLE));

        registerAnnotationConfigProcessors(beanFactory);
        PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory,null);
        //core.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());

        AutowireByAnnQualifierBean bean1 = beanFactory.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        assertThat(bean1.getMysqlDataSource3()).isNotNull();
        assertThat(bean1.getOracleDataSource1()).isNotNull();
        assertThat(bean1.getOracleDataSource2()).isNotNull();
        assertThat(bean1.getOracleDataSource3()).isNotNull();

        AutowireByAnnQualifierBean bean2 = beanFactory.getBean("bean_byType_qualifier_at",
                AutowireByAnnQualifierBean.class);
        assertThat(bean2.getMysqlDataSource1()).isNotNull();
        assertThat(bean2.getMysqlDataSource2()).isNotNull();
        assertThat(bean2.getMysqlDataSource3()).isNotNull();
        assertThat(bean2.getOracleDataSource1()).isNotNull();
        assertThat(bean2.getOracleDataSource2()).isNotNull();
        assertThat(bean2.getOracleDataSource3()).isNotNull();
    }

    public void registerAnnotationConfigProcessors(DefaultListableBeanFactory context){
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME)).isTrue();
    }
}
