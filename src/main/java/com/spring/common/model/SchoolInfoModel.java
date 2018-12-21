package com.spring.common.model;

public class SchoolInfoModel {

	//学校类型：高中、中专、大学
	private String schoolType;
	//学校名称
	private String schoolName;
	//专业
	private String specialty;
	
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	@Override
	public String toString() {
		return "SchoolInfoModel [schoolType=" + schoolType + ", schoolName="
				+ schoolName + ", specialty=" + specialty + "]";
	}
	
	
	
}
