package com.study.SpringCoreCoursework.coursework3.entity;

import java.util.Date;
import java.util.List;
import  static java.util.stream.Collectors.toList;

public class Emp {
	private Integer eid;
	private String ename;
	private Integer age;
	private Date createtime;
	//關係
	private List<Job> jobs;
	
	public Emp(String ename, Integer age) {
		super();
		this.ename = ename;
		this.age = age;
	}
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	@Override
	public String toString() {
		return "Emp [eid=" + eid + ", ename=" + ename + ", age=" + age + ", createtime=" + createtime + ", jobs=" + 
				(jobs == null ? "" : jobs.stream().map(j -> j.getJname()).collect(toList()))
				+ "]";
	}

	
	
}
