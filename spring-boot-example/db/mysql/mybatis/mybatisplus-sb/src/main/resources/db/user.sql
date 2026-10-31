CREATE DATABASE IF NOT EXISTS mybatis DEFAULT CHARACTER SET utf8mb4;
USE mybatis;

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
                        id        BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
                        user_name VARCHAR(50)  NOT NULL COMMENT '用户名',
                        age       INT          NULL COMMENT '年龄',
                        PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';