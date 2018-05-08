package com.spring.ioc.javaConig;

import javax.sql.DataSource;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.spring.ioc.appliction.conditional.LinuxCondition;
import com.spring.ioc.appliction.conditional.WindowsCondition;
import com.spring.ioc.appliction.conditional.model.ConditionalOnBean;
import com.spring.ioc.appliction.conditional.model.ConditionalOnClass;
import com.spring.ioc.appliction.conditional.model.ConditionalService;
import com.spring.ioc.appliction.conditional.model.LinuxListService;
import com.spring.ioc.appliction.conditional.model.ListService;
import com.spring.ioc.appliction.conditional.model.OnBeanConditionalService;
import com.spring.ioc.appliction.conditional.model.OnClassConditionalService;
import com.spring.ioc.appliction.conditional.model.OnPropertyConditionalService;
import com.spring.ioc.appliction.conditional.model.WindowsListService;

@Configuration
@ComponentScan("com.spring.ioc.appliction.conditional.model")
public class ConditionConifg {
	@Bean
    @Conditional(WindowsCondition.class) //①
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class) //②
    public ListService linuxListService() {
        return new LinuxListService();
    }
    
    @Bean
    public ConditionalOnBean conditionalOnBean() {
        return new ConditionalOnBean();
    }
    
    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnBean(name="conditionalOnBean")
    public ConditionalService onBeanConditionalService() {
        return new OnBeanConditionalService();
    }
    
    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnClass({ ConditionalOnClass.class})
    public ConditionalService onClassConditionalService() {
        return new OnClassConditionalService();
    }
    
    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "isLoad", havingValue = "true", matchIfMissing = true)
    public ConditionalService onPropertyConditionalService() {
        return new OnPropertyConditionalService();
    }
    

}
