package com.springcache.service.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.springcache.param.Account;



@Component
public class AnnotationAccountService {

	/**
	 * 在使用注解的时候并没有指定cacheManage只能通过value=xxx， 来确认cacheManage
	 * 向上查找,本例中中我们定义了2个cacheManage
	 * redisCacheManager,ehcacheManager,其中redisCacheManager支持自动生成Cache
	 * 而ehcacheManager是需要通过指定的encache(配置文件中)，所以 当设置encache 走
	 * 
	 * @Cacheable(value="encache", key="#userName") 走ehcacheManager 非encache
	 *                             redisCacheManager 不同名称redis1 redis2相当于
	 * @Cacheable(value="redis1", key="#userName") 走redisCacheManager
	 **/
	@Cacheable(value = "redis", key = "#userName")
	// @Cacheable(value="encache", key="#userName")
	public Account getAccountByName(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		return getFromDB(userName);
	}

	private Account getFromDB(String name) {
		System.out.println("real querying db..." + name);
		return new Account(name);
	}

	@Cacheable(value = "redis1", key = "#userName")
	public Account getAccountByName1(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		return getFromDB(userName);
	}
	@CachePut(value = "redis1", key = "#userName")
	public Account updateAccountByName(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		return getFromDB("update");
	}

	@CacheEvict(value = "redis1",key = "#userName")
	public void deleteAccountByName(String userName) {

	}
	
	@CacheEvict(value = "redis1",allEntries = true)
	public void deleteAll(String userName) {

	}
}
