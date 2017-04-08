package cn.programingmonkey.Exception;

import cn.programingmonkey.Bean.ResetPwdBean;
import cn.programingmonkey.Exception.type.RESET_PWD_EXCEPTION_TYPE;

/**
 * Created by cai on 2017/3/30.
 */
public class ResetPwdException extends BaseException {

    public ResetPwdException(RESET_PWD_EXCEPTION_TYPE type){
        super(type);
        this.code = code;
        this.msg = msg;
    }
}
