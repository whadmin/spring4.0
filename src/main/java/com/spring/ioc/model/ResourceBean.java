package com.spring.ioc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResourceBean {
    
    private FileOutputStream fos;
    
    public void init() {
        System.out.println("ResourceBean:========初始化");
    }
    
    public void destroy() {
        System.out.println("ResourceBean:========销毁");
    }
}
