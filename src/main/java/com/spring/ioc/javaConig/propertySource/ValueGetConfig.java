package com.spring.ioc.javaConig.propertySource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class ValueGetConfig {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Bean(destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties("spring.datasource")
	public DataSource readDataSource1() {
		DruidDataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
	
    @Bean(destroyMethod = "close", initMethod = "init")  
    public DataSource readDataSource2(){  
        DruidDataSource dataSource = new DruidDataSource();  
        dataSource.setUrl(url);  
        dataSource.setUsername(username);//用户名  
        dataSource.setPassword(password);//密码  
        dataSource.setDriverClassName(driverClassName);  
        dataSource.setInitialSize(2);  
        dataSource.setMaxActive(20);  
        dataSource.setMinIdle(0);  
        dataSource.setMaxWait(60000);  
        dataSource.setValidationQuery("SELECT 1");  
        dataSource.setTestOnBorrow(false);  
        dataSource.setTestWhileIdle(true);  
        dataSource.setPoolPreparedStatements(false);  
        return dataSource;  
    }

}
