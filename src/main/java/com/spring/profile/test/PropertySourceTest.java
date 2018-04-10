package com.spring.profile.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

public class PropertySourceTest {

	@Test
	public void test() throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("encoding", "gbk");
		PropertySource propertySource1 = new MapPropertySource("map", map);
		System.out.println(propertySource1.getProperty("encoding"));

		ResourcePropertySource propertySource2 = new ResourcePropertySource(
				"resource", "classpath:resources.properties"); // name, location
		System.out.println(propertySource2.getProperty("encoding"));
	}

}
