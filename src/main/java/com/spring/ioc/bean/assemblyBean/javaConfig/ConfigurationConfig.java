package com.spring.ioc.bean.assemblyBean.javaConfig;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import.ImportSelector;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.ExcludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.IncludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import.ImportConfiguration;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value ="configurationConfig")
//@Import(ImportConfiguration.class)
@Import(ImportSelector.class)
@ComponentScan(
        basePackages = "com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner",
        includeFilters = { @ComponentScan.Filter(IncludeFilter.class)} ,
        excludeFilters = { @ComponentScan.Filter(ExcludeFilter.class)})
public class ConfigurationConfig {

    @Bean(name = "bean",autowire = Autowire.NO, destroyMethod ="destroy", initMethod ="init")
    public BeanObject BeanObject() {
        return new BeanObject();
    }
}
