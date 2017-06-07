package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.USER_EXCEPTION_TYPE;

/**
 * Created by cai on 29/01/2017.
 */

public class UserException extends BaseException {

    public UserException(USER_EXCEPTION_TYPE type){
        super(type.getCode(),type.getMsg());
    }

}

