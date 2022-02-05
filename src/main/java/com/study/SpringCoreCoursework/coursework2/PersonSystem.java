package com.study.SpringCoreCoursework.coursework2;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonSystem {

	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
	private PersonController personController= ctx.getBean("personController",PersonController.class);
	private boolean stop;
	private void menu() {
		System.out.println("===================================");
		System.out.println("1.建立 Person 資料");
		System.out.println("2.取得 Person 全部資料");
		System.out.println("3.根據姓名取得 Person");
		System.out.println("4.取得今天生日的 Person");
		System.out.println("5.取得某一歲數以上的 Person");
		System.out.println("6.根據姓名來修改 Person 的生日");
		System.out.println("7.根據姓名來刪除 Person");
		System.out.println("0.離開");
		System.out.println("===================================");
		System.out.println("請選擇");
		//作業2:
		//第3~7是回家作業
		
		Scanner sc=new Scanner(System.in);
		int choice=sc.nextInt();
		switch (choice) {
		case 1:
			createPerson();
			break;
		case 2:
			printPersons();
			break;	
		case 3:
			printPersonByName();
			break;
		case 4:
			printPersonsByBirth();
			break;
		case 5:
			printPersonsByAge();
			break;
		case 6:
			setPersonBirthByName();
			break;
		case 7:
			deletePersonByName();
			break;
		case 0:
			System.out.println("離開系統");
			stop=true;
			break;
		}	
	}
	
	private void createPerson() {
		System.out.print("請輸入姓名 生日年 月 日: ");
		//EX: Jack 1999 5 4
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		int yyyy=sc.nextInt();
		int mm=sc.nextInt();
		int dd=sc.nextInt();
		
		personController.addPerson(name, yyyy, mm, dd);
	}
	
	private void printPersons() {
		personController.printAllPersons();
	}
		
	private void printPersonByName() {
		System.out.println("請輸入姓名: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		personController.printPersonByName(name);
		
	}
	
	private void printPersonsByBirth() {
		System.out.println("請輸入生日年 月 日: ");
		Scanner sc=new Scanner(System.in);
		int yyyy=sc.nextInt();
		int mm=sc.nextInt();
		int dd=sc.nextInt();
		personController.printPersonsByBirth(yyyy, mm, dd);
	}
	
	private void printPersonsByAge() {
		System.out.println("請輸入年齡: ");
		Scanner sc=new Scanner(System.in);
		int age=sc.nextInt();
		personController.printPersonsByAge(age);
	}
	
	private void setPersonBirthByName() {
		System.out.println("請輸入姓名 生日年 月 日: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		int yyyy=sc.nextInt();
		int mm=sc.nextInt();
		int dd=sc.nextInt();
		personController.setPersonBirthByName(name, yyyy, mm, dd);
	}
	
	private void deletePersonByName() {
		System.out.println("請輸入欲刪除的姓名: ");
		Scanner sc=new Scanner(System.in);
		String name = sc.next();
		personController.deletePersonByName(name);
	}
	
	public  void start() {
		while (!stop) {
			menu();
		}
	}
	
	public static void main(String[] args) {
		new PersonSystem().start();
	}

}
