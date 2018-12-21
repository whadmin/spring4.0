package com.spring.common.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClassPathResourceTest  {


    /**
     * 使用默认的加载器加载资源，将加载当前ClassLoader类路径上相对于根路径的资源
     * 这里无论是否使用"/"开头 都从classpath路径读取（统一了getClassLoader().getResource规则）
     */
    @Test
    public void testClasspathResourceByDefaultClassLoader() throws IOException {
        Resource resource = new ClassPathResource("com/spring/common/resource/test1.properties");
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("2222\r\n", content);
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());

         resource = new ClassPathResource("/com/spring/common/resource/test1.properties");
         content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("2222\r\n", content);
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
    }


    /**
     * 使用指定的ClassLoader进行加载资源，将加载指定的ClassLoader类路径上相对于根路径的资源
     * 这里无论是否使用"/"开头 都从classpath路径读取（统一了getClassLoader().getResource规则）
     */
    @Test
    public void testClasspathResourceByClassLoader() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        Resource resource = new ClassPathResource("com/spring/common/resource/test1.properties" , cl);
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("2222\r\n", content);
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());

         cl = this.getClass().getClassLoader();
         resource = new ClassPathResource("/com/spring/common/resource/test1.properties" , cl);
         content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("2222\r\n", content);
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
    }


    /**
     * 使用指定的类进行加载资源，将尝试加载相对于当前类的路径的资源
     * path不以’/'开头时，默认是从此类所在的包下取资源；
     * path以’/'开头时，则是从ClassPath根下获取；
     */
    @Test
    public void testClasspathResourceByClass() throws IOException {
        Class clazz = this.getClass();
        Resource resource1 = new ClassPathResource("/com/spring/common/resource/test1.properties" , ClassPathResourceTest.class);
        String content1 = FileCopyUtils.copyToString(new InputStreamReader(resource1.getInputStream()));
        assertEquals("2222\r\n", content1);
        System.out.println("path:" + resource1.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource1.isOpen());

        Resource resource2 = new ClassPathResource("test1.properties" , this.getClass());
        String content2 = FileCopyUtils.copyToString(new InputStreamReader(resource2.getInputStream()));
        assertEquals("2222\r\n", content2);
        System.out.println("path:" + resource2.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource2.isOpen());
    }


    /**
     * 加载jar包里的资源，首先在当前类路径下找不到，最后才到Jar包里找，而且在第一个Jar包里找到的将被返回
     * @throws IOException
     */
    @Test
    public void classpathResourceTestFromJar() throws IOException {
        Resource resource = new ClassPathResource("META-INF/spring.factories");
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        System.out.println(content);
        System.out.println("path:" + resource.getURL().getPath());
        Assert.assertEquals(false, resource.isOpen());
    }


    @Test
    public void testClassPathResourceCreateRelative() throws IOException {
        Resource resource = new ClassPathResource("org/springframework/core/io/Resource.class");
        doTestResource(resource);
        Resource resource2 = new ClassPathResource("org/springframework/core/../core/io/./Resource.class");
        assertEquals(resource, resource2);
        Resource resource3 = new ClassPathResource("org/springframework/core/").createRelative("../core/io/./Resource.class");
        assertEquals(resource, resource3);

        // Check whether equal/hashCode works in a HashSet.
        HashSet<Resource> resources = new HashSet<Resource>();
        resources.add(resource);
        resources.add(resource2);
        assertEquals(1, resources.size());
    }

    private void doTestResource(Resource resource) throws IOException {
        assertEquals("Resource.class", resource.getFilename());
        assertTrue(resource.getURL().getFile().endsWith("Resource.class"));

        Resource relative1 = resource.createRelative("ClassPathResource.class");
        assertEquals("ClassPathResource.class", relative1.getFilename());
        assertTrue(relative1.getURL().getFile().endsWith("ClassPathResource.class"));
        assertTrue(relative1.exists());

        Resource relative2 = resource.createRelative("support/ResourcePatternResolver.class");
        assertEquals("ResourcePatternResolver.class", relative2.getFilename());
        assertTrue(relative2.getURL().getFile().endsWith("ResourcePatternResolver.class"));
        assertTrue(relative2.exists());

    }


}
