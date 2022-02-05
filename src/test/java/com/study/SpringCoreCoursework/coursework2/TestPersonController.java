package com.study.SpringCoreCoursework.coursework2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPersonController {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
		PersonController personController= ctx.getBean("personController",PersonController.class);
		personController.printAllPersons();
		//personController.addPerson("Bob", 2013, 12, 30);
		personController.printAllPersons();
		
		
		
	}

}
