package com.douyuehan.doubao.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("ums_admin")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UmsAdmin implements Serializable {
    private static final long serialVersionUID = -3483489348934859L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("username")
    private String username;

    @JsonIgnore
    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("mobile")
    private String mobile;

    @Builder.Default
    @TableField("status")
    private Boolean status = true;

    @TableField("role_id")
    private Integer roleId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
