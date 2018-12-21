package com.spring.common.propertySource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.mock.env.MockPropertySource;

import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PropertySourcesPropertyResolverTests {

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
}
