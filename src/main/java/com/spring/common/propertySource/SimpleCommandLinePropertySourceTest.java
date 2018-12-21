package com.spring.common.propertySource;

import org.junit.Test;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SimpleCommandLinePropertySourceTest {

    /**
     * 测试默认不带参数的String[] args
     * SimpleCommandLinePropertySource
     */
    @Test
    public void withDefaul() {
        PropertySource<?> ps = new SimpleCommandLinePropertySource();
        assertThat(((SimpleCommandLinePropertySource) ps).getPropertyNames().length, equalTo(0));
        assertThat(ps.getName(), equalTo(CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME));
        assertThat(ps.containsProperty("foo"), is(false));
        assertThat(ps.getProperty("foo"), nullValue());

        ps = new SimpleCommandLinePropertySource("ps1", new String[0]);
        assertThat(ps.getName(), equalTo("ps1"));
        assertThat(((SimpleCommandLinePropertySource) ps).getPropertyNames().length, equalTo(0));
        assertThat(ps.containsProperty("foo"), is(false));
        assertThat(ps.getProperty("foo"), nullValue());
    }

    @Test
    public void withOptionArgs() {
        CommandLinePropertySource<?> ps =
                new SimpleCommandLinePropertySource("--o1=v1", "noa1", "--o2", "noa2");
        assertThat(ps.getPropertyNames().length, is(4));

        assertThat(ps.containsProperty("o1"), is(true));
        assertThat(ps.getProperty("o1"), equalTo("v1"));

        assertThat(ps.containsProperty("o2"), is(true));
        assertThat(ps.getProperty("o2"), equalTo(""));

        assertThat(ps.containsProperty("o3"), is(false));
        assertThat(ps.getProperty("o3"), nullValue());

        assertThat(ps.containsProperty("nonOptionArgs"), is(false));
        assertThat(ps.getProperty("nonOptionArgs"), nullValue());

        assertThat(ps.containsProperty("nonOptionArgs"), is(true));
        String nonOptionArgs = ps.getProperty("nonOptionArgs");
        assertThat(nonOptionArgs, equalTo("noa1,noa2"));

        ps.setNonOptionArgsPropertyName("NOA");
        nonOptionArgs = ps.getProperty("NOA");
        assertThat(nonOptionArgs, equalTo("noa1,noa2"));

        StandardEnvironment env = new StandardEnvironment();
        env.getPropertySources().addFirst(ps);
        String[] nonOptionArgsArray = env.getProperty("nonOptionArgs", String[].class);
        assertThat(nonOptionArgsArray[0], equalTo("noa1"));
        assertThat(nonOptionArgsArray[1], equalTo("noa2"));

    }


}
