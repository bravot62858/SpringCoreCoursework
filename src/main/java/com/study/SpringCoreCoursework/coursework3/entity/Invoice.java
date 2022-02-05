package com.study.SpringCoreCoursework.coursework3.entity;

import java.util.Date;
import java.util.List;

public class Invoice {
	private Integer id;
	private Date invdate;
	private List<Item> items;
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Invoice(Date invdate, List<Item> items) {
		super();
		this.invdate = invdate;
		this.items = items;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getInvdate() {
		return invdate;
	}
	public void setInvdate(Date invdate) {
		this.invdate = invdate;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItem(List<Item> items) {
		this.items = items;
	}

}
