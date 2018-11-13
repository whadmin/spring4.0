package com.springdb.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.springdb.mybatis.enums.DeleteEnum;
import com.springdb.mybatis.enums.TypeEnum;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SysUser implements Serializable {
    private Long id;

    private String name;

    private Integer age;

    private TypeEnum type;

    private Byte version;

    private DeleteEnum isdelete;

    private Date ctime;

    private Date utime;

    private static final long serialVersionUID = 1L;

}