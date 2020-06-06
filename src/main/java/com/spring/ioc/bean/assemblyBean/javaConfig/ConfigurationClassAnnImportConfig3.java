package com.spring.ioc.bean.assemblyBean.javaConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;


/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 14:37
 */
@Configuration(value ="configurationClassAnnImportConfig3")
@ConfigurationClassAnnImportConfig3.EnableHelloWorld2
@ConfigurationClassAnnImportConfig3.EnableHelloWorld1
public class ConfigurationClassAnnImportConfig3 {
    /**
     * 激活 HelloWorld 模块
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @Import(ConfigurationClassAnnBeanConfig1.class)
    public static @interface EnableHelloWorld1 {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @Import(ImportSelector.class)
    public static @interface EnableHelloWorld2 {
    }


    public static class ImportSelector implements org.springframework.context.annotation.ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{ConfigurationClassAnnBeanConfig2.class.getName()};
        }
    }
}
