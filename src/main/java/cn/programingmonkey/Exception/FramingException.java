package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.FRAMING_EXCEPTION_TYPE;

/**
 * Created by cai on 2017/3/31.
 */
public class FramingException extends BaseException {

    public FramingException(FRAMING_EXCEPTION_TYPE type){

        super(type);
        this.code = type.getCode();
        this.msg  = type.getMsg();
    }
}
