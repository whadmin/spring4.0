package com.springdb.mybatis.model;

import java.io.Serializable;

/**
 * Role
 * description TODO
 * create by lxj 2018/4/26
 **/
public class Role implements Serializable {
    private static final long seralVersionUID = 1L;

    private long id;
    private String roleName;
    private String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
