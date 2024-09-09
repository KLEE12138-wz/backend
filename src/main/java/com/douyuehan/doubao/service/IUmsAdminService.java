package com.douyuehan.doubao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyuehan.doubao.model.dto.AdminLoginDTO;
import com.douyuehan.doubao.model.dto.AdminRegisterDTO;
import com.douyuehan.doubao.model.entity.UmsAdmin;
import com.douyuehan.doubao.model.vo.AdminProfileVO;

public interface IUmsAdminService extends IService<UmsAdmin> {

    /**
     * 注册功能
     *
     * @param dto 注册数据传输对象
     * @return 注册成功的管理员对象
     */
    UmsAdmin executeRegister(AdminRegisterDTO dto);

    /**
     * 获取管理员信息
     *
     * @param username 管理员用户名
     * @return 管理员对象
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 管理员登录
     *
     * @param dto 登录数据传输对象
     * @return 生成的JWT的token
     */
    String executeLogin(AdminLoginDTO dto);

    /**
     * 获取管理员信息
     *
     * @param id 管理员ID
     * @return 管理员信息的视图对象
     */
    AdminProfileVO getAdminProfile(String id);
}
