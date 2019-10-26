package com.spring.ioc.bean.register.beanObject.scanner;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface IncludeFilter {

	String value() default "";

}
