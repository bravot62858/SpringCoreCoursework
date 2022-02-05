package com.study.SpringCoreCoursework.coursework3;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.study.SpringCoreCoursework.coursework3.template.EmpDao;

public class TemplateTest1 {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
		EmpDao empDao = ctx.getBean("empDao",EmpDao.class);
		
		
		/*
		HomeWork 利用 AOP
		在每次的 查詢 queryAll() 都可以將 查詢時間的 Log 記錄下來
		+-------------+--------------------+
		| method_name |   log_timestamp    |
		+-------------+--------------------+
		| queryAll    | 2022/1/10 13:50:43 |
		+-------------+--------------------+
		| queryAll    | 2022/1/11 13:51:43 |
		+-------------+--------------------+
		| queryAll    | 2022/1/12 13:52:43 |
		+-------------+--------------------+
		*/
		/*先在finweb資料庫建立LOG資料表
		 * 
		 	create table if not exists log (
			lid int not null auto_increment, 
			lname varchar(50) not null, -- 存放方法名稱
			ldate timestamp default current_timestamp, -- 建檔時間
			primary key(lid)
			)
		  
		 
		//jdbc-config.xml加上<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
		ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
		EmpDao empDao = ctx.getBean("empDao",EmpDao.class);
		*/	
		System.out.println(empDao.queryAll());  //執行queryAll()，會記錄方法名稱到Log資料表並記錄時間。
		empDao.queryAllLog();	//從LOG資料表抓資料顯示
		
	}
	
}
