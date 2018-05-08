package com.spring.ioc.core.common.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import com.spring.ioc.core.common.model.PhoneNumberModel;

public class ConversionServiceTest {

	@Test
	public void testStringToPhoneNumberConvert() {
		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(new StringToPhoneNumberConverter());

		String phoneNumberStr = "010-12345678";
		PhoneNumberModel phoneNumber = conversionService.convert(phoneNumberStr, PhoneNumberModel.class);

		Assert.assertEquals(Boolean.valueOf(true),conversionService.convert("1", Boolean.class));

		Assert.assertEquals(4, conversionService.convert("1,2,3,4", List.class).size());

		Assert.assertEquals("010", phoneNumber.getAreaCode());
	}

}
