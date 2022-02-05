package com.study.SpringCoreCoursework.coursework4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.SpringCoreCoursework.coursework4.controller.BookController;

public class TestBook {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
		BookController bookController = ctx.getBean(BookController.class);
		/*
		case1 買一本
		Integer wid=1;
		Integer bid=1;
		bookController.buyBook(wid, bid);
		*/
		/*
		case2 買多本
		Integer wid=1;
		bookController.buyBooks(wid, 1,1,2,2,2,1);
		*/
		
		/*
		 * 請先建立Table:
		  	create table if not exists order_log(
			oid integer not null auto_increment,
			wid integer not null,
			bid integer not null,
			amount integer default 0,
			price integer default 0,
			total integer default 0,
			buytime timestamp,
			primary key (oid),
			foreign key (wid) references wallet(wid),
			foreign key (bid) references book(bid)
			)
		*/
		bookController.queryOrder();	//會查詢order_log資料表裡面的紀錄，若時間相同表示同一筆訂單。
	
	}

}
