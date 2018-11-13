package com.springdb.mybatis.enums;

import java.util.HashMap;
import java.util.Map;

public enum DeleteEnum implements BaseEnum<DeleteEnum, String> {
	Y("0", "已经删除"), N("1", "未删除");

	private String value;

	private String displayName;

	static Map<String, DeleteEnum> enumMap = new HashMap<String, DeleteEnum>();
	static {
		for (DeleteEnum type : DeleteEnum.values()) {
			enumMap.put(type.getValue(), type);
		}
	}

	private DeleteEnum(String value, String displayName) {
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

	public static DeleteEnum getEnum(String value) {
		return enumMap.get(value);
	}
}
