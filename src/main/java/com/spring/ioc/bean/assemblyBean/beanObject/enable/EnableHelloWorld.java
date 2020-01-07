package com.spring.ioc.bean.assemblyBean.beanObject.enable;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import.ImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  激活 HelloWorld 模块
 *
 * @author 小马哥
 * @since 2018/5/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(ImportConfiguration.class)
@Import(ImportSelector.class)
public @interface EnableHelloWorld {
}
