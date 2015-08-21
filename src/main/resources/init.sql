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
