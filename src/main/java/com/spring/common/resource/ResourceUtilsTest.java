package com.spring.common.resource;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceUtilsTest {

    @Test
    public void getFile() throws IOException {
        File file=ResourceUtils.getFile("classpath:com/spring/common/resource/test1.properties");
        System.out.println(new String(FileCopyUtils.copyToByteArray(file)));

        File file1=ResourceUtils.getFile("file:d:/test.txt");
        System.out.println(new String(FileCopyUtils.copyToByteArray(file1),Charset.forName("UTF-8")));

        File file2=ResourceUtils.getFile("d:/test.txt");
        System.out.println(new String(FileCopyUtils.copyToByteArray(file2),Charset.forName("UTF-8")));

    }


    @Test
    public void isJarURL() throws Exception {
        assertTrue(ResourceUtils.isJarURL(new URL("jar:file:myjar.jar!/mypath")));
        assertTrue(ResourceUtils.isJarURL(new URL(null, "zip:file:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertTrue(ResourceUtils.isJarURL(new URL(null, "wsjar:file:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertTrue(ResourceUtils.isJarURL(new URL(null, "jar:war:file:mywar.war*/myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertFalse(ResourceUtils.isJarURL(new URL("file:myjar.jar")));
        assertFalse(ResourceUtils.isJarURL(new URL("http:myserver/myjar.jar")));
    }

    @Test
    public void extractJarFileURL() throws Exception {
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL("jar:file:myjar.jar!/mypath")));
        assertEquals(new URL("file:/myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL(null, "jar:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL(null, "zip:file:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL(null, "wsjar:file:myjar.jar!/mypath", new DummyURLStreamHandler())));

        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL("file:myjar.jar")));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL("jar:file:myjar.jar!/")));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL(null, "zip:file:myjar.jar!/", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractJarFileURL(new URL(null, "wsjar:file:myjar.jar!/", new DummyURLStreamHandler())));
    }

    @Test
    public void extractArchiveURL() throws Exception {
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL("jar:file:myjar.jar!/mypath")));
        assertEquals(new URL("file:/myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL(null, "jar:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL(null, "zip:file:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL(null, "wsjar:file:myjar.jar!/mypath", new DummyURLStreamHandler())));
        assertEquals(new URL("file:mywar.war"),
                ResourceUtils.extractArchiveURL(new URL(null, "jar:war:file:mywar.war*/myjar.jar!/mypath", new DummyURLStreamHandler())));

        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL("file:myjar.jar")));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL("jar:file:myjar.jar!/")));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL(null, "zip:file:myjar.jar!/", new DummyURLStreamHandler())));
        assertEquals(new URL("file:myjar.jar"),
                ResourceUtils.extractArchiveURL(new URL(null, "wsjar:file:myjar.jar!/", new DummyURLStreamHandler())));
        assertEquals(new URL("file:mywar.war"),
                ResourceUtils.extractArchiveURL(new URL(null, "jar:war:file:mywar.war*/myjar.jar!/", new DummyURLStreamHandler())));
    }

    private static class DummyURLStreamHandler extends URLStreamHandler {

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            throw new UnsupportedOperationException();
        }
    }
}
