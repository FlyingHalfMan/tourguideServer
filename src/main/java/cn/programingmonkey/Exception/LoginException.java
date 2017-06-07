package cn.programingmonkey.Exception;


import cn.programingmonkey.Exception.type.LOGIN_EXCEPTION_TYPE;

/**
 * Created by cai on 2017/3/30.
 */

public class LoginException extends BaseException {

    public LoginException(LOGIN_EXCEPTION_TYPE type){
        super(type.getCode(),type.getMsg());
    }
}


