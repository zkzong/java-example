package io.code.sb3.mybatis.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {

    private Long id;
    private String userName;
    private Integer age;
    private Date createTime;
    private Date updateTime;

}
