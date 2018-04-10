package com.spring.ioc.model;

import java.util.ArrayList;
import java.util.List;

public class ListBean {

	private List<HelloApi> list1;

	private ArrayList<HelloApi> list2;

	public void setList1(List<HelloApi> list1) {
		this.list1 = list1;
	}

	public void setList2(ArrayList<HelloApi> list2) {
		this.list2 = list2;
	}

	public List<HelloApi> getList1() {
		return list1;
	}

	public ArrayList<HelloApi> getList2() {
		return list2;
	}
}
