package com.spring.ioc.bean.specialbean.factoryBean.beanObject;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

@Data
public class CarFactoryBean implements FactoryBean<CarBean> {

    private static boolean prototypeCreated;

    private boolean singleton = true;

    private static boolean invokegetObject = false;

    private CarBean carBean;

    public CarFactoryBean() {
        this.carBean = new CarBean("singleton");
    }

    @Override
    public CarBean getObject() throws Exception {
        invokegetObject = true;
        if (isSingleton()) {
            return this.carBean;
        } else {
            CarBean prototype = new CarBean("prototype");
            prototypeCreated = true;
            return prototype;
        }
    }

    public static boolean wasinvokegetObject() {
        return invokegetObject;
    }

    /**
     * 重置
     */
    public static void reset() {
        prototypeCreated = false;
        invokegetObject=false;
    }

    /**
     * 返回此FactoryBean创建的对象的类型，
     */
    @Override
    public Class<CarBean> getObjectType() {
        return CarBean.class;
    }

    /**
     * 判断FactoryBean管理的对象是否是单例
     */
    @Override
    public boolean isSingleton() {
        return singleton;
    }
}
