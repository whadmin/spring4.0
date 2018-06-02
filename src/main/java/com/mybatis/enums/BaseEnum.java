package com.mybatis.enums;

public interface BaseEnum<E extends Enum<?>, T> {  
    public T getValue();  
    public String getDisplayName();  
}  