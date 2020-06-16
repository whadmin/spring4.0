package com.spring.ioc.bean.assemblyBean.javaConfig.condition;

import com.spring.ioc.bean.assemblyBean.beanObject.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/6 18:50
 */
@Configuration("springBootConditionConfig")
public class SpringBootConditionConfig {

    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnBean(name = "conditionalOnBean")
    public ConditionalService onBeanConditionalService() {
        return new OnBeanConditionalService();
    }

    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnClass({ConditionalOnClass.class})
    public ConditionalService onClassConditionalService() {
        return new OnClassConditionalService();
    }

    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "isLoad", havingValue = "true", matchIfMissing = true)
    public ConditionalService onPropertyConditionalService() {
        return new OnPropertyConditionalService();
    }


}
