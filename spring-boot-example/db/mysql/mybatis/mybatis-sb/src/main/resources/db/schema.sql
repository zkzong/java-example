-- drop database if exists mybatis;
-- CREATE database mybatis;
-- use mybatis;

drop table if exists t_user;
CREATE TABLE t_user (
    id          int         not null auto_increment primary key comment 'id',
    user_name   varchar(20) not null comment '姓名',
    age         int         not null comment '年龄',
    create_time timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_time timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
) comment '用户表';