package com.spring.ioc.appliction.profile.model.annotation.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.ioc.appliction.profile.model.CommonService;


@Component
public class CommonServiceImpl implements CommonService {

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Value("#{default.name}")
    public String commonName;


    @Override
    public String sayHello() {
        return String.format("hello,I'm %s,this is a common environment!",
                commonName);
    }

}
