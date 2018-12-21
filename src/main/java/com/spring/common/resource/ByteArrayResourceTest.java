package com.spring.common.resource;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ByteArrayResourceTest{

    @Test
    public void testByteArrayResource() throws IOException {
        Resource resource = new ByteArrayResource("testString".getBytes());
        assertTrue(resource.exists());
        assertFalse(resource.isOpen());
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("testString", content);
        assertEquals(resource, new ByteArrayResource("testString".getBytes()));
    }

    @Test
    public void testByteArrayResourceWithDescription() throws IOException {
        Resource resource = new ByteArrayResource("testString".getBytes(), "my description");
        assertTrue(resource.exists());
        assertFalse(resource.isOpen());
        String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        assertEquals("testString", content);
        assertTrue(resource.getDescription().contains("my description"));
        assertEquals(resource, new ByteArrayResource("testString".getBytes()));
    }


}
