package com.mybatis.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.mybatis.enums.TypeEnum;

/**
 * 系统用户表
 */
@Getter
@Setter
@ToString
public class User  {

	
    private Integer id;

    /**
     * 用户名
     */
    private String name;
    /**
     * 通用枚举测试
     */
    private TypeEnum type;
    /**
     * 用户年龄
     */
    private Integer age;
    /**
     * 自定义填充的创建时间
     */
    private Date ctime;

}
