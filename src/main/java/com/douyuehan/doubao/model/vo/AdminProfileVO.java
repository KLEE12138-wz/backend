package com.douyuehan.doubao.model.vo;

import lombok.Data;

/**
 * 管理员个人信息视图对象
 */
@Data
public class AdminProfileVO {

    /**
     * 管理员ID
     */
    private String id;

    /**
     * 管理员用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 状态
     */
    private Boolean status;

    // 其他管理员特有的信息可以在此添加
}
