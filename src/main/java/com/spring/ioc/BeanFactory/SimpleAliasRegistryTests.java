package com.spring.ioc.BeanFactory;

import org.junit.Test;
import org.springframework.core.SimpleAliasRegistry;

import static org.assertj.core.api.Assertions.assertThat;


public class SimpleAliasRegistryTests {

    @Test
    void aliasChaining() {
        SimpleAliasRegistry registry = new SimpleAliasRegistry();
        registry.registerAlias("test", "testAlias");
        registry.registerAlias("testAlias", "testAlias2");
        registry.registerAlias("testAlias2", "testAlias3");

        assertThat(registry.hasAlias("test", "testAlias")).isTrue();
        assertThat(registry.hasAlias("test", "testAlias2")).isTrue();
        assertThat(registry.hasAlias("test", "testAlias3")).isTrue();
        assertThat(registry.canonicalName("testAlias")).isSameAs("test");
        assertThat(registry.canonicalName("testAlias2")).isSameAs("test");
        assertThat(registry.canonicalName("testAlias3")).isSameAs("test");
    }

    @Test  // SPR-17191
    void testAliasChainingWithMultipleAliases() {
        SimpleAliasRegistry registry = new SimpleAliasRegistry();
        registry.registerAlias("name", "alias_a");
        registry.registerAlias("name", "alias_b");
        assertThat(registry.hasAlias("name", "alias_a")).isTrue();
        assertThat(registry.hasAlias("name", "alias_b")).isTrue();

        registry.registerAlias("real_name", "name");
        assertThat(registry.hasAlias("real_name", "name")).isTrue();
        assertThat(registry.hasAlias("real_name", "alias_a")).isTrue();
        assertThat(registry.hasAlias("real_name", "alias_b")).isTrue();

        registry.registerAlias("name", "alias_c");
        assertThat(registry.hasAlias("real_name", "name")).isTrue();
        assertThat(registry.hasAlias("real_name", "alias_a")).isTrue();
        assertThat(registry.hasAlias("real_name", "alias_b")).isTrue();
        assertThat(registry.hasAlias("real_name", "alias_c")).isTrue();
    }
}
