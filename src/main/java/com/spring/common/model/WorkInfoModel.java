package com.spring.common.model;

public class WorkInfoModel {
	
	//所在城市
	private String city;
	
	//职位
	private String job;
	
	//工作年限
	private String year;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "WorkInfoModel [city=" + city + ", job=" + job + ", year="
				+ year + "]";
	}
	
	
	
}
