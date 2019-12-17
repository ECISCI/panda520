package com.panda520.mall.restapi.controller;

import com.panda520.mall.common.annotation.Log;
import com.panda520.mall.common.core.controller.BaseController;
import com.panda520.mall.common.core.domain.ResponseResult;
import com.panda520.mall.restapi.annotation.PassAuth;
import com.panda520.mall.restapi.entity.login.LoginReq;
import com.panda520.mall.restapi.entity.login.LoginRes;
import com.panda520.mall.restapi.entity.register.RegisterReq;
import com.panda520.mall.restapi.entity.register.RegisterRes;
import com.panda520.mall.restapi.util.Constant;
import com.panda520.mall.restapi.util.JWTUtil;
import com.panda520.mall.restapi.util.UUidUtils;
import com.panda520.mall.system.domain.SysUser;
import com.panda520.mall.system.domain.SysUserMobile;
import com.panda520.mall.system.service.ISysUserMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/mobile")
public class MobileUserController extends BaseController {

    @Autowired
    private ISysUserMobileService service;

    @PostMapping("/login")
    @ResponseBody
    @PassAuth
    @Log(title = "mobileLogin")
    public ResponseResult mobileLogin(@RequestBody LoginReq req) {

        String username = req.getUsername();
        String password = req.getPassword();

        LoginRes res = new LoginRes();

        SysUserMobile mobile = service.selectSysUserMobileByUserNameAndPassword(username, password);

        if (mobile == null) { // 用户已存在,请注册

            return ResponseResult.error(Constant.LOGIN_NOT_EXIST);
        } else {
            String token = JWTUtil.sign(username, password);

            res.setToken(token);
            res.setAddress(mobile.getAddress());
            res.setEmail(mobile.getEmail());
            res.setPhone(mobile.getPhone());
            res.setUserType(mobile.getUserType());
            res.setUser_id(mobile.getId());
        }
        return ResponseResult.successResponse(res);

    }

    @PostMapping("/register")
    @ResponseBody
    @PassAuth
    @Log(title = "mobileRegister")
    public ResponseResult mobileRegister(@RequestBody LoginReq req) {

        String username = req.getUsername();
        String password = req.getPassword();
        String user_id = UUidUtils.uuid();

        // 去查数据库
        SysUserMobile user = service.selectSysUserMobileByUserNameAndPassword(username, password);

        if (user != null) { // 该用户已存在
            return ResponseResult.error(Constant.R_EXIST);
        }
        SysUserMobile sysUserMobile = new SysUserMobile();
        sysUserMobile.setId(user_id);
        sysUserMobile.setPassword(password);
        sysUserMobile.setUname(username);

        int insertCode = service.insertSysUserMobile(sysUserMobile);
        if (insertCode == 0) { // 插入数据库失败
            return ResponseResult.error(Constant.R_FAILED);
        }

        RegisterRes res = new RegisterRes();
        String token = JWTUtil.sign(username, password);
        res.setToken(token);
        res.setUser_id(user_id);

        return ResponseResult.success(Constant.R_SUCCESS, res);
    }
}