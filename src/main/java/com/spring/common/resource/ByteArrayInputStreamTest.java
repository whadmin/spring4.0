package com.spring.common.resource;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ByteArrayInputStreamTest{

    @Test
    public void testInputStreamResource() throws IOException {
        InputStream is = new ByteArrayInputStream("testString".getBytes());
        Resource resource = new InputStreamResource(is);
        assertTrue(resource.exists());
        assertTrue(resource.isOpen());
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("testString", content);
        assertEquals(resource, new InputStreamResource(is));
    }

    @Test
    public void testInputStreamResourceWithDescription() throws IOException {
        InputStream is = new ByteArrayInputStream("testString".getBytes());
        Resource resource = new InputStreamResource(is, "my description");
        assertTrue(resource.exists());
        assertTrue(resource.isOpen());
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("testString", content);
        assertTrue(resource.getDescription().contains("my description"));
        assertEquals(resource, new InputStreamResource(is));
    }
}
