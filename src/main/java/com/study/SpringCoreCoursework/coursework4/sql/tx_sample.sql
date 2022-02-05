-- 交易 TX (Transcation) 所需要的資料表
-- book (書籍資料)、stock (庫存資料)、wallet (客戶雲端錢包)
-- 建立 book (書籍資料)
create table if not exists book(
	bid integer not null auto_increment,
	bname varchar(20) not null,
	price integer,
	ct timestamp default current_timestamp,
	primary key (bid)
);
-- 建立 stock (庫存資料)
create table if not exists stock(
	sid integer not null auto_increment,
	bid integer not null, -- book的id
	amount integer default 0,
	primary key (sid),
	foreign key (bid) references book(bid)
);
-- 建立 wallet (客戶雲端錢包)
create table if not exists wallet(
	wid integer not null auto_increment,
	wname varchar(20) not null,
	money integer default 0,
	primary key (wid)
);

-- HomeWork 建立交易紀錄 order_log 資料表
-- Vincent在2020/01/23 PM 2:07:51 買了Java書2本,共300元
-- Vincent在2020/01/23 PM 2:08:51 買了Python書2本,共200元
-- Vincent在2020/01/23 PM 2:10:51 買了Java書4本,共600元
--若book的price 有異動，order_log則不影響
-- 資料表如何創建? 
create table if not exists order_log(
	oid integer not null auto_increment,
	wid integer not null,
	bid integer not null,
	amount integer default 0,
	price integer default 0,
	total integer default 0,
	buytime timestamp,
	primary key (oid),
	foreign key (wid) references wallet(wid),
	foreign key (bid) references book(bid)
)

