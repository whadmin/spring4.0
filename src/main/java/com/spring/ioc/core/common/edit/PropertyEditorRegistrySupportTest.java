package com.spring.ioc.core.common.edit;

import java.beans.PropertyEditor;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.propertyeditors.PropertiesEditor;


public class PropertyEditorRegistrySupportTest {
	
	@Test
	public void getDefaultEditor() {
		PropertyEditorRegistrySupport support=new PropertyEditorRegistrySupport();
		//support.registerDefaultEditors();
		PropertyEditor edit=support.getDefaultEditor(File.class);
		System.out.println(edit);
	}
	
	
	@Test
	public void findCustomEditor() {
		PropertyEditorRegistrySupport support=new PropertyEditorRegistrySupport();
		support.registerCustomEditor(UUID.class, new UUIDEditor());
		support.registerCustomEditor(UUID.class,"uuid",new UUIDEditor());
		PropertyEditor edit=support.findCustomEditor(UUID.class,"uuid");
		edit.setAsText(UUID.randomUUID().toString());
		System.out.println(edit.getValue());
		edit=support.findCustomEditor(UUID.class,null);
		edit.setAsText(UUID.randomUUID().toString());
		System.out.println(edit.getValue());
		
		support.registerCustomEditor(Properties.class,"props",new PropertiesEditor());
		support.registerCustomEditor(Properties.class,new PropertiesEditor());
		edit=support.findCustomEditor(Properties.class,"props");
		edit.setAsText("key1=value1");
		System.out.println(edit.getValue());
		edit=support.findCustomEditor(Properties.class,null);
		edit.setAsText("key2=value2");
		System.out.println(edit.getValue());
		
		
		support.registerCustomEditor(Date.class,"date",new Dateditor());
		support.registerCustomEditor(Date.class,new Dateditor());
		edit=support.findCustomEditor(Date.class,"date");
		edit.setAsText("2018-05-21");
		System.out.println(edit.getValue());
		edit=support.findCustomEditor(Date.class,null);
		edit.setAsText("2018-05-22");
		System.out.println(edit.getValue());

	}

}
