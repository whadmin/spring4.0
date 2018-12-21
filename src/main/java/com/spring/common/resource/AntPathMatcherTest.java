package com.spring.common.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntPathMatcherTest {

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Test
    public void testQuestionMark() {
        Assert.assertTrue(pathMatcher.match("config?.xml", "config1.xml"));
        Assert.assertFalse(pathMatcher.match("config?.xml", "config12.xml"));
        Assert.assertFalse(pathMatcher.match("config?.xml", "config.xml"));
    }

    @Test
    public void testOneAsterisk() {
        Assert.assertTrue(pathMatcher.match("config-*.xml", "config-dao.xml"));
        Assert.assertTrue(pathMatcher.match("config-*.xml", "config-.xml"));
        Assert.assertTrue(pathMatcher.match("config-**.xml", "config-dao.xml"));

        Assert.assertFalse(pathMatcher.match("config-*.xml", "config-1/.xml"));
        Assert.assertFalse(pathMatcher.match("config-*.xml", "config-1/2.xml"));

        Assert.assertTrue(pathMatcher.match("/cn/*/config.xml", "/cn/javass/config.xml"));

        Assert.assertFalse(pathMatcher.match("/cn/*/config.xml", "/cn/config.xml"));
        Assert.assertFalse(pathMatcher.match("/cn/*/config.xml", "/cn//config.xml"));
        Assert.assertFalse(pathMatcher.match("/cn/*/config.xml", "/cn/javass/spring/config.xml"));

    }

    @Test
    public void testTwoAsterisk() {
        Assert.assertTrue(pathMatcher.match("/cn/**/config-*.xml", "/cn/javass/config-dao.xml"));
        Assert.assertTrue(pathMatcher.match("/cn/**/config-*.xml", "/cn/javass/spring/config-dao.xml"));
        Assert.assertTrue(pathMatcher.match("/cn/**/config-*.xml", "/cn/config-dao.xml"));

    }
}
