package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 2017/3/30.
 */
public enum  LOGIN_EXCEPTION_TYPE {

    LOGIN_EXCEPTION_MOBILE_UNRIGHTED(-801,"手机号未注册"),
    LOGIN_EXCEPTION_WRONG_PASSWORD(-802,"密码错误,请重新输入，忘记密码请选择重置"),
    LOGIN_EXCEPTION_VERIFYCODE_NOT_SEND(-803,"没有向该手机发送过验证码"),
    LOGIN_EXCEPTION_VERIFYCODE_INVALID_WRONG(-804,"无效的验证码或者验证码错误"),
    LOGIN_EXCEPTION_VERIFYCDOE_SEND_OFFTEN(-805,"请勿重复请求验证码，1分钟后可重新发送"),
    LOGIN_EXCEPTION_VERIFYCODE_EXPIRED(-806,"验证码已经失效，请重新请求");


    private int code;
    private String msg;


    LOGIN_EXCEPTION_TYPE(int code, String msg) {
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
