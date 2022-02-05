package com.study.SpringCoreCoursework.coursework2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.SpringCoreCoursework.coursework2.JsonDB;

public class TestJsonDB {
	private static final Path PATH = Paths.get("src/main/java/com/study/SpringCoreCoursework/coursework2/person.json");
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
		JsonDB jsonDB = ctx.getBean("jsonDB",JsonDB.class);
		System.out.println(jsonDB.queryAll());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		boolean check = jsonDB.add(new Person("John",0,sdf.parse("2000/1/1")));
		System.out.println(check);
		List<Person> peoples=jsonDB.queryAll();
		
		//----------------------------------------------------------------------
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		//todayCalendar.setTime(today);
		String todayStr =calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE);
		System.out.println(calendar);
		System.out.println(todayStr);
		//------------------------------------------------------------
		for(Person p:peoples) {
			calendar.setTime(p.getBirth());
			String birthStr =calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE);
			System.out.println(birthStr);
		}
		peoples.stream().filter(p->{
			calendar.setTime(p.getBirth());
			String birthStr =calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE);
			return birthStr.equals("2000/1/1");
		}).forEach(System.out::print);

		
		
		String jsonStr = Files.readAllLines(PATH).stream().collect(Collectors.joining());
		System.out.println(Files.readAllLines(PATH));
		
	}

}
