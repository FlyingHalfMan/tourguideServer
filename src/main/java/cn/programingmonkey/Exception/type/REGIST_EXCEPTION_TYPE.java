package cn.programingmonkey.Exception.type;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by cai on 2017/3/30.
 */
public enum REGIST_EXCEPTION_TYPE {
    REGIST_EXCEPTION_MOBILE_REGISTED(-1001,"手机号已经注册"),
    REGIST_EXCEPTION_VERIFYCODE_TOO_OFTEN(-1002,"验证码请求过于频繁,请1分钟后再试"),
    REGIST_EXCEPTION_VERIFYCODE_EXPIRED(-1003,"验证码已经失效,请重新请求"),
    REGIST_EXCEPTION_VERIFYCODE_NOT_SEND(-1004,"未向该手机发送验证码"),
    RESET_PWD_EXCEPTION_VERIFYCODE_INVALID(-1005,"无效的验证码");


    private int code;
    private String msg;

    REGIST_EXCEPTION_TYPE(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
