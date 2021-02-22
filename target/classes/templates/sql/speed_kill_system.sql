show databases ;

use speed_kill_system;

#商品表
create table ay_product(
    id bigint(20) not null comment '商品id',
    name varchar(255) not null default '' comment '商品名称',
    number int not null default 0 comment '商品数量',
    start_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '秒杀开始时间',
    end_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '秒杀结束时间',
    create_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '创建时间',
    product_img varchar(255) default null comment '商品图片',
    primary key (id),
    key idx_name (name) using btree
);

insert into ay_product(id, name, number, start_time, end_time, create_time, product_img)
VALUES ('1', '围城', '100', '2021-02-22 15:50:00', '2021-02-22 19:50:00', '2021-02-22 15:50:00', null),
       ('2', '活着', '100', '2021-02-22 15:50:00', '2021-02-22 19:50:00', '2021-02-22 15:50:00', null),
       ('3', '三国演义', '100', '2021-02-22 15:50:00', '2021-02-22 19:50:00', '2021-02-22 15:50:00', null);

insert into ay_product(id, name, number, start_time, end_time, create_time, product_img)
VALUES ('4', '围城', '1', '2021-02-22 15:50:00', '2021-02-22 19:50:00', '2021-02-22 15:50:00', null);

select * from ay_product;

#用户表
create table ay_user(
    id bigint(20) not null comment '用户id',
    name varchar(255) not null comment '用户名',
    phone_number varchar(11) default null comment '电话号码',
    primary key (id)
);

insert into ay_user
values ('1', '张三', '13366668888'),
       ('2', '李四', '15599996666');

select * from ay_user;

#商品秒杀记录表
create table ay_user_kill_product(
    product_id bigint(20) default null comment '商品id',
    user_id bigint(20) default null comment '用户id',
    state tinyint(4) default null comment '状态，-1：无效；0：成功；1：已付款',
    create_time timestamp null default null on update current_timestamp comment '创建时间',
    id bigint(20) not null auto_increment comment '唯一主键',
    primary key (id)
);