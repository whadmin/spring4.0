package com.spring.ioc.bean.register.beanObject.configuration;

import com.spring.ioc.bean.register.beanObject.scanner.ExcludeFilter;
import com.spring.ioc.bean.register.beanObject.scanner.IncludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value ="configurationScannerConfig" )
@ComponentScan(
        basePackages = "com.spring.ioc.bean.register.beanObject.scanner",
        includeFilters = { @ComponentScan.Filter(IncludeFilter.class)} ,
        excludeFilters = { @ComponentScan.Filter(ExcludeFilter.class)})
public class ConfigurationScannerConfig {
}
