package com.it521.open.common.constant;

import com.it521.open.common.core.domain.BaseEnum;
import com.it521.open.common.utils.MessageUtils;
import com.it521.open.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author X
 */
public enum ResponseEnum implements BaseEnum {
    /**
     * @描述.请求成功
     */
    REQUEST_SUCCESS(0, "request.success"),
    /**
     * @描述.请求失败
     */
    REQUEST_FAILURE(10001, "request.failure"),
    /**
     * @描述.缺少认证信息
     */
    USER_TOKEN_NOT_EXISTS(10400, "user.token.not.exists.error"),
    /**
     * @描述.未认证或认证信息过期
     */
    USER_TOKEN_EXPIRED(10401, "user.token.expired.error"),
    /**
     * @描述.认证信息非法
     */
    USER_TOKEN_INVALID(10402, "user.token.invalid.error"),
    /**
     * @描述.缺少必要参数
     */
    REQUIRED_PARAMS_MISSING(10403, "required.params.missing.error"),
    /**
     * @描述.请求方式不支持
     */
    REQUEST_METHOD_NOT_SUPPORT(10405, "request.method.not.support"),
    /**
     * @描述.请求格式不正确
     */
    REQUEST_FORMAT_NOT_SUPPORT(10406, "request.format.not.support"),
    /**
     * @描述.用户不存在
     */
    USER_NOT_EXISTS(20001, "user.not.exists"),
    /**
     * @描述.密码错误
     */
    USER_PASSWORD_NOT_MATCH(20002, "user.password.not.match"),
    /**
     * @描述.微信用户信息校验失败
     */
    WX_USERINFO_ERROR(30000, "wechat.userinfo.error"),
    /**
     * @描述.系统警告
     */
    SYSTEM_WARNING(99997, "system.warning"),
    /**
     * @描述.自定义异常
     */
    CUSTOMIZE_ERROR(99998, "system.error"),
    /**
     * @描述.服务器错误
     */
    SERVER_ERROR(99999, "server.error");

    private Integer code;

    private String msg;

    private static Map<Integer, String> allMap = new HashMap<>();

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        for (ResponseEnum enums : ResponseEnum.values()) {
            allMap.put(enums.code, enums.msg);
        }
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String msg() {
        String message = null;
        if (!StringUtils.isEmpty(msg)) {
            message = MessageUtils.message(msg);
        }
        if (message == null) {
            message = msg;
        }
        return message;
    }

    public String msg(String code) {
        return allMap.get(code);
    }
}
