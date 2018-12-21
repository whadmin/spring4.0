package com.spring.common.resource;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.core.io.*;

public class ResourceLoaderTest {

    @Test
    public void testResourceLoad() {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:com/spring/common/resource/test1.txt");
        //验证返回的是ClassPathResource
        Assert.assertEquals(ClassPathResource.class, resource.getClass());

        Resource resource2 = loader.getResource("file:com/spring/common/resource/test1.txt");
        //验证返回的是ClassPathResource
        Assert.assertEquals(UrlResource.class, resource2.getClass());

        Resource resource3 = loader.getResource("com/spring/common/resource/test1.txt");
        //验证返默认可以加载ClasspathResource
        Assert.assertTrue(resource3 instanceof ClassPathResource);
    }
}
