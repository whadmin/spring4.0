package com.spring.ioc.appliction.propertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

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
        System.out.println(propertySource3.getProperty("encoding"));
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

}
