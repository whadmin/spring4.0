package com.spring.common.propertySource;

import org.junit.Test;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.util.Properties;

public class PropertiesPropertySourceTest {

    @Test
    public void propertiesPropertySource() throws IOException {
        Properties props = new Properties();
        props.put("key", "value2");
        PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
                "resource2", props);

        System.out.println(propertySource2.getProperty("key"));
    }
}
