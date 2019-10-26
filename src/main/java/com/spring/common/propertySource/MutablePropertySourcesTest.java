package com.spring.common.propertySource;

import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MutablePropertySourcesTest {

    @Test
    public void MutablePropertySources() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value1");
        PropertySource propertySource1 = new MapPropertySource("resource1", map);

        Properties props = new Properties();
        props.put("key", "value2");
        PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
                "resource2", props);

        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource1);
        propertySources.addLast(propertySource2);
        System.out.println(propertySources.get("resource1").getProperty("key"));

        for (PropertySource propertySource : propertySources) {
            System.out.println(propertySource.getProperty("key"));
        }
    }
}
