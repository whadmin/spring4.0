package com.spring.ioc.appliction.environment;
import com.spring.BaseTest;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.context.support.XmlWebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class PropertySourceAnnotationTests extends BaseTest {

    @Configuration
    @org.springframework.context.annotation.PropertySource(name="p1", value="classpath:propertySource/p1.properties")
    static class ConfigurationAnnotationPropertySource {
    }

    @Test
    public void withExplicitName() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        //web环境IOC Environment 类型为 StandardServletEnvironment
        assertThat(context.getEnvironment() instanceof StandardServletEnvironment);


    }

    private void assertThat(boolean b) {
    }
}
