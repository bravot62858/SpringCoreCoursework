package com.study.SpringCoreCoursework.coursework2;

import java.util.Date;
import java.util.List;

public interface PersonService {
	
	boolean append(String name,Date birth);
	boolean append(Person person);
	List<Person> findAllPersons();
	Person getPerson(String name);
	
	List<Person> getPerson(Date birth);
	List<Person> getPerson(int age);
	
	boolean update(List<Person> peoples);
	
	boolean delete(Person person);
}
