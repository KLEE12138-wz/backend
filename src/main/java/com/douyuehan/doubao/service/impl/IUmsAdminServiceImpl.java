package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.common.exception.ApiAsserts;
import com.douyuehan.doubao.jwt.JwtUtil;
import com.douyuehan.doubao.mapper.UmsAdminMapper;
import com.douyuehan.doubao.model.dto.AdminLoginDTO;
import com.douyuehan.doubao.model.dto.AdminRegisterDTO;
import com.douyuehan.doubao.model.entity.UmsAdmin;
import com.douyuehan.doubao.model.vo.AdminProfileVO;
import com.douyuehan.doubao.service.IUmsAdminService;
import com.douyuehan.doubao.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IUmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Override
    public UmsAdmin executeRegister(AdminRegisterDTO dto) {
        // 查询是否有相同用户名或邮箱的管理员
        LambdaQueryWrapper<UmsAdmin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsAdmin::getUsername, dto.getUsername()).or().eq(UmsAdmin::getEmail, dto.getEmail());
        UmsAdmin umsAdmin = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(umsAdmin)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
        UmsAdmin addAdmin = UmsAdmin.builder()
                .username(dto.getUsername())
                .password(MD5Utils.getPwd(dto.getPassword()))
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .roleId(dto.getRoleId())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addAdmin);

        return addAdmin;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsAdmin>().eq(UmsAdmin::getUsername, username));
    }

    @Override
    public String executeLogin(AdminLoginDTO dto) {
        String token = null;
        try {
            UmsAdmin admin = getAdminByUsername(dto.getUsername());
            if (admin == null) {
                throw new Exception("管理员不存在");
            }
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if (!encodePwd.equals(admin.getPassword())) {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(admin.getUsername()));
        } catch (Exception e) {
            log.warn("管理员登录失败=======>{}", dto.getUsername());
        }
        return token;
    }

    @Override
    public AdminProfileVO getAdminProfile(String id) {
        AdminProfileVO profile = new AdminProfileVO();
        UmsAdmin admin = baseMapper.selectById(id);
        if (admin == null) {
            return profile; // 可能需要处理管理员不存在的情况
        }
        BeanUtils.copyProperties(admin, profile);

        // 可以根据实际需求增加更多的业务逻辑，例如权限等

        return profile;
    }
}
