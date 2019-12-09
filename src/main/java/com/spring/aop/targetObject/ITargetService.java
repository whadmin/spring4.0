package com.spring.aop.targetObject;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/6 15:04
 * AOP 目标对象接口
 */
public interface ITargetService {

    public void executeNoParam();

    public void executeExistParam(String param);

    public void executeError(String param) throws Exception;
}
