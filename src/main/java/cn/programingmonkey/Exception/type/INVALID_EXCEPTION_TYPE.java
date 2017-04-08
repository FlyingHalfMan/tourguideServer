package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 2017/3/19.
 */
public enum INVALID_EXCEPTION_TYPE {
    INVALID_EXCEPTION_VERIFYCODE(-4001,"无效的验证码"),
    INVALID_EXCEPTION_COUNT(-4002,"无效的账号"),
    INVALID_EXCEPTION_NAME(-4003,"无效的用户名"),
    INVALID_EXCEPTION_NAME_USED(-4004,"用户名已经被使用"),
    INVALID_EXCEPTION_VERIFYCODE_NOT_SEND(-4005,"未向该账号发送过验证码"),
    INVALID_EXCEPTION_COUNT_NOT_REQUEST_REGIST(-4006,"该账号和请求注册的账号不一致,请重新获取验证码"),
    INVALID_EXCEPTION_DIRRERENT_PASSWORD(-4007,"密码不一致，请重新输入"),
    INVALID_EXCEPTION_MOBILE_BIND(-4008,"此手机号已经和当前账号绑定，请使用其他手机号"),
    INVALID_EXCEPTION_MOBILE_REGISTED(-4009,"该手机号已经注册，请事情用其他手机号"),
    INVALID_EXCEPTION_VERIFYCODE_EXPIRED(-4010,"验证码已经失效，请重新获取");

    private int code;
    private String msg;

     INVALID_EXCEPTION_TYPE(int code, String msg){this.code = code;this.msg = msg;}

    public int getCode() {
        return code;
    }
    public String getMsg(){
         return msg;
    }
}
