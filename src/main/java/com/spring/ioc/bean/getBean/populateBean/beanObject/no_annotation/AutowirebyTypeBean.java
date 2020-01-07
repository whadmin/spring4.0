package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

import lombok.Getter;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 14:23
 */
@ToString
@Getter
public class AutowirebyTypeBean {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
