package com.study.SpringCoreCoursework.coursework1;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.ls.LSOutput;

public class Test1 {

	public static void main(String[] args) {
		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext1.xml");
					
		//mary的老師是誰? 印出Name
		Student mary=ctx.getBean("s2",Student.class);
		Set<Teacher> teachers2= new LinkedHashSet<Teacher>();
		Set<Teacher> teachers= new LinkedHashSet<Teacher>();
		teachers.add(ctx.getBean("t1",Teacher.class));
		teachers.add(ctx.getBean("t2",Teacher.class));
		
		teachers.stream().filter(
				t->t.getClazzs().stream().map(Clazz::getId).anyMatch(
						id->mary.getClazzs().stream().map(Clazz::getId).anyMatch(id2->id2==id)
						)
				).map(Teacher::getName).forEach(System.out::println);
		
	}

}
