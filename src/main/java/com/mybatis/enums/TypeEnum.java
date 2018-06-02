package com.mybatis.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum implements BaseEnum<TypeEnum, String> {
	DISABLED("0", "禁用"), NORMAL("1", "正常");

	private String value;

	private String displayName;

	static Map<String, TypeEnum> enumMap = new HashMap<String, TypeEnum>();
	static {
		for (TypeEnum type : TypeEnum.values()) {
			enumMap.put(type.getValue(), type);
		}
	}

	private TypeEnum(String value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public static TypeEnum getEnum(String value) {
		return enumMap.get(value);
	}
}
