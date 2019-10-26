package com.spring.ioc.bean.register.beanObject.scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl {

    @Autowired
    private TestDaoImpl dao;

    public TestDaoImpl getDao() {
        return dao;
    }
}
