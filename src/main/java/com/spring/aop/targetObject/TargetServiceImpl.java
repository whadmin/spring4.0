package com.spring.aop.targetObject;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/6 15:06
 * AOP 目标实现类
 */
public class TargetServiceImpl implements ITargetService {


    @Override
    public void executeNoParam() {
        System.out.println("============execute============ ");
    }

    @Override
    public void executeExistParam(String param) {
        System.out.println("============execute " + param);
    }

    @Override
    public void executeError(String param) throws Exception {
        throw new Exception();
    }
}
