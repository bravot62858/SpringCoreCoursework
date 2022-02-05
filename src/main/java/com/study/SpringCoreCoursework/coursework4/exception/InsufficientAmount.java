package com.study.SpringCoreCoursework.coursework4.exception;

// 餘額不足
public class InsufficientAmount extends Exception{

	public InsufficientAmount(String message) {
		super(message);
	}

}
