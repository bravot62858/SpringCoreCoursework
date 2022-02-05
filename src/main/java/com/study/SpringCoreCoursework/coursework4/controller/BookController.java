package com.study.SpringCoreCoursework.coursework4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.study.SpringCoreCoursework.coursework4.entity.Order_Log;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientAmount;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientQuantity;
import com.study.SpringCoreCoursework.coursework4.service.BookService;

@Controller
public class BookController {
	@Autowired
	private BookService bookService;
	
	public void buyBook(Integer wid, Integer bid) {
		try {
			bookService.buyOne(wid, bid);
			System.out.println("單筆 buyBook OK!");			
		} catch (InsufficientQuantity e) {
			System.err.println("庫存不足 "+e);
		} catch (InsufficientAmount e) {
			System.err.println("金額不足: "+e);
		}
	}
	
	public void buyBooks(Integer wid, Integer... bids) {
		
		try {
			bookService.buyMany(wid, bids);
			System.out.println("多筆 buyBooks OK!");			
		} catch (InsufficientQuantity e) {
			System.err.println("庫存不足 "+e);
		} catch (InsufficientAmount e) {
			System.err.println("金額不足: "+e);
		}
	}
	
	public void queryOrder() {
		List<Order_Log> order_Logs = bookService.queryOrder();
		for(Order_Log ol:order_Logs) {
			System.out.println(ol.getWallet().getWname()+" 在 "+ol.getBuytime()+" 買了 "+ol.getBook().getBname()+" "+ol.getAmount()+" 本，共 "+ol.getTotal()+" 元");
		}
	}
	
}
