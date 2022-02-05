package com.study.SpringCoreCoursework.coursework2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl implements PersonDao {
	
	@Autowired
	private JsonDB jsonDB;
	
	@Override
	public boolean create(Person person) {
		Boolean check = null;
		try {
			check = jsonDB.add(person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			check = false;
		}
		return check;
	}

	@Override
	public List<Person> readAll() {
		List<Person> people = null;
		try {
			people = jsonDB.queryAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return people;
	}

	@Override
	public boolean update(List<Person> peoples) {
		Boolean check=null;
		try {
			check= jsonDB.set(peoples);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			check=false;
		}
		return check;
	}

	@Override
	public boolean delete(Person person) {
		Boolean check = null;
		try {
			check = jsonDB.delete(person);
		} catch (Exception e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}
}
