package com.spring.ioc.appliction.propertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.core.env.PropertyResolver;

public class PropertySourceTest {

	@Test
	public void mapPropertySource() throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("key", "value1");
		PropertySource propertySource1 = new MapPropertySource("resource1", map);
		
		System.out.println(propertySource1.getProperty("key"));
	}

	@Test
	public void propertiesPropertySource() throws IOException {
		Properties props = new Properties();
		props.put("key", "value2");
		PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
				"resource2", props);
		
		System.out.println(propertySource2.getProperty("key"));
	}
	
	@Test
	public void ResourcePropertySource() throws IOException {
		ResourcePropertySource propertySource3 = new ResourcePropertySource("resource3", "classpath:application.properties"); // name,
        System.out.println(propertySource3.getProperty("key"));
	}
	
	
    @Test  
    public void MutablePropertySources() throws IOException {  
    	Map<String, Object> map = new HashMap<>();
		map.put("key", "value1");
		PropertySource propertySource1 = new MapPropertySource("resource1", map);
		
		Properties props = new Properties();
		props.put("key", "value2");
		PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
				"resource2", props);
		
        MutablePropertySources propertySources = new MutablePropertySources();  
        propertySources.addFirst(propertySource1);  
        propertySources.addLast(propertySource2);  
        System.out.println(propertySources.get("propertySource1").getProperty("key"));  
      
        for(PropertySource propertySource : propertySources) {  
            System.out.println(propertySource.getProperty("key"));  
        }  
    } 
    
    @Test  
    public void PropertyResolver() throws Exception {  
    	Map<String, Object> map = new HashMap<>();
		map.put("key", "value1");
		map.put("SPRING_APPLICATION_JSON", "value2");
		map.put("spring.application.json", "value3");
		PropertySource propertySource1 = new MapPropertySource("resource1", map);
		
		Properties props = new Properties();
		props.put("key", "value2");
		PropertiesPropertySource propertySource2 = new PropertiesPropertySource(
				"resource2", props);
		
        MutablePropertySources propertySources = new MutablePropertySources();  
        propertySources.addFirst(propertySource1);  
        propertySources.addLast(propertySource2);  
        System.out.println(propertySources.get("resource1").getProperty("key"));  
      
        PropertyResolver  propertyResolver = new PropertySourcesPropertyResolver(propertySources);  
      
        System.out.println(propertyResolver.getProperty("key"));  
        System.out.println(propertyResolver.getProperty("no", "default"));  
        System.out.println(propertyResolver.resolvePlaceholders("must be encoding ${key}"));  //输出must be encoding gbk
        System.out.println(propertyResolver.resolvePlaceholders("must be encoding ${spring.application.json:${SPRING_APPLICATION_JSON:}}"));
        
        
    }
    
    @Test  
    public void test() {  
        //会自动注册 System.getProperties() 和 System.getenv()  
        Environment environment = new StandardEnvironment();  
        System.out.println(environment.getProperty("file.encoding")); 
        System.out.println(environment.getProperty("spring.application.json")); 
    } 

}
