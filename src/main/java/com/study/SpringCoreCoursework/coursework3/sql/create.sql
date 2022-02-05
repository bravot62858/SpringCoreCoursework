-- 建立 Emp 資料表
create table if not exists log (
	eid int not null auto_increment, -- 主鍵值(自動產生序號)
	ename varchar(50) not null, -- 員工姓名
	createtime timestamp default current_timestamp, -- 建檔時間
	primary key(eid)
)

create table if not exists log (
			lid int not null auto_increment, 
			lname varchar(50) not null, -- 存放方法名稱
			ldate timestamp default current_timestamp, -- 建檔時間
			primary key(lid)
)

-- 建立 Job 資料表
create table if not exists job (
	jid int not null auto_increment, -- 主鍵值(自動產生序號)
	jname varchar(50) not null unique, -- 工作名稱
	eid int, -- 員工Id
	primary key(jid),
	foreign key(eid) references emp(eid) -- 外鍵約束/關聯
)

-- 由上述可知，一個emp對應多個job，一個job對應一個emp
insert into job(jname, eid) values ('coding', 3);
insert into job(jname, eid) values ('jobA', 3);
insert into job(jname, eid) values ('jobB', 4);
insert into job(jname, eid) values ('jobC', 7);
insert into job(jname, eid) values ('jobD', 12);
insert into job(jname, eid) values ('jobE', 13);
insert into job(jname, eid) values ('jobH', 3);
insert into job(jname, eid) values ('jobI', 12);
insert into job(jname, eid) values ('jobJ', 7);
insert into job(jname, eid) values ('jobK', 4);
insert into job(jname, eid) values ('jobL', 3);
insert into job(jname) values ('jobM');
insert into job(jname) values ('jobN');

-- 每個員工的工作列表
select e.eid,e.ename,j.jname 
from emp e ,job j
where e.eid=j.eid
order by e.ename;

-- 每個員工有幾項工作
select e.ename,count(j.jname) 
from emp e ,job j
where e.eid=j.eid
group by e.ename
order by e.ename;

-- 每個員工有幾項工作
select e.ename, count(j.jname) as work 
from emp e, job j
where e.eid=j.eid 
group by e.ename
order by 2 deSc 
limit 1

SELECT d.ename, MAX(cnt)
FROM (
SELECT a.ename, count(b.jname) cnt
FROM emp a inner JOIN job b on a.eid=b.eid
GROUP BY  a.ename
) d

-- HomeWork
CREATE TABLE ItemProduct ( -- 商品項目
id INTEGER not null auto_increment, -- 商品項目序號(主鍵)
text VARCHAR(50) not null, -- 商品項目名稱
price INTEGER NOT NULL, -- 商品單價
inventory INTEGER NOT NULL, -- 商品庫存量
PRIMARY KEY (id)
);

CREATE TABLE Invoice ( -- 發票
id INTEGER not null auto_increment, -- 發票序號(主鍵)
invdate Date not null, -- 發票日期
PRIMARY KEY (id)
);

CREATE TABLE Item (  -- 發票項目
id INTEGER not null auto_increment, -- 項目序號(主鍵)
amount INTEGER NOT NULL, -- 數量
ipid INTEGER NOT NULL, -- 商品項目序號
invid INTEGER NOT NULL, -- 發票序號
PRIMARY KEY (id),
FOREIGN KEY (ipid) REFERENCES ItemProduct(id),
FOREIGN KEY (invid) REFERENCES Invoice(id)
);



-- 每一張發票價值多少?	List<ItemProduct>一一抓出來算價錢加總
select a.id ,a.amount,a.ipid,a.invid ,b.price ,sum(b.price *a.amount) as 'sum'
from item a
left join itemproduct b on a.ipid=b.id
group by invid
-- 每一樣商品各賣了多少?	
		
-- 哪一件商品賣得錢最多?	
select a.id,a.`text`,a.price,b.amount,sum(a.price*b.amount) as 'ss'
from itemproduct a 
left join item b on a.id=b.ipid
group by a.id
order by ss desc
limit 1		
-- 哪一張發票價值最高?	

