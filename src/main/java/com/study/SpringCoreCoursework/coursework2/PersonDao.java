package com.study.SpringCoreCoursework.coursework2;

import java.util.List;

public interface PersonDao {
	//建立Person
	public boolean create(Person person);
	//查詢所有Person
	public List<Person> readAll();
	//修改後的List回寫Json
	public boolean update(List<Person> peoples);
	//刪除不要的Person
	public boolean delete(Person person);
}
