package com.spring.ioc.core.common.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.spring.ioc.core.common.model.PhoneNumberModel;

//String----->PhoneNumberModel
public class StringToPhoneNumberConverter implements
		Converter<String, PhoneNumberModel> {

	Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");

	@Override
	public PhoneNumberModel convert(String source) {

		if (!StringUtils.hasLength(source)) {
			// ①如果source为空 返回null
			return null;
		}

		Matcher matcher = pattern.matcher(source);
		if (matcher.matches()) {
			// ②如果匹配 进行转换
			PhoneNumberModel phoneNumber = new PhoneNumberModel();
			phoneNumber.setAreaCode(matcher.group(1));
			phoneNumber.setPhoneNumber(matcher.group(2));
			return phoneNumber;
		} else {
			// ③如果不匹配 转换失败
			throw new IllegalArgumentException(String.format(
					"类型转换失败，需要格式[010-12345678]，但格式是[%s]", source));
		}
	}

}