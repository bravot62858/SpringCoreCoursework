package com.study.SpringCoreCoursework.coursework2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.SpringCoreCoursework.coursework2.JsonDB;

public class TestPersonDao {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
		PersonDao personDao= ctx.getBean("personDaoImpl",PersonDaoImpl.class);
		System.out.println(personDao.readAll());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		boolean check = personDao.create(new Person("Nick",0,sdf.parse("2020/3/1")));
		System.out.println(check);
		System.out.println(personDao.readAll());
		
		
	}

}
