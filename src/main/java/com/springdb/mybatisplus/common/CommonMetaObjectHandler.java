package com.springdb.mybatisplus.common;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

/**
 * 自定义填充创建时间，修改时间
 */
public class CommonMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("ctime", new Date(), metaObject);
        this.setFieldValByName("utime", new Date(), metaObject);
        this.setFieldValByName("version", 0, metaObject);
        this.setFieldValByName("delete","N", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	//this.setFieldValByName("utime", new Timestamp(System.currentTimeMillis()), metaObject);
    }
}
