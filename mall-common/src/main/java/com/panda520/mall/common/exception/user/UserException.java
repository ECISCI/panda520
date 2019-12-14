package com.panda520.mall.common.exception.user;


import com.panda520.mall.common.exception.base.BaseException;

/**
 * @author X
 * @描述.用户信息异常类
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
