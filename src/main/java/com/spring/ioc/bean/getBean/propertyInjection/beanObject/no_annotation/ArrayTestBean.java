package com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation;

public class ArrayTestBean {
    
    private String[] array;

    private String[][] array2;

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public String[][] getArray2() {
        return array2;
    }

    public void setArray2(String[][] array2) {
        this.array2 = array2;
    }
    
}
