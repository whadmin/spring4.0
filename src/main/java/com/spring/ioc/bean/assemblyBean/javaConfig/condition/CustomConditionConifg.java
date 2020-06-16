package com.spring.ioc.bean.assemblyBean.javaConfig.condition;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.spring.ioc.bean.assemblyBean.beanObject.condition.LinuxCondition;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.WindowsCondition;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.ConditionalOnBean;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.ConditionalOnClass;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.ConditionalService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.LinuxListService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.ListService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.OnBeanConditionalService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.OnClassConditionalService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.OnPropertyConditionalService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.WindowsListService;

@Configuration("customConditionConifg")
@ComponentScan("com.spring.ioc.bean.assemblyBean.beanObject.condition")
public class CustomConditionConifg {

	@Bean
    @Conditional(WindowsCondition.class)
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }
    
    @Bean
    public ConditionalOnBean conditionalOnBean() {
        return new ConditionalOnBean();
    }


    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnBean(name = "conditionalOnBean")
    public ConditionalService onBeanConditionalService() {
        return new OnBeanConditionalService();
    }
    

}
