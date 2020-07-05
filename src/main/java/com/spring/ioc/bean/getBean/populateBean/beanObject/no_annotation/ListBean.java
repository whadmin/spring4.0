package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;


import java.util.ArrayList;
import java.util.List;

public class ListBean {

	private List<PopulateBean> list1;

	private ArrayList<PopulateBean> list2;

	public void setList1(List<PopulateBean> list1) {
		this.list1 = list1;
	}

	public void setList2(ArrayList<PopulateBean> list2) {
		this.list2 = list2;
	}

	public List<PopulateBean> getList1() {
		return list1;
	}

	public ArrayList<PopulateBean> getList2() {
		return list2;
	}
}
