package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.MOBILE_EXCEPTION_TYPE;

/**
 * Created by cai on 30/01/2017.
 */
public class MobileException  extends  BaseException{


    private String mobile;

    public MobileException(MOBILE_EXCEPTION_TYPE mobile_exception_type,String mobile){

        super(mobile_exception_type.getCode(),mobile_exception_type.getMsg());
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
