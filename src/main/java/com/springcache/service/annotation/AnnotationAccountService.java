package com.springcache.service.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.springcache.param.Account;



@Component
public class AnnotationAccountService {
	
	  @Cacheable(value="default", key="#userName")
	  public Account getAccountByName(String userName) { 
	    // 方法内部实现不考虑缓存逻辑，直接实现业务
	    System.out.println("real query account."+userName); 
	    return getFromDB(userName); 
	  } 
	  
	  private Account getFromDB(String name) { 
	    System.out.println("real querying db..."+name); 
	    return new Account(name); 
	  } 
	}
