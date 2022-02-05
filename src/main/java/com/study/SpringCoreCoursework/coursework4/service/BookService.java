package com.study.SpringCoreCoursework.coursework4.service;

import java.util.Date;
import java.util.List;

import com.study.SpringCoreCoursework.coursework4.entity.Order_Log;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientAmount;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientQuantity;

public interface BookService {
	void buyOne(Integer wid, Integer bid) throws InsufficientAmount,InsufficientQuantity;
	void buyMany(Integer wid, Integer... bids) throws InsufficientAmount,InsufficientQuantity;
	void addOrder(Integer wid,Integer bid,Integer amount,Integer price,Date buytime);
	List<Order_Log> queryOrder();
}
