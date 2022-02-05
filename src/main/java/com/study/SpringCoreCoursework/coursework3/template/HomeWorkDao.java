package com.study.SpringCoreCoursework.coursework3.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSOutput;

import com.study.SpringCoreCoursework.coursework3.entity.Emp;
import com.study.SpringCoreCoursework.coursework3.entity.Invoice;
import com.study.SpringCoreCoursework.coursework3.entity.Item;
import com.study.SpringCoreCoursework.coursework3.entity.ItemProduct;

import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;

@Repository
public class HomeWorkDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private List<Invoice> queryInvoices(){
		String queryInvoiceSql = "select id,invdate from invoice";
		return jdbcTemplate.query(queryInvoiceSql,new BeanPropertyRowMapper(Invoice.class));
	}
	private List<ItemProduct> queryItemProducts(){
		String queryItemProductSql = "select id,text,price,inventory from itemproduct";
		return jdbcTemplate.query(queryItemProductSql, new BeanPropertyRowMapper(ItemProduct.class));
	}
	
	
	private void setItem(List<Item> items) {
		List<Invoice> invoices = queryInvoices();
		List<ItemProduct> itemProducts = queryItemProducts();
		items.forEach(it->{
			itemProducts.stream().filter(ip->it.getIpid().equals(ip.getId())).forEach(ip->it.setItemProduct(ip));
			invoices.stream().filter(i->it.getInvid().equals(i.getId())).forEach(i->it.setInvoice(i));
		});
		
	}
	
	//每一張發票有那些商品?	
	public void queryProductNameInInvoice() {
		String sql = "select id,invdate from invoice";
		List<Invoice> invoices = jdbcTemplate.query(sql, (ResultSet rs,int rowNum)->{
			Invoice invoice = new Invoice();
			invoice.setId(rs.getInt("id"));
			invoice.setInvdate(rs.getDate("invdate"));
			String sql2 = "select id,amount,ipid,invid from item where invid=?";
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),invoice.getId());
			invoice.setItem(items);
			return invoice;
		});
		invoices.forEach(i->setItem(i.getItems()));
		System.out.println("1.每一張發票有那些商品?");
		invoices.forEach(i->{
			System.out.print("發票ID: "+i.getId()+" 日期: "+i.getInvdate()+" 項目: ");
			i.getItems().forEach(it->{
				System.out.print(it.getItemProduct().getText()+" "+it.getAmount()+"個。 ");
			});
			System.out.println();
		});
	}
	
	//每一張發票有幾件商品?	
	public void queryProductCountInInvoice() {
		String sql = "select id,invdate from invoice";
		List<Invoice> invoices = jdbcTemplate.query(sql, (ResultSet rs,int rowNum)->{
			Invoice invoice = new Invoice();
			invoice.setId(rs.getInt("id"));
			invoice.setInvdate(rs.getDate("invdate"));
			String sql2 = "select id,amount,ipid,invid from item where invid=?";
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),invoice.getId());
			setItem(items);
			invoice.setItem(items);
			return invoice;
		});
		System.out.println("2.每一張發票有幾件商品?");
		invoices.forEach(i->{
			System.out.println("發票ID: "+i.getId()+" 日期: "+i.getInvdate()+" 項目個數: "+i.getItems().size()+" 件");
		});
	}
	//每一張發票價值多少?	
	public void queryInvoiceIsHowMuch() {
		String sql = "select id,invdate from invoice";
		List<Invoice> invoices = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Invoice.class));
		System.out.println("3.每一張發票價值多少?	");
		invoices.forEach(i->{
			String sql2 = "select id,amount,ipid,invid from item where invid=?";
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),i.getId());
			setItem(items);
			i.setItem(items);
			List<Item> items2 = i.getItems();
			int sum = 0;
			for(Item item:items2) {
				ItemProduct itemProduct = item.getItemProduct();
				sum = sum+item.getAmount()*itemProduct.getPrice();
			}	
			System.out.println("發票ID: "+i.getId()+" 日期: "+i.getInvdate()+" 總花費: "+sum);	
			
		});
		
	}
	
	//每一樣商品各賣了多少?	
	public void queryItemProductMoney() {
		String sql = "select a.id,a.`text`,a.price,b.amount,sum(a.price*b.amount) as 'ss'\r\n"
				+ "from itemproduct a \r\n"
				+ "left join item b on a.id=b.ipid\r\n"
				+ "group by a.id";
		List<ItemProduct> itemProducts = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ItemProduct.class));
		System.out.println("4.每一樣商品各賣了多少?	");
		itemProducts.forEach(ip->{
			String sql2 = "select id,amount,ipid,invid from item where ipid=?";
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),ip.getId());
			setItem(items);
			ip.setItem(items);
			int amount=0;
			int sum=0;
			for(Item i:ip.getItems()){
				amount = amount+i.getAmount();
				sum=sum+(i.getAmount()*ip.getPrice());
			}
			System.out.println("產品ID: "+ip.getId()+" 產品名稱: "+ip.getText()
			+" 產品價錢: "+ip.getPrice()+" 販售數量: "+amount+" 總價: "+sum);
		});
	}
	
	
	
	//哪一件商品賣得錢最多?	
	public void queryMaxItemProductMoney() {
		String sql = "select a.id,a.`text`,a.price,b.amount,sum(a.price*b.amount) as 'ss'\r\n"
				+ "from itemproduct a \r\n"
				+ "left join item b on a.id=b.ipid\r\n"
				+ "group by a.id\r\n"
				+ "order by ss desc\r\n"
				+ "limit 1";
		List<ItemProduct> itemProducts = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ItemProduct.class));
		System.out.println("5.哪一件商品賣得錢最多?	");
		if(itemProducts.size()!=0) {
			String sql2 = "select id,amount,ipid,invid from item where ipid=?";
			ItemProduct itemProduct = itemProducts.get(0);
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),itemProduct.getId());
			itemProduct.setItem(items);
			int amount=0;
			int sum=0;
			for(Item item:itemProduct.getItems()) {
				amount = amount+item.getAmount();
				sum=sum+(item.getAmount()*itemProduct.getPrice());
			}
			System.out.println("產品ID: "+itemProduct.getId()+" 產品名稱: "+itemProduct.getText()
			+" 產品價錢: "+itemProduct.getPrice()+" 購買數量: "+amount+" 總價: "+sum);
		}
	}
	//哪一張發票價值最高?	
	public void queryMaxInvoiceMoney() {
		String sql="select id,invdate,total\r\n"
				+ "from  (\r\n"
				+ "	select i.id,i.invdate ,it.invid,sum(it.amount*ip.price) as total ,it.amount ,ip.price \r\n"
				+ "	from item it\r\n"
				+ "	inner join itemproduct ip on it.ipid =ip.id \r\n"
				+ "	inner join invoice i on i.id=it.invid\r\n"
				+ "	group by it.invid \r\n"
				+ "	order by i.id desc\r\n"
				+ ") d\r\n"
				+ "order by total desc \r\n"
				+ "limit 1";
		List<Invoice> invoices = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Invoice.class));
		System.out.println("6.哪一張發票價值最高?	");
		if(invoices.size()!=0) {
			Invoice invoice = invoices.get(0);
			String sql2 = "select id,amount,ipid,invid from item where invid=?";
			List<Item> items = jdbcTemplate.query(sql2, new BeanPropertyRowMapper(Item.class),invoice.getId());
			setItem(items);
			invoice.setItem(items);
			List<Item> items2 = invoice.getItems();
			int sum = 0;
			for(Item item:items2) {
				ItemProduct itemProduct = item.getItemProduct();
				sum = sum+item.getAmount()*itemProduct.getPrice();
			}
			System.out.println("發票ID: "+invoice.getId()+" 日期: "+invoice.getInvdate()+" 總花費: "+sum);				
		}
	}

}
