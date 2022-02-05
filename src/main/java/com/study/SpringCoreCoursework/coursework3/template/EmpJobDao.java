package com.study.SpringCoreCoursework.coursework3.template;

import java.sql.ResultSet;
import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.study.SpringCoreCoursework.coursework3.entity.Emp;
import com.study.SpringCoreCoursework.coursework3.entity.Job;

@Repository
public class EmpJobDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Emp> queryEmps(){
		//查出每個人的工作名稱為何?
		String sql = "select eid,ename,age,createtime from emp";
		List<Emp> emps = jdbcTemplate.query(sql, (ResultSet rs,int rowNum)->{
			//設定欄位
			Emp emp = new Emp();
			emp.setEid(rs.getInt("eid"));
			emp.setEname(rs.getString("ename"));
			emp.setAge(rs.getInt("age"));
			emp.setCreatetime(rs.getTimestamp("createtime"));
			
			String sql2 = "select jid,jname,eid from job where eid=?";
			List<Job> jobs = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Job.class),emp.getEid());
			//設定關聯
			emp.setJobs(jobs);
			return emp;
		});
		return emps;
	}
	
	public List<Job> query_Jobs(){
		//每個工作的員工姓名為何?
		String sql = "select jid,jname,eid from job";
		List<Job> jobs = jdbcTemplate.query(sql, (ResultSet rs,int rowNum)->{
			Job job = new Job();
			job.setJid(rs.getInt("jid"));
			job.setJname(rs.getString("jname"));
			job.setEid(rs.getInt("eid"));
			
			String sql2 = "select eid,ename,age,createtime from emp where eid=?";
			List<Emp> emps = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Emp.class),job.getEid());
			//System.out.println(job.getEid());
			if(emps!=null && emps.size()>0) {
				job.setEmp(emps.get(0));	
			}
			return job;
		});
		return jobs;
	}
	
	public List<Emp> query_Emps2(){
		String sql = "select e.eid, e.ename, e.age, e.createtime,\r\n"
				+ "       j.jid as job_jid, j.jname as job_jname, j.eid as job_eid\r\n"
				+ "from emp e left outer join job j on j.eid = e.eid";
		// j.jid as job_jid 這樣的命名原則 "job_jid" 是表示將 jid 的內容 是對應給 job 的資料表
		ResultSetExtractor<List<Emp>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys("eid")
				.newResultSetExtractor(Emp.class);
		
		return jdbcTemplate.query(sql, resultSetExtractor);
	}
	
	public List<Job> query_Jobs2(){
		String sql = "select j.jid,j.jname,j.eid,\r\n"
				+ "   e.eid as emp_eid,e.ename as emp_ename,e.age as emp_age,e.createtime as emp_createtime\r\n"
				+ "from job j left outer join emp e on j.eid=e.eid";
		ResultSetExtractor<List<Job>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys("Jid")
				.newResultSetExtractor(Job.class);
		
		return jdbcTemplate.query(sql, resultSetExtractor);
	}
}
