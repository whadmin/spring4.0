package com.spring.ioc.bean.specialbean.factoryBean.beanObject;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

@Data
public class CarFactoryBean implements FactoryBean<CarBean> {

    private static boolean prototypeCreated;

    private boolean singleton = true;


    private CarBean carBean;

    public CarFactoryBean() {
        this.carBean = new CarBean("singleton");
    }

    @Override
    public CarBean getObject() throws Exception {
        if (isSingleton()) {
            return this.carBean;
        } else {
            CarBean prototype = new CarBean("prototype");
            prototypeCreated = true;
            return prototype;
        }
    }

    /**
     * 重置
     */
    public static void reset() {
        prototypeCreated = false;
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
