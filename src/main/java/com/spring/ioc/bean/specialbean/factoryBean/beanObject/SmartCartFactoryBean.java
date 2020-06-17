package com.spring.ioc.bean.specialbean.factoryBean.beanObject;

import org.springframework.beans.factory.SmartFactoryBean;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/17 16:39
 */
public class SmartCartFactoryBean implements SmartFactoryBean<CarBean> {
    private static boolean prototypeCreated;

    private boolean singleton = true;

    private boolean invokegetObject = false;

    private CarBean carBean;

    public SmartCartFactoryBean() {
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

    /**
     * 是否在beanFactory.preInstantiateSingletons()预加载单例工厂Bean时候，创建工厂对象
     */
    public boolean isEagerInit() {
        return true;
    }
}
