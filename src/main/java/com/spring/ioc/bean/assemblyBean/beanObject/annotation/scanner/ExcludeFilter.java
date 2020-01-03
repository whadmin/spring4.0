package com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExcludeFilter {

	String value() default "";

}
