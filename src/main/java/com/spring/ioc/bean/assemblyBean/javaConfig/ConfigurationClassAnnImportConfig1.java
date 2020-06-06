package com.spring.ioc.bean.assemblyBean.javaConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 14:36
 */
@Configuration(value = "configurationClassAnnImportConfig1")
@Import(ConfigurationClassAnnBeanConfig2.class)
public class ConfigurationClassAnnImportConfig1 {
}
