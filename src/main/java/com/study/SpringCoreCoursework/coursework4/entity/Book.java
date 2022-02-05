package com.study.SpringCoreCoursework.coursework4.entity;

import java.util.Date;

public class Book {
	private Integer bid;
	private String bname;
	private Integer price;
	private Date ct;
	
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Date getCt() {
		return ct;
	}
	public void setCt(Date ct) {
		this.ct = ct;
	}

	
}
