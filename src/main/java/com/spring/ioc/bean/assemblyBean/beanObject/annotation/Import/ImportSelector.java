package com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import;

import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/7 23:51
 */
public class ImportSelector implements org.springframework.context.annotation.ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ImportConfiguration.class.getName()};
    }
}
