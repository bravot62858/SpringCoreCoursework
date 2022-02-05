package com.study.SpringCoreCoursework.coursework3.template;

import java.util.Arrays;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1) 
public class LogAspect {
	
	
	@Pointcut(value = "execution(* com.study.SpringCoreCoursework.coursework3.template.EmpDao.queryAll(..))") 
	public void pt() {}
	
	
	@Before(value = "pt()") 
	public void before(JoinPoint joinpoint) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
		EmpDao empDao = ctx.getBean("empDao",EmpDao.class);
		empDao.insertLog(joinpoint.getSignature().getName());
		System.out.printf("%s %s\n", "寫入LOG:", joinpoint.getSignature().getName());
	}
	
}
