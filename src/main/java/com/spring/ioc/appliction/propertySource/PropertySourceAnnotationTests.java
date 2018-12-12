package com.spring.ioc.appliction.propertySource;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class PropertySourceAnnotationTests {

    @Configuration
    @org.springframework.context.annotation.PropertySource(name="p1", value="classpath:propertySource/p1.properties")
    static class ConfigurationAnnotationPropertySource {
    }

    @Test
    public void withExplicitName() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ConfigurationAnnotationPropertySource.class);
        ctx.refresh();
        assertThat(ctx.getEnvironment().getProperty("testbean.name"), equalTo("p1TestBean"));
    }
}
