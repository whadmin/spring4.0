package com.spring.common.util;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileCopyUtilsTests {

    @Test
    public void copyFromInputStream() throws IOException {
        byte[] content = "content".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        ByteArrayOutputStream out = new ByteArrayOutputStream(content.length);
        int count = FileCopyUtils.copy(in, out);
        assertEquals(content.length, count);
        assertTrue(Arrays.equals(content, out.toByteArray()));
    }

    @Test
    public void copyFromByteArray() throws IOException {
        byte[] content = "content".getBytes();
        ByteArrayOutputStream out = new ByteArrayOutputStream(content.length);
        FileCopyUtils.copy(content, out);
        assertTrue(Arrays.equals(content, out.toByteArray()));
    }

    @Test
    public void copyToByteArray() throws IOException {
        byte[] content = "content".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        byte[] result = FileCopyUtils.copyToByteArray(in);
        assertTrue(Arrays.equals(content, result));
    }

    @Test
    public void copyFromReader() throws IOException {
        String content = "content";
        StringReader in = new StringReader(content);
        StringWriter out = new StringWriter();
        int count = FileCopyUtils.copy(in, out);
        assertEquals(content.length(), count);
        assertEquals(content, out.toString());
    }

    @Test
    public void copyFromString() throws IOException {
        String content = "content";
        StringWriter out = new StringWriter();
        FileCopyUtils.copy(content, out);
        assertEquals(content, out.toString());
    }

    @Test
    public void copyToString() throws IOException {
        String content = "content";
        StringReader in = new StringReader(content);
        String result = FileCopyUtils.copyToString(in);
        assertEquals(content, result);
    }
}
