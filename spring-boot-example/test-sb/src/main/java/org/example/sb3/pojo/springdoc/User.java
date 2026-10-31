package org.example.sb3.pojo.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户")
public class User implements Serializable {

    @Schema(name = "id", title = "id", description = "id", format = "int32", required = true, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "用户姓名", title = "用户姓名", description = "用户姓名", example = "张/刘", accessMode = Schema.AccessMode.READ_ONLY, required = true, requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(name = "密码", description = "密码")
    private String password;

    @Schema(name = "电子邮箱", description = "电子邮箱")
    private String email;

    @Schema(name = "身份证", description = "身份证")
    private String idCard;

    @Schema(name = "手机号", description = "手机号")
    private String phone;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

}
