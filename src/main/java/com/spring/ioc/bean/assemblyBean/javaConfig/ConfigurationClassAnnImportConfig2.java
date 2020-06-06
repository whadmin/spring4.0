package com.spring.ioc.bean.assemblyBean.javaConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 14:37
 */
@Configuration(value ="configurationClassAnnImportConfig2")
@Import(ConfigurationClassAnnImportConfig2.ImportSelector.class)
public class ConfigurationClassAnnImportConfig2 {

    /**
     * @Author: wuhao.w
     * @Date: 2020/1/7 23:51
     */
    public static class ImportSelector implements org.springframework.context.annotation.ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{ConfigurationClassAnnBeanConfig1.class.getName()};
        }
    }
}
