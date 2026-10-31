package org.example.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserInfo {

    @Id
    private Long id;
    private String username;
    private String password;

}
