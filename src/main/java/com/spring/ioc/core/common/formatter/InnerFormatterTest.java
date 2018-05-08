package com.spring.ioc.core.common.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.format.number.PercentFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;

public class InnerFormatterTest {

	@Test
	public void testNumber() throws ParseException {
		//一、NumberFormatter：实现通用样式的格式化/解析
		NumberFormatter numberFormatter = new NumberFormatter("#,##0.##");//指定格式化字符串
		//1、将字符串“12,345.12”字符串解析为 BigDecimal("12345.12")
		Assert.assertEquals(new BigDecimal("12345.12"), numberFormatter.parse("12,345.12", Locale.CHINA));
		//2、将BigDecimal("12345.126")格式化为字符串“12,345.13”展示(会进行四舍五入)
		Assert.assertEquals("12,345.13", numberFormatter.print(new BigDecimal("12345.126"), Locale.CHINA));
		
		//二、CurrencyFormatter：实现货币样式的格式化/解析
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2);//保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);//舍入模式（ceilling表示四舍五入）
		//1、将带货币符号的字符串“$123.125”转换为BigDecimal("123.00")
		Assert.assertEquals(new BigDecimal("123.13"), currencyFormatter.parse("$123.125", Locale.US));
		//2、将BigDecimal("123")格式化为字符串“$123.00”展示
		Assert.assertEquals("$123.00", currencyFormatter.print(new BigDecimal("123"), Locale.US));
		Assert.assertEquals("￥123.00", currencyFormatter.print(new BigDecimal("123"), Locale.CHINA));
		Assert.assertEquals("￥123.00", currencyFormatter.print(new BigDecimal("123"), Locale.JAPAN));
		
		//三、PercentFormatter：实现百分数样式的格式化/解析
		PercentFormatter percentFormatter = new PercentFormatter();
		//1、将字符串“12,345.12”字符串解析为 BigDecimal("12345.12")
		Assert.assertEquals(new BigDecimal(".5"), percentFormatter.parse("50%", Locale.US));
		//2、将BigDecimal("12345.126")格式化为字符串“12,345.13”展示(会进行四舍五入)
		Assert.assertEquals("51%", percentFormatter.print(new BigDecimal("0.51"), Locale.US));
	}
	
	@Test
	public void testDate() throws ParseException {
		//DateFormatter：实现日期的格式化/解析
		DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");
		//1、将字符串“2012-05-01”字符串解析为java.util.Date类型
		Assert.assertEquals(new Date(2012-1900, 4, 1), dateFormatter.parse("2012-05-01", Locale.CHINA));
		//2、将java.util.Date类型数据格式化为字符串“2012-05-01”
		Assert.assertEquals("2012-05-01", dateFormatter.print(new Date(2012-1900, 4, 1), Locale.CHINA));
	}
	
	@Test
	public void testWithDefaultFormattingConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		//默认不自动注册任何Formatter
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2);//保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);//舍入模式（ceilling表示四舍五入）
		
		//注册Formatter SPI实现
		conversionService.addFormatter(currencyFormatter);
		
		//绑定Locale信息到ThreadLocal
		//FormattingConversionService内部自动获取作为Locale信息，如果不设值默认是 Locale.getDefault()
		LocaleContextHolder.setLocale(Locale.US);
		Assert.assertEquals("$1,234.13", conversionService.convert(new BigDecimal("1234.128"), String.class));
		LocaleContextHolder.setLocale(null);
		LocaleContextHolder.setLocale(Locale.CHINA);
		Assert.assertEquals("￥1,234.13", conversionService.convert(new BigDecimal("1234.128"), String.class));
		Assert.assertEquals(new BigDecimal("1234.13"), conversionService.convert("￥1,234.13", BigDecimal.class));
		LocaleContextHolder.setLocale(null);
		
		
		
	}
	
	
	
}
