package com.spring.ioc.bean.assemblyBean.javaConfig;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.ExcludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.IncludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 14:35
 */
@Configuration(value ="configurationClassAnnComponentScanConfig")
@ComponentScan(
        basePackages = "com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner",
        includeFilters = { @ComponentScan.Filter(IncludeFilter.class)} ,
        excludeFilters = { @ComponentScan.Filter(ExcludeFilter.class)})
public class ConfigurationClassAnnComponentScanConfig {
}
