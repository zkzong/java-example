package io.code.sb3.mybatis.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class User implements Serializable {

    private Long id;
    private String userName;
    private Integer age;
    private Date createTime;
    private Date updateTime;

}
