package com.study.SpringCoreCoursework.coursework3.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.study.SpringCoreCoursework.coursework3.entity.Emp;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Repository
public class EmpDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ComboPooledDataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//多筆查詢1
	public List<Map<String,Object>> queryAll(){
		String sql = "select eid,ename,age,createtime from emp";
		List<Map<String,Object>> emps = jdbcTemplate.queryForList(sql);
		return emps;
	}
	
	// 多筆查詢2
	public List<Emp> queryListEmps(){
		String sql = "select eid,ename,age,createtime from emp";
		List<Emp> emps = jdbcTemplate.query(sql, (ResultSet rs,int rowNum)->{
			Emp emp = new Emp();
			emp.setEid(rs.getInt("eid"));
			emp.setEname(rs.getString("ename"));
			emp.setAge(rs.getInt("age"));
			emp.setCreatetime(rs.getTimestamp("createtime"));
			return emp;
		});
		return emps;
	}
	
	//多筆查詢3
	public List<Emp> queryListEmps2(){
		String sql = "select eid,ename,age,createtime from emp";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Emp.class));
	}
	
	//單筆新增1
	public int addOne1(String ename,Integer age) {
		String sql="insert into emp(ename,age) values(?,?)";
		int rowcount = jdbcTemplate.update(sql, ename,age);
		return rowcount;
	}
	//單筆新增2
	public int addOne2(String ename,Integer age) {
		String sql="insert into emp(ename,age) values(:ename,:age)";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("ename",ename)
				.addValue("age", age);
		int rowcount = namedParameterJdbcTemplate.update(sql, params);
		return rowcount;
	}
	//多筆新增1
	public int[] multiAdd1(List<Object[]> rows) {
		String sql="insert into emp(ename,age) values(?,?)";
		return jdbcTemplate.batchUpdate(sql, rows);
	}
	//多筆新增2
	public int[] multiAdd2(List<Emp> emps) {
		String sql="insert into emp(ename,age) values(?,?)";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, emps.get(i).getEname());
				ps.setInt(2, emps.get(i).getAge());
				
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return emps.size();
			}
		};
		return jdbcTemplate.batchUpdate(sql, setter);
	}
	
	//修改
	public  int updateById(Integer eid,String ename,Integer age) {
		String sql = "update emp set ename=?,age=? where eid=?";
		return jdbcTemplate.update(sql,ename,age,eid);
	}
	
	//刪除
	public  int deleteById(Integer eid) {
		String sql = "delete from emp where eid=?";
		return jdbcTemplate.update(sql,eid);
	}
	
	//單筆新增1交易版
		public int addOneTx(String ename,Integer age) throws Exception{
			// 建立TransactionManager
			DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
			// 定義TransactionDefinition
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			int rowcount = 0;
			TransactionStatus status = transactionManager.getTransaction(def);
			try {
				String sql="insert into emp(ename,age) values(?,?)";
				rowcount = jdbcTemplate.update(sql, ename,age);
				//System.out.println(10/0);	//模擬發生錯誤
			} catch (Exception e) {
				transactionManager.rollback(status);
				throw e;
			}
			transactionManager.commit(status);
			return rowcount;
		}
	
	//功課寫的方法----------------------------------------------------------------
	public void insertLog(String methodName) {
		String sql = "insert into log(lname) values('"+methodName+"')";
		jdbcTemplate.update(sql);
	}
	public void queryAllLog(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String sql = "select lid,lname,ldate from log";
		List<Map<String,Object>> logs = jdbcTemplate.queryForList(sql);
		System.out.println("+-------------+---------------------+");
		System.out.println("| method_name | log_timestamp       |");
		System.out.println("+-------------+---------------------+");
		for(Map<String,Object> m:logs) {
			String lname = (String) m.get("lname");
			String ldate = sdf.format(m.get("ldate"));
			System.out.printf("| %-12s| %-20s|\n",lname,ldate);
			System.out.println("+-------------+---------------------+");
		}
		
	}
}
