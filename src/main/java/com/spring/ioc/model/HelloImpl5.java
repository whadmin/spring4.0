package com.spring.ioc.model;


import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;

public abstract class HelloImpl5 implements HelloApi {
    
    private SingletonBean singletonBean;
    
    @Override
    public void sayHello() {
//        singletonBean.print("setter");
//        createPrototypePrinter().print("prototype");
//        createSingletonPrinter().print("singleton");
    }
    
    public abstract SingletonBean createPrototypePrinter();
        

    public SingletonBean createSingletonPrinter() {
        System.out.println("该方法不会被执行，如果输出就错了");
        return new SingletonBean();
    }
    
    public void setSingletonBean(SingletonBean singletonBean) {
        this.singletonBean = singletonBean;
    }
}
