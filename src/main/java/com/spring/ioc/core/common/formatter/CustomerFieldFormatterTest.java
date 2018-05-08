package com.spring.ioc.core.common.formatter;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.spring.ioc.core.common.model.FormatterModel;
import com.spring.ioc.core.common.model.PhoneNumberModel;



public class CustomerFieldFormatterTest {

	
	@Test
	public void test() throws SecurityException, NoSuchFieldException {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();//创建格式化服务
		conversionService.addFormatterForFieldAnnotation(new PhoneNumberFormatAnnotationFormatterFactory());//添加自定义的注解格式化工厂
		
		FormatterModel model = new FormatterModel();

		TypeDescriptor descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("phoneNumber"));
		TypeDescriptor stringDescriptor = TypeDescriptor.valueOf(String.class);
		PhoneNumberModel value = (PhoneNumberModel) conversionService.convert("010-12345678", stringDescriptor, descriptor);
		model.setPhoneNumber(value);
		
		Assert.assertEquals("010-12345678", conversionService.convert(model.getPhoneNumber(), descriptor, stringDescriptor));
		
	}
	
}
