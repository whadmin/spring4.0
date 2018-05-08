package com.spring.ioc.core.common.formatter;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.spring.ioc.core.common.model.FormatterModel;
import com.spring.ioc.core.common.model.PhoneNumberModel;

public class CustomerFormatterTest {

	@Test
	public void test() {
		
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addFormatter(new PhoneNumberFormatter());

		PhoneNumberModel phoneNumber = new PhoneNumberModel("010", "12345678");
		Assert.assertEquals("010-12345678", conversionService.convert(phoneNumber, String.class));
		
		Assert.assertEquals("010", conversionService.convert("010-12345678", PhoneNumberModel.class).getAreaCode());
		
	}
	
	
}
