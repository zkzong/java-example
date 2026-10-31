package org.example.sb3.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: zongz
 * @Date: 2024/10/27
 */
@Data
@Builder
public class UserVo {

    private Long id;
    private String userName;
    private Integer age;

    private List<String> hobbies;

}
