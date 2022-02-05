package com.study.SpringCoreCoursework.coursework2;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
	private PersonDao personDao;
	@Override
	public boolean append(String name, Date birth) {
		Person person = new Person();
		person.setName(name);
		person.setBirth(birth);
		return append(person);
	}

	@Override
	public boolean append(Person person) {
		
		return personDao.create(person);
	}

	@Override
	public List<Person> findAllPersons() {
		
		return personDao.readAll();
	}

	@Override
	public Person getPerson(String name) {
		List<Person> people = findAllPersons();
		Optional<Person> optPerson = people.stream().filter(p->p.getName().equals(name)).findFirst();
		return optPerson.isPresent()? optPerson.get():null;
	}

	@Override
	public List<Person> getPerson(Date birth) {
		List<Person> people = findAllPersons();
		return people.stream().filter(p->p.getBirth().equals(birth)).collect(Collectors.toList());
	}

	@Override
	public List<Person> getPerson(int age) {
		List<Person> people = findAllPersons();
		return people.stream().filter(p->p.getAge()>=age).collect(Collectors.toList());
	}

	@Override
	public boolean update(List<Person> peoples) {
		return personDao.update(peoples);
	}

	@Override
	public boolean delete(Person person) {
		return personDao.delete(person);
	}
}
