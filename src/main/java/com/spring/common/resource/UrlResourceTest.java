package com.spring.common.resource;

import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import static org.junit.Assert.assertTrue;

public class
UrlResourceTest {
    /**
     * 读取URL访问资源
     */
    @Test
    public void testJavaUrl() throws Exception {
        Lists.newArrayList(new URL("http://www.runoob.com/index.html?language=cn#j2se"), new URL("jar:file:D:/settings.jar!/installed.txt"), new URL("file:d:/test.txt")).forEach(url -> {
            System.out.println("URL 为：" + url.toString());
            System.out.println("协议为：" + url.getProtocol());
            System.out.println("验证信息：" + url.getAuthority());
            System.out.println("文件名及请求参数：" + url.getFile());
            System.out.println("主机名：" + url.getHost());
            System.out.println("路径：" + url.getPath());
            System.out.println("端口：" + url.getPort());
            System.out.println("默认端口：" + url.getDefaultPort());
            System.out.println("请求参数：" + url.getQuery());
            System.out.println("定位位置：" + url.getRef());
            try {
                URLConnection con = url.openConnection();
                String content = FileCopyUtils.copyToString(new InputStreamReader(con.getInputStream(), "UTF-8"));
                System.out.println(content);
                System.out.println("--------------------------------------------");
            } catch (IOException e) {
            }
        });
    }

    /**
     * 读取URI访问资源
     */
    @Test
    public void testJavaUri() throws Exception {
        Lists.newArrayList(new URL("http://www.runoob.com/index.html?language=cn#j2se").toURI(), new URL("jar:file:D:/settings.jar!/installed.txt").toURI(), new URL("file:d:/test.txt").toURI()).forEach(uri -> {
            System.out.println("scheme             : " + uri.getScheme());
            System.out.println("SchemeSpecificPart : " + uri.getSchemeSpecificPart());
            System.out.println("Authority          : " + uri.getAuthority());
            System.out.println("host               : " + uri.getHost());
            System.out.println("port               : " + uri.getPort());
            System.out.println("path               : " + uri.getPath());
            System.out.println("query              : " + uri.getQuery());
            System.out.println("fragment           : " + uri.getFragment());
        });
    }


    @Ignore
    @Test
    public void testUrlResource() throws Exception {

        /*** 通过URL创建UrlResource ***/
        Lists.newArrayList(new URL("http://www.runoob.com/index.html?language=cn#j2se"), new URL("jar:file:D:/settings.jar!/installed.txt"), new URL("file:d:/test.txt")).forEach(url -> {

            try {
                Resource resource = new UrlResource(url);
                doTest(resource);
                System.out.println("--------------------------------------------");
            } catch (IOException e) {
            }
        });

        /*** 通过URL链接UrlResource ***/
        Lists.newArrayList("http://www.runoob.com/index.html?language=cn#j2se", "jar:file:D:/settings.jar!/installed.txt", "file:d:/test.txt").forEach(url -> {
            try {
                Resource resource = new UrlResource(url);
                doTest(resource);
                System.out.println("--------------------------------------------");
            } catch (IOException e) {
            }
        });

        /*** 通过URI创建UrlResource ***/
        Lists.newArrayList(new URL("http://www.runoob.com/index.html?language=cn#j2se").toURI(), new URL("jar:file:D:/settings.jar!/installed.txt").toURI(), new URL("file:d:/test.txt").toURI()).forEach(uri -> {
            try {
                Resource resource = new UrlResource(uri);
                doTest(resource);
                System.out.println("--------------------------------------------");
            } catch (IOException e) {
            }
        });
    }

    private void doTest(Resource resource) throws IOException {
        EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
        String content = FileCopyUtils.copyToString(encodedResource.getReader());
        System.out.println(content);
        assertTrue(resource.exists());
    }
}
