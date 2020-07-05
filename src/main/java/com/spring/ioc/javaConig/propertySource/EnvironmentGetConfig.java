package com.spring.ioc.javaConig.propertySource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;



@Configuration
public class EnvironmentGetConfig implements EnvironmentAware{
	
	@Autowired
	private Environment env;

    @Override
    public void setEnvironment(Environment environment) {

    }

    //private RelaxedPropertyResolver propertyResolver;
	  
//    @Override
//    public void setEnvironment(Environment env) {
//        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
//    }
//
//    @Bean(destroyMethod = "close", initMethod = "init")
//    @ConfigurationProperties()
//    public DataSource writeDataSource1() {
//        DruidDataSource dataSource = new DruidDataSource();
//
//        return dataSource;
//    }
//
//    @Bean(destroyMethod = "close", initMethod = "init")
//    public DataSource writeDataSource2(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setInitialSize(2);
//        dataSource.setMaxActive(20);
//        dataSource.setMinIdle(0);
//        dataSource.setMaxWait(60000);
//        dataSource.setValidationQuery("SELECT 1");
//        dataSource.setTestOnBorrow(false);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setPoolPreparedStatements(false);
//        return dataSource;
//    }
}
