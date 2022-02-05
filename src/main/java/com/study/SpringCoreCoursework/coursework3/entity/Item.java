package com.study.SpringCoreCoursework.coursework3.entity;

public class Item {
	private Integer id;
	private Integer amount;
	private Integer ipid;
	private Integer invid;
	private ItemProduct itemProduct;
	private Invoice invoice;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getIpid() {
		return ipid;
	}
	public void setIpid(Integer ipid) {
		this.ipid = ipid;
	}
	public Integer getInvid() {
		return invid;
	}
	public void setInvid(Integer invid) {
		this.invid = invid;
	}
	public ItemProduct getItemProduct() {
		return itemProduct;
	}
	public void setItemProduct(ItemProduct itemProduct) {
		this.itemProduct = itemProduct;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	

}
