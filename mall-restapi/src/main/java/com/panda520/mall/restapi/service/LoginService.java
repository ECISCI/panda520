package com.panda520.mall.restapi.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.panda520.mall.common.exception.user.UserNotExistsException;
import com.panda520.mall.common.exception.user.UserPasswordNotMatchException;
import com.panda520.mall.common.utils.StringUtils;
import com.panda520.mall.common.utils.security.Md5Utils;
import com.panda520.mall.restapi.result.LoginResult;
import com.panda520.mall.restapi.util.JWTUtil;
import com.panda520.mall.system.domain.SysUser;
import com.panda520.mall.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 玛丽莲梦明
 */
@Service
public class LoginService {

    @Autowired
    private ISysUserService sysUserService;

    public LoginResult login(SysUser loginUser) {
        SysUser user = sysUserService.selectUserByLoginName(loginUser.getLoginName());
        if (user == null) {
            throw new UserNotExistsException();
        }
        if (verify(user, loginUser.getPassword())) {
            String token = JWTUtil.sign(user.getLoginName(), user.getPassword());
            return new LoginResult(token, user.getUserId());
        } else {
            throw new UserPasswordNotMatchException();
        }
    }

    public String wechatLogin(WxMaJscode2SessionResult wxMaJscode2SessionResult, String ipAddr) {
        String openid = wxMaJscode2SessionResult.getOpenid();
        SysUser user = sysUserService.selectUserByOpenid(openid);
        if (user == null) {
            user = new SysUser();
            String randomString = StringUtils.getRandomString(12);
            user.setLoginName("wechat_user_" + randomString);
            user.setUserName("微信用户_" + randomString);
            user.setPassword(Md5Utils.hash(openid));
            user.setWxOpenid(openid);
            user.setWxUnionid(wxMaJscode2SessionResult.getUnionid());
            user.setLoginDate(new Date());
            user.setLoginIp(ipAddr);
            user.setRegisterDate(new Date());
            user.setRegisterIp(ipAddr);
            // 增加userType
            sysUserService.insertUser(user);
        }
        return JWTUtil.signWeChat(wxMaJscode2SessionResult);
    }

    private boolean verify(SysUser sysUser, String password) {
        String loginName = sysUser.getLoginName();
        String salt = sysUser.getSalt();
        String pwd = sysUser.getPassword();
        String concatStr = loginName.concat(password).concat(salt);
        return Md5Utils.hash(concatStr).equals(pwd);
    }
}
