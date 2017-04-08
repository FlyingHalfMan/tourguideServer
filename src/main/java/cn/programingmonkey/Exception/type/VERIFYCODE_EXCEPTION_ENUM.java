package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 30/01/2017.
 */
public enum  VERIFYCODE_EXCEPTION_ENUM {

    VERIFYCODE_EXCEPTION_NOTFOUND(-300,"没有向该手机发送验证码"),
    VERIFYCODE_EXCEPTION_WRONG(-301,"验证码错误"),
    VERIFYCODE_EXCEPTION_USERLESS(-302,"验证码已经失效"),
    VERIFYCODE_EXCEPTION_OFTEN(-303,"验证码发送过于频繁，1分钟后再重试");

    private int code;
    private String msg;

      VERIFYCODE_EXCEPTION_ENUM(int code,String msg){

          this.code = code;
          this.msg  = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
