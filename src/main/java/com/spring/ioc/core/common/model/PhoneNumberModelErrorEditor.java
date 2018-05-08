package com.spring.ioc.core.common.model;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
//测试时将Error去掉即可（即同包下的【模型类名Editor】可以被Spring根据标准JavaBean规范自动识别）
public class PhoneNumberModelErrorEditor extends PropertyEditorSupport {
	
	Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text == null || !StringUtils.hasLength(text)) {
			//如果没值，设值为null
			setValue(null);
		}
		
		Matcher matcher = pattern.matcher(text);
		if(matcher.matches()) {
			PhoneNumberModel phoneNumber = new PhoneNumberModel();
			phoneNumber.setAreaCode(matcher.group(1));
			phoneNumber.setPhoneNumber(matcher.group(2));
			setValue(phoneNumber);
		} else {
			throw new IllegalArgumentException(String.format("类型转换失败，需要格式[010-12345678]，但格式是[%s]", text));
		}
	}
	
	@Override
	public String getAsText() {
		PhoneNumberModel phoneNumber = ((PhoneNumberModel)getValue());
		return phoneNumber == null ? "" : phoneNumber.getAreaCode() + "-" + phoneNumber.getPhoneNumber();
	}
	

}
