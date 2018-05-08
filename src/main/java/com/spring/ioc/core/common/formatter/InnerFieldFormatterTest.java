package com.spring.ioc.core.common.formatter;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.spring.ioc.core.common.model.FormatterModel;

public class InnerFieldFormatterTest {

	
	@Test
	public void test() throws SecurityException, NoSuchFieldException {
		//默认自动注册对@NumberFormat和@DateTimeFormat的支持
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		
		//准备测试模型对象
		FormatterModel model = new FormatterModel();
		model.setTotalCount(10000);
		model.setDiscount(0.51);
		model.setSumMoney(10000.13);
		model.setRegisterDate(new Date(2012-1900, 4, 1));
		model.setOrderDate(new Date(2012-1900, 4, 1, 20, 18, 18));
		
		
		
		
		
		//TypeDescriptor：拥有类型信息的上下文，用于Spring3类型转换系统获取类型信息的（可以包含类、字段、方法参数、属性信息）
		//通过TypeDescriptor，我们就可以获取（类、字段、方法参数、属性）的各种信息，如注解类型信息；
		TypeDescriptor descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("totalCount"));
		
		TypeDescriptor stringDescriptor = TypeDescriptor.valueOf(String.class);
		
		Assert.assertEquals("10,000", conversionService.convert(model.getTotalCount(), descriptor, stringDescriptor));
		Assert.assertEquals(model.getTotalCount(), conversionService.convert("10,000", stringDescriptor, descriptor));
		
		descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("discount"));
		Assert.assertEquals("51%", conversionService.convert(model.getDiscount(), descriptor, stringDescriptor));
		Assert.assertEquals(model.getDiscount(), conversionService.convert("51%", stringDescriptor, descriptor));
		
		LocaleContextHolder.setLocale(Locale.CHINA);
		descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("sumMoney"));
		Assert.assertEquals("￥10,000.13", conversionService.convert(model.getSumMoney(), descriptor, stringDescriptor));
		Assert.assertEquals(model.getSumMoney(), conversionService.convert("￥10,000.13", stringDescriptor, descriptor));
		LocaleContextHolder.setLocale(null);
		
		descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("registerDate"));
		Assert.assertEquals("2012-05-01", conversionService.convert(model.getRegisterDate(), descriptor, stringDescriptor));
		Assert.assertEquals(model.getRegisterDate(), conversionService.convert("2012-05-01", stringDescriptor, descriptor));
		
		descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("orderDate"));
		Assert.assertEquals("2012-05-01 20:18:18", conversionService.convert(model.getOrderDate(), descriptor, stringDescriptor));
		Assert.assertEquals(model.getOrderDate(), conversionService.convert("2012-05-01 20:18:18", stringDescriptor, descriptor));
		
		
		
	}
	
}
