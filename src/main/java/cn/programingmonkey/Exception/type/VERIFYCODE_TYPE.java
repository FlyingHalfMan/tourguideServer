package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 30/01/2017.
 */
public enum  VERIFYCODE_TYPE {

    VERIFYCODE_TYPE_LOGIN(0,"登录"),
    VERIFYCODE_TYPE_RIGIST(1,"注册"),
    VERIFYCODE_TYPE_RESETPWD(2,"密码重置");

    private int code;
    private String msg;

     VERIFYCODE_TYPE(int code,String msg){

        this.code = code;
        this.msg =msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
