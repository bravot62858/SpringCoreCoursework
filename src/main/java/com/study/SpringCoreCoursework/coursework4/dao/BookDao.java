package com.study.SpringCoreCoursework.coursework4.dao;

import java.util.Date;
import java.util.List;

import com.study.SpringCoreCoursework.coursework4.entity.Order_Log;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientAmount;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientQuantity;

public interface BookDao {
	Integer getPrice(Integer bid);
	Integer getStockAmount(Integer bid);
	Integer getWalletMoney(Integer wid);
	Integer updateStock(Integer bid,Integer amount) throws InsufficientQuantity;	//減去庫存
	Integer updateWallet(Integer wid,Integer money) throws InsufficientAmount;	//減去金額

	Integer addOrder(Integer wid,Integer bid,Integer amount,Integer price,Date buytime);
	
	List<Order_Log> queryOrder();
}
