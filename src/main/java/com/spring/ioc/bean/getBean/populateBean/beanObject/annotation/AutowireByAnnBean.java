package com.spring.ioc.bean.getBean.populateBean.beanObject.annotation;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 17:28
 */
@ToString
@Data
public class AutowireByAnnBean {

    @Autowired(required = false)
    private DataSource dataSource1;

    @Resource
    private DataSource dataSource2;
}
