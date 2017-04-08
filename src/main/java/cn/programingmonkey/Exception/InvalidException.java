package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.INVALID_EXCEPTION_TYPE;

/**
 * Created by cai on 29/01/2017.
 */
public class InvalidException  extends  BaseException{

    public InvalidException(INVALID_EXCEPTION_TYPE type) {
        super(type);
        this.code = type.getCode();
        this.msg = type.getMsg();
    }

    public InvalidException(int code, String msg) {
        super(code, msg);
    }

    public InvalidException(String string){
        super(403,string);
    }
}
