package com.spring.ioc.model.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl {

    @Autowired
    private TestDaoImpl dao;

    public TestDaoImpl getDao() {
        return dao;
    }
}
