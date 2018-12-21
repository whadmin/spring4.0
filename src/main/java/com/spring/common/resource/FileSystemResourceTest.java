package com.spring.common.resource;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileSystemResourceTest{

    @Test
    public void testFileResource() {
        File file = new File("d:/test.txt");
        Resource resource = new FileSystemResource(file);
        Assert.assertEquals(false, resource.isOpen());
    }

    @Test
    public void testFileSystemResourceWithRelativePath() throws IOException {
        Resource resource = new FileSystemResource("dir/");
        Resource relative = resource.createRelative("subdir");
        assertEquals(new FileSystemResource("dir/subdir"), relative);
    }
}
