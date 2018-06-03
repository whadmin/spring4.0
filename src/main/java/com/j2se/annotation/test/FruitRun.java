package com.j2se.annotation.test;

import com.j2se.annotation.model.Apple;


public class FruitRun {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        FruitInfoUtil.getFruitInfo(Apple.class);
        
    }

}
