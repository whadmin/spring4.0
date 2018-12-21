package com.spring.common.propertySource;

import org.junit.Test;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SimpleCommandLinePropertySourceTests {

    /**
     *
     *
     */
    @Test
    public void withDefaul() {
        PropertySource<?> ps = new SimpleCommandLinePropertySource();
        assertThat(((SimpleCommandLinePropertySource) ps).getPropertyNames().length,equalTo(0));
        assertThat(ps.getName(), equalTo(CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME));
        assertThat(ps.containsProperty("foo"), is(false));
        assertThat(ps.getProperty("foo"), nullValue());

        ps = new SimpleCommandLinePropertySource("ps1", new String[0]);
        assertThat(ps.getName(), equalTo("ps1"));
        assertThat(((SimpleCommandLinePropertySource) ps).getPropertyNames().length,equalTo(0));
        assertThat(ps.containsProperty("foo"), is(false));
        assertThat(ps.getProperty("foo"), nullValue());
    }


}
