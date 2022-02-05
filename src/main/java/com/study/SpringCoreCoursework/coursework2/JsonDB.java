package com.study.SpringCoreCoursework.coursework2;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class JsonDB { //Json資料庫
	
	@Autowired
	private Gson gson;
	
	//Json file 資料庫存放地
	private static final Path PATH = Paths.get("src/main/java/com/study/SpringCoreCoursework/coursework2/person.json");
	
	//查詢全部
	public List<Person> queryAll() throws Exception {
		String jsonStr = Files.readAllLines(PATH).stream().collect(Collectors.joining());
		Type type = new TypeToken<ArrayList<Person>>() {}.getType();
		List<Person> people = gson.fromJson(jsonStr, type);
		//請將 age 變為最新年齡
		//for迴圈將今年-birth
		/*
		Date today = new Date();
		LocalDate todayLocateDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		for(Person p:people) {
			LocalDate birthLocalDate = p.getBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int age = todayLocateDate.getYear()-birthLocalDate.getYear();
			p.setAge(age);
		}*/
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int todayYear = calendar.get(Calendar.YEAR);
		for(Person p:people) {
			calendar.setTime(p.getBirth());
			int birthYear = calendar.get(Calendar.YEAR);
			int age = todayYear - birthYear;
			p.setAge(age);
		}
		return people;
	}
	
	public boolean add(Person person) throws Exception {
		boolean check = false;
		List<Person> people = queryAll();
		//作業1:
		//如果person已存在則return false
		//name,age,birth 皆與目前資料庫的person資料相同
		//person先更新年齡
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int todayYear = calendar.get(Calendar.YEAR);
		calendar.setTime(person.getBirth());
		int birthYear = calendar.get(Calendar.YEAR);
		person.setAge(todayYear-birthYear);
		//-------------------
		check= people.stream().allMatch(p->p.hashCode()!=person.hashCode());
		if(check) {
				people.add(person);
				String newJsonString = gson.toJson(people);
				Files.write(PATH, newJsonString.getBytes("UTF-8"));	
		}
		return check;
	}
	//修改
	public boolean set(List<Person> peoples) throws Exception {
		Boolean check=null;
		if(peoples!=null) {
			String newJsonString = gson.toJson(peoples);
			Files.write(PATH, newJsonString.getBytes("UTF-8"));	
			check= true;
		}
		return check;
	}
	//刪除
	public boolean delete(Person person) throws Exception {
		Boolean check=null;
		List<Person> peoples = queryAll();
		peoples.remove(person);
		String newJsonString = gson.toJson(peoples);
		Files.write(PATH, newJsonString.getBytes("UTF-8"));
		check=true;
		return check;
	}
}
