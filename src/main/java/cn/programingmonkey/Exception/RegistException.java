package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.REGIST_EXCEPTION_TYPE;

/**
 * Created by cai on 2017/3/30.
 */

public class RegistException  extends BaseException{

    public RegistException(REGIST_EXCEPTION_TYPE type){

        super(type.getCode(),type.getMsg());
    }
}
