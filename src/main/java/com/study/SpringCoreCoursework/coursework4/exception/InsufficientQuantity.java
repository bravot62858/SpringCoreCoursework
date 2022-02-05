package com.study.SpringCoreCoursework.coursework4.exception;

// 庫存量不足
public class InsufficientQuantity extends Exception{

	public InsufficientQuantity(String message) {
		super(message);
	}
	
}
