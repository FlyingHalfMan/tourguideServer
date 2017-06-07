package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.VERIFYCODE_EXCEPTION_TYPE;

/**
 * Created by cai on 30/01/2017.
 */


public class VerifyCodeException  extends  BaseException{

    public VerifyCodeException(VERIFYCODE_EXCEPTION_TYPE type){
        super(type.getCode(),type.getMsg());
    }

    public VerifyCodeException(int code, String msg){
        super(code,msg);
    }


}
