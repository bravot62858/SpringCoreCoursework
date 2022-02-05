package com.study.SpringCoreCoursework.coursework4.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.study.SpringCoreCoursework.coursework4.entity.Book;
import com.study.SpringCoreCoursework.coursework4.entity.Order_Log;
import com.study.SpringCoreCoursework.coursework4.entity.Wallet;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientAmount;
import com.study.SpringCoreCoursework.coursework4.exception.InsufficientQuantity;

@Repository
public class BookDaoImpl implements BookDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer getPrice(Integer bid) {
		String sql = "select price from book where bid=?";
		return jdbcTemplate.queryForObject(sql, Integer.class,bid);
	}

	@Override
	public Integer getStockAmount(Integer bid) {
		String sql = "select amount from stock where bid=?";
		return jdbcTemplate.queryForObject(sql, Integer.class,bid);
	}

	@Override
	public Integer getWalletMoney(Integer wid) {
		String sql = "select money from wallet where wid=?";
		return jdbcTemplate.queryForObject(sql, Integer.class,wid);
	}

	@Override
	public Integer updateStock(Integer bid, Integer amount) throws InsufficientQuantity{
		// 確認該書的最新庫存量
		Integer new_amount = getStockAmount(bid);
		if(new_amount<= 0) {
			throw new InsufficientQuantity(String.format("此書號: %d 目前沒庫存，目前數量: %d", bid,new_amount));
		}else if(new_amount<amount) {
			throw new InsufficientQuantity(String.format("此書號: %d 目前沒庫存，目前數量: %d，欲購買數量: %d", bid,new_amount,amount));
		}
		// 修改庫存
		String sql = "update stock set amount = amount - ? where bid=?";
		return jdbcTemplate.update(sql,amount,bid);
	}

	@Override
	public Integer updateWallet(Integer wid, Integer money) throws InsufficientAmount{
		// 確認餘額
		Integer new_money = getWalletMoney(wid);
		if(new_money<=0) {
			throw new InsufficientAmount(String.format("錢包號碼: %d 目前沒餘額，目前餘額: %d", wid,new_money));
		}else if(new_money<money) {
			throw new InsufficientAmount(String.format("錢包號碼: %d 餘額不足，目前餘額: %d，欲扣除金額: %d", wid,new_money,money));
		}
		// 修改餘額
		String sql = "update wallet set money = money - ? where wid=?";
		return jdbcTemplate.update(sql,money,wid);
	}

	@Override
	public Integer addOrder(Integer wid, Integer bid, Integer amount, Integer price, Date buytime) {
		String sql = "insert into order_log (wid,bid,amount,price,total,buytime) values(?,?,?,?,?,?)";
		int total = amount*price;
		return jdbcTemplate.update(sql,wid,bid,amount,price,total,buytime);
	}

	@Override
	public List<Order_Log> queryOrder() {
		String sql = "select oid,wid,bid,amount,price,total,buytime from order_log";
		String Booksql = "select bid,bname,price,ct from book where bid=?";
		String Walletsql = "select wid,wname,money from wallet where wid=?";
		List<Order_Log> order_Logs = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Order_Log.class));
		order_Logs.forEach(ol->{
			ol.setBook((Book)jdbcTemplate.query(Booksql, new BeanPropertyRowMapper(Book.class),ol.getBid()).get(0));
			ol.setWallet((Wallet)jdbcTemplate.query(Walletsql, new BeanPropertyRowMapper(Wallet.class),ol.getWid()).get(0));
		});
		return order_Logs;
	}

}
