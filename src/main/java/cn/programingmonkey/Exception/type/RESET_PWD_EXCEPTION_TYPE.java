package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 2017/3/30.
 */
public enum RESET_PWD_EXCEPTION_TYPE  {

    RESET_PWD_EXCEPTION_MOBILE_UNRIGHTED(-901,"手机号未注册"),
    RESET_PWD_EXCEPTION_INVALID_VEIFYCODE(-902,"无效的验证码"),
    RESET_PWD_EXCEPTION_VERIFYCODE_EXPIRED(-903,"验证码已经失效,请重新获取"),
    RESET_PWD_EXCEPTION_PASSWORD_LEN_NOT_ENOUGH(-904,"密码长度不足"),
    RESET_PWD_EXCEPTION_INVALID_PASSWORD(-905,"无效的密码"),
    RESET_PWD_EXCEPTION_VERIFYCODE_SEND_TOO_OFTEN(-906,"验证码请求过于频繁，请一分钟后再试");

    ;
    private int code;
    private String msg;

    RESET_PWD_EXCEPTION_TYPE(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
