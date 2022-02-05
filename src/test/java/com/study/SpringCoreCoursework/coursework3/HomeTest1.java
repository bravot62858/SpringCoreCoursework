package com.study.SpringCoreCoursework.coursework3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.SpringCoreCoursework.coursework3.entity.Emp;
import com.study.SpringCoreCoursework.coursework3.template.EmpDao;
import com.study.SpringCoreCoursework.coursework3.template.EmpJobDao;
import com.study.SpringCoreCoursework.coursework3.template.HomeWorkDao;

public class HomeTest1 {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
		HomeWorkDao homeWorkDao = ctx.getBean("homeWorkDao", HomeWorkDao.class);
		//每一張發票有那些商品?	
		homeWorkDao.queryProductNameInInvoice(); 
		//每一張發票有幾件商品?
		System.out.println("============================================================");
		homeWorkDao.queryProductCountInInvoice(); 
		//每一張發票價值多少?	
		System.out.println("============================================================");
		homeWorkDao.queryInvoiceIsHowMuch();  
		//每一樣商品各賣了多少?	
		System.out.println("============================================================");
		homeWorkDao.queryItemProductMoney();  
		//哪一件商品賣得錢最多?	
		System.out.println("============================================================");
		homeWorkDao.queryMaxItemProductMoney(); 
		//哪一張發票價值最高?	
		System.out.println("============================================================");
		homeWorkDao.queryMaxInvoiceMoney(); 
			
	}
	
}
