package com.study.SpringCoreCoursework.coursework4.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.study.SpringCoreCoursework.coursework4.dao.BookDao;
import com.study.SpringCoreCoursework.coursework4.entity.Order_Log;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientAmount;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientQuantity;
@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookDao bookdao;
	private Boolean buyone = true;
	
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {InsufficientAmount.class,InsufficientQuantity.class},
			noRollbackFor = {ArithmeticException.class})
	// getConnection() , setAutoCommit(false), commit()
	@Override
	public void buyOne(Integer wid, Integer bid) throws InsufficientAmount,InsufficientQuantity{
		// 減去一本庫存
		bookdao.updateStock(bid, 1);
		// 取得書籍價格
		//System.out.println(10/0);	//產生 ArithmeticException 錯誤 (根據上面的設定，資料庫不做回滾)
		Integer price = bookdao.getPrice(bid);
		// 減去錢包的金額
		bookdao.updateWallet(wid, price);
		//Log...
		if(buyone) {
			Date buydate = new Date();
		addOrder(wid, bid, 1, price,buydate );
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {InsufficientAmount.class,InsufficientQuantity.class},
			noRollbackFor = {ArithmeticException.class})
	@Override
	public void buyMany(Integer wid, Integer... bids) throws InsufficientAmount,InsufficientQuantity{
		buyone = false;
		// 重複執行 buyOne
		for(Integer bid:bids) {
			buyOne(wid, bid);
		}
		// Log...
		List<Integer> manyBook = Arrays.asList(bids);
		List<Integer> bookList =manyBook.stream().distinct().collect(Collectors.toList());
		for(int list:bookList) {
			int count=0;
			for(int book:manyBook) {
				if(book==list) {
					count=count+1;
				}
			}
			addOrder(wid, list, count, bookdao.getPrice(list), new Date());
		}
		buyone = true;
	}

	@Override
	public void addOrder(Integer wid, Integer bid, Integer amount, Integer price, Date buytime) {
		bookdao.addOrder(wid, bid, amount, price, buytime);
	}

	@Override
	public List<Order_Log> queryOrder() {
		return bookdao.queryOrder();
	}

}
