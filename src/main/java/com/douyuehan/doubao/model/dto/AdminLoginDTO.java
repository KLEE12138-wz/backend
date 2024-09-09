package com.douyuehan.doubao.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理员登录数据传输对象
 */
@Data
public class AdminLoginDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 15, message = "登录用户名长度在2-15")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    private String password;

    private Boolean rememberMe;
}
