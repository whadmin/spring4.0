package com.spring.common.propertySource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.*;
import org.springframework.core.env.MapPropertySource;
import org.springframework.mock.env.MockPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PropertySourcesPropertyResolverTest {

    private Properties testProperties;

    private MutablePropertySources propertySources;

    private ConfigurablePropertyResolver propertyResolver;


    @Before
    public void setUp() {
        propertySources = new MutablePropertySources();
        propertyResolver = new PropertySourcesPropertyResolver(propertySources);
        testProperties = new Properties();
        propertySources.addFirst(new PropertiesPropertySource("testProperties", testProperties));
    }


    @Test
    public void resolveNestedPropertyPlaceholders() {
        MutablePropertySources ps = new MutablePropertySources();
        ps.addFirst(new MockPropertySource()
                .withProperty("p1", "v1")
                .withProperty("p2", "v2")
                .withProperty("p3", "${p1}:${p2}")              // nested placeholders
                .withProperty("p4", "${p3}")                    // deeply nested placeholders
                .withProperty("p5", "${p1}:${p2}:${bogus}")     // unresolvable placeholder
                .withProperty("p6", "${p1}:${p2}:${bogus:def}") // unresolvable w/ default
                .withProperty("pL", "${pR}")                    // cyclic reference left
                .withProperty("pR", "${pL}")                    // cyclic reference right
        );
        ConfigurablePropertyResolver pr = new PropertySourcesPropertyResolver(ps);
        assertThat(pr.getProperty("p1"), equalTo("v1"));
        assertThat(pr.getProperty("p2"), equalTo("v2"));
        assertThat(pr.getProperty("p3"), equalTo("v1:v2"));
        assertThat(pr.getProperty("p4"), equalTo("v1:v2"));
        try {
            pr.getProperty("p5");
        }
        catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString(
                    "Could not resolve placeholder 'bogus' in value \"${p1}:${p2}:${bogus}\""));
        }
        assertThat(pr.getProperty("p6"), equalTo("v1:v2:def"));
        try {
            pr.getProperty("pL");
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().toLowerCase().contains("circular"));
        }
    }

    @Test
    public void PropertyResolver() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value1");
        map.put("SPRING_APPLICATION_JSON", "value2");
        //map.put("spring.application.json", "value3");
        PropertySource propertySource1 = new MapPropertySource("resource1", map);

        Properties props = new Properties();
        props.put("key", "value2");
        PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
                "resource2", props);

        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource1);
        propertySources.addLast(propertySource2);
        System.out.println(propertySources.get("resource1").getProperty("key"));

        PropertyResolver  propertyResolver = new PropertySourcesPropertyResolver(propertySources);

        System.out.println(propertyResolver.getProperty("key"));
        System.out.println(propertyResolver.getProperty("no", "default"));
        System.out.println(propertyResolver.resolvePlaceholders("must be encoding ${key}"));  //输出must be encoding gbk
        System.out.println(propertyResolver.resolvePlaceholders("must be encoding ${spring.application.json:${SPRING_APPLICATION_JSON:}}"));


    }
}
