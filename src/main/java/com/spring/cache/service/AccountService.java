package com.spring.cache.service;

import org.springframework.stereotype.Component;

import com.spring.cache.param.Account;

@Component
public class AccountService {

	public Account getAccountByName(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		return getFromDB(userName);
	}

	public Account updateAccountByName(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		return getFromDB("update");
	}

	public void deleteAccountByName(String userName) {

	}

	private Account getFromDB(String name) {
		System.out.println("real querying db..." + name);
		return new Account(name);
	}
}
