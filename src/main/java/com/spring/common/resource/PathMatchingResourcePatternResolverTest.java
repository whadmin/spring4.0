package com.spring.common.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PathMatchingResourcePatternResolverTest {

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private static final String[] CLASSES_IN_COMMONSLOGGING =
            new String[] {"Log.class", "LogConfigurationException.class", "LogFactory.class"};



    @Test
    public void singleResourceOnFileSystem() throws IOException {
        Resource[] resources =
                resolver.getResources("com/spring/common/resource/PathMatchingResourcePatternResolverTest.class");
        assertEquals(1, resources.length);
        assertProtocolAndFilenames(resources, "file", "PathMatchingResourcePatternResolverTest.class");
    }


    @Test
    public void singleResourceInJar() throws IOException {
        Resource[] resources = resolver.getResources("java/net/URL.class");
        assertEquals(1, resources.length);
        assertProtocolAndFilenames(resources, "jar", "URL.class");
    }

    @Test
    public void singleResourceclasspath() throws IOException {
        Resource[] resources = resolver.getResources("classpath:META-INF/spring.factories");
        assertEquals(1, resources.length);
        assertProtocolAndFilenames(resources, "file", "spring.factories");
    }

    @Test
    public void singleResourceclasspath1() throws IOException {
        Resource[] resources = resolver.getResources("classpath*:META-INF/spring.factories");
        assertEquals(13, resources.length);
        assertProtocolAndFilenames(resources, "file", "spring.factories");
    }

    @Test
    public void classpathStarWithPatternOnFileSystem() throws IOException {
        Resource[] resources = resolver.getResources("classpath*:org/springframework/core/io/sup*/*.class");

    }

    private void assertProtocolAndFilenames(Resource[] resources, String protocol, String... filenames)
            throws IOException {

        assertEquals("Correct number of files found", filenames.length, resources.length);
        for (Resource resource : resources) {
            String actualProtocol = resource.getURL().getProtocol();
            // resources from rt.jar get retrieved as jrt images on JDK 9, so let's simply accept that as a match too
            assertTrue(actualProtocol.equals(protocol) || ("jar".equals(protocol) && "jrt".equals(actualProtocol)));
            assertFilenameIn(resource, filenames);
        }
    }




    @Test
    public void classpathWithPatternInJar() throws IOException {
        Resource[] resources = resolver.getResources("classpath:org/apache/commons/logging/*.class");
        assertProtocolAndFilenames(resources, "jar", CLASSES_IN_COMMONSLOGGING);
    }

    @Test
    public void classpathStartWithPatternInJar() throws IOException {
        Resource[] resources = resolver.getResources("classpath*:org/apache/commons/logging/*.class");
        assertProtocolAndFilenames(resources, "jar", CLASSES_IN_COMMONSLOGGING);
    }


    private void assertFilenameIn(Resource resource, String... filenames) {
        String filename = resource.getFilename();
        assertTrue(resource + " does not have a filename that matches any of the specified names",
                Arrays.stream(filenames).anyMatch(filename::endsWith));
    }

    @Test
    public void testClasspathPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //只加载一个绝对匹配Resource，且通过ResourceLoader.getResource进行加载
        Resource[] resources = resolver.getResources("classpath:META-INF/spring.factories");
        Assert.assertEquals(1, resources.length);

        //只加载一个匹配的Resource，且通过ResourceLoader.getResource进行加载
        resources = resolver.getResources("classpath:META-INF/spring.factories");
        Assert.assertTrue(resources.length == 1);
    }


    @Test
    public void testClasspathAsteriskPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:META-INF/spring.factories");
        Assert.assertTrue(resources.length > 1);

        //将加载多个模式匹配的Resource
        resources = resolver.getResources("classpath*:META-INF/*.LIST");
        Assert.assertTrue(resources.length > 1);


    }

    @Test
    public void testClasspathAsteriskPrefixLimit() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //将首先通过ClassLoader.getResources("")加载目录，
        //将只返回文件系统的类路径不返回jar的跟路径
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:asm-*.txt");
        Assert.assertTrue(resources.length == 0);

        //将通过ClassLoader.getResources("asm-license.txt")加载
        //asm-license.txt存在于com.springsource.net.sf.cglib-2.2.0.jar
        resources = resolver.getResources("classpath*:asm-license.txt");
        Assert.assertTrue(resources.length > 0);

        //将只加载文件系统类路径匹配的Resource
        resources = resolver.getResources("classpath*:LICENS*");
        Assert.assertTrue(resources.length == 1);
    }

    @Test
    public void testFilekPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:D:/*.txt");
        Assert.assertTrue(resources.length > 0);
    }


}
