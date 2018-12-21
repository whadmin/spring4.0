package com.spring.common.propertySource;

import org.junit.Test;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapPropertySourceTest {
    @Test
    public void mapPropertySource() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value1");
        PropertySource propertySource1 = new org.springframework.core.env.MapPropertySource("resource1", map);

        System.out.println(propertySource1.getProperty("key"));
    }
}
