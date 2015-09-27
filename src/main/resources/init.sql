#SAE公共版MySQL支持MyISAM存储引擎，企业版支持InnoDB存储引擎
create table tb_message_text(
  id int(12) not null auto_increment PRIMARY  key,
  open_id varchar(100) not null,
  content varchar(2047) not null,
  create_time DATE ,
  remark VARCHAR(1000)
)engine=MyISAM


#签到功能
#用户表：
create table weixin_user(
	id int(11) not null auto_increment primary key, #主键标识
	open_id varchar(100) not null, #微信用户的ID
	subscribe_time datetime, #关注时间
	subscribe_status tinyint default 1, # 关注状态
	points int(11) default 0   #用户总积分
)engine = MyISAM DEFAULT CHARSET=utf8

#签到表
create table weixin_sign(
   id int(11) not null auto_increment primary key, #主键标识
   open_id varchar(100),  #微信用户的ID
   sign_time datetime, #签到时间
   sign_points int(11)  # 签到积分
) engine = MyISAM DEFAULT CHARSET=utf8

#用户地理位置表
create table user_location(
	id int not null auto_increment primary key,
	open_id varchar(50) not null, #用户的Open id
	lng varchar(30) not null, #用户发送的经度
	lat varchar(30) not null, #用户发送的纬度
	bd09_lng varchar(30), # 经过百度坐标转换后的经度
	bd09_lat varchar(30)  #经过百度坐标转换后的纬度
)