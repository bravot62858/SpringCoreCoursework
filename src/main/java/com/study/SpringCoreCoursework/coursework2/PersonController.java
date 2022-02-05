package com.study.SpringCoreCoursework.coursework2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
 * 功能:
 * 1.建立 Person 資料
 * 	->輸入name,birth
 * 2.取得 Person 全部資料
 * 	->不用輸入參數
 * 3.根據姓名取得 Person
 * 	->輸入name
 * 4.取得今天生日的 Person
 * 	->輸入今天的日期
 * 5.取得某一歲數以上的 Person
 * 	->輸入age
 * 6.根據姓名來修改 Person 的生日
 *  ->輸入name,birth
 * 7.根據姓名來刪除 Person
 * 	->輸入name
 * */
@Controller
public class PersonController {
	@Autowired
	private PersonService personService;
	
	public void addPerson(String name, Date birth) {
		// 1. 判斷 name 與 birth 是否有資料?
		// 2. 建立 Person 資料
		boolean check = personService.append(name, birth);
		System.out.println("add person: " + check);
	}
	
	public void addPerson(String name,int yyyy,int mm,int dd) {
		//1.判斷name與yyyy/mm/dd是否有資料
		//2.將 yyyy/mm/dd 轉日期格式
		//3.建立 Person 資料
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		
		try {
			Date birth = sdf.parse(yyyy+"/"+mm+"/"+dd);
			addPerson(name, birth);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void printAllPersons() {
		//System.out.println(personService.findAllPersons());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		//資料呈現
		List<Person> people = personService.findAllPersons();
		if(!people.isEmpty()) {
			System.out.println("+-------------+--------+-------------+");
			System.out.println("|  Name       |  Age   |   Birthday  |");
			System.out.println("+-------------+--------+-------------+");
			for(Person p: people) {
				String birthday=sdf.format(p.getBirth());
				System.out.printf("| %-12s|%7d |%12s |\n",p.getName(),p.getAge(),birthday);
				System.out.println("+-------------+--------+-------------+");
			}
		}else {
			System.out.println("查無資料");
		}
	}
	
	
	//根據姓名取得 Person
	public void printPersonByName(String name) {
		Person person=personService.getPerson(name);
		if(person!=null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			String birthday=sdf.format(person.getBirth());
			System.out.println("+-------------+--------+-------------+");
			System.out.println("|  Name       |  Age   |   Birthday  |");
			System.out.println("+-------------+--------+-------------+");
			System.out.printf("| %-12s|%7d |%12s |\n",person.getName(),person.getAge(),birthday);
			System.out.println("+-------------+--------+-------------+");	
		}else {
			System.out.println("查無資料");
		}
	}
	
	//取得今天生日的 Person
	public void printPersonsByBirth(int yyyy,int mm,int dd) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date birth = sdf.parse(yyyy+"/"+mm+"/"+dd);
			List<Person> peoples = personService.getPerson(birth);
			if(!peoples.isEmpty()) {
				System.out.println("+-------------+--------+-------------+");
				System.out.println("|  Name       |  Age   |   Birthday  |");
				System.out.println("+-------------+--------+-------------+");
				for(Person p:peoples) {
					String birthday=sdf.format(p.getBirth());
					System.out.printf("| %-12s|%7d |%12s |\n",p.getName(),p.getAge(),birthday);
					System.out.println("+-------------+--------+-------------+");	
				}
			}else {
				System.out.println("查無資料");
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//取得某一歲數以上的 Person
	public void printPersonsByAge(int age) {
		List<Person> peoples = personService.getPerson(age);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		if(!peoples.isEmpty()) {
			System.out.println("+-------------+--------+-------------+");
			System.out.println("|  Name       |  Age   |   Birthday  |");
			System.out.println("+-------------+--------+-------------+");
			for(Person p:peoples) {
				String birthday=sdf.format(p.getBirth());
				System.out.printf("| %-12s|%7d |%12s |\n",p.getName(),p.getAge(),birthday);
				System.out.println("+-------------+--------+-------------+");	
			}
		} else {
			System.out.println("查無資料");
		}
	}
	
	//根據姓名來修改 Person 的生日
	public void setPersonBirthByName(String name,int yyyy,int mm,int dd) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Date birth;
		List<Person> peoples = personService.findAllPersons();
		try {
			birth = sdf.parse(yyyy+"/"+mm+"/"+dd);
			Optional<Person> setperson = peoples.stream().filter(p->p.getName().equals(name)).findFirst();
			if(setperson.isPresent()) {
				peoples.get(peoples.indexOf(setperson.get())).setBirth(birth);
				personService.update(peoples);
				System.out.println("修改完成");
			}else {
				System.out.println("修改失敗");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//根據姓名來刪除 Person
	public void deletePersonByName(String name) {
		List<Person> peoples = personService.findAllPersons();
		Optional<Person> delepeople = peoples.stream().filter(p->p.getName().equals(name)).findFirst();
		if(delepeople.isPresent()) {
			Person deletePerson = delepeople.get();
			personService.delete(deletePerson);
			System.out.println("刪除完成");
		} else {
			System.out.println("刪除失敗");
		}	
	}
}
