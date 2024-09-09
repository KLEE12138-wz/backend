package com.douyuehan.doubao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.dto.AdminLoginDTO;
import com.douyuehan.doubao.model.dto.AdminRegisterDTO;
import com.douyuehan.doubao.model.entity.UmsAdmin;
import com.douyuehan.doubao.service.IUmsAdminService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/ums/admin")
public class UmsAdminController extends BaseController {

    @Resource
    private IUmsAdminService iUmsAdminService;

    @PostMapping("/register")
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody AdminRegisterDTO dto) {
        UmsAdmin admin = iUmsAdminService.executeRegister(dto);
        if (ObjectUtils.isEmpty(admin)) {
            return ApiResult.failed("管理员注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("admin", admin);
        return ApiResult.success(map);
    }

    @PostMapping("/admin/login")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody AdminLoginDTO dto) {
        String token = iUmsAdminService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

    @GetMapping("/info")
    public ApiResult<UmsAdmin> getAdmin(@RequestHeader(value = USER_NAME) String userName) {
        UmsAdmin admin = iUmsAdminService.getAdminByUsername(userName);
        return ApiResult.success(admin);
    }

    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getAdminByName(@PathVariable("username") String username,
                                                         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        UmsAdmin admin = iUmsAdminService.getAdminByUsername(username);
        Assert.notNull(admin, "管理员不存在");
        Page<?> page = new Page<>(pageNo, size); // 可以添加管理员特定的分页逻辑
        map.put("admin", admin);
        map.put("page", page);
        return ApiResult.success(map);
    }

    @PostMapping("/update")
    public ApiResult<UmsAdmin> updateAdmin(@RequestBody UmsAdmin umsAdmin) {
        iUmsAdminService.updateById(umsAdmin);
        return ApiResult.success(umsAdmin);
    }
}
