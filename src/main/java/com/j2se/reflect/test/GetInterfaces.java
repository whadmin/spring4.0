package com.j2se.reflect.test;

public class GetInterfaces {
    public static void main(String[] args) {

        try {
            //创建类
            Class<?> class1 = Class.forName("com.reflect.model.Person");

            //获取所有的接口
            Class<?>[] interS = class1.getInterfaces() ;

            for (Class<?> class2 : interS ) {
                System.out.println( class2 );
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
