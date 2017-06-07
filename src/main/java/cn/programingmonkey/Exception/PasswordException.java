package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.PASSWORD_EXCEPTION_TYPE;

/**
 * Created by cai on 31/01/2017.
 */

public class PasswordException  extends BaseException{

    public PasswordException(int code,String msg){
        super(code,msg);
    }

    public PasswordException(PASSWORD_EXCEPTION_TYPE password_exception_type) {

        super.code = password_exception_type.getCode();
        super.msg  = password_exception_type.getMsg();
    }
}
