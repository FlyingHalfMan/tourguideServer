package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 31/01/2017.
 */
public enum  PASSWORD_EXCEPTION_TYPE {

    PASSWORD_EXCEPTION_WRONG(-500,"账号密码不一致"),
    PASSWORD_EXCEPTION_INVALIDLENGTH(-501,"密码长度不足"),
    PASSWORD_EXCEPTION_DIFFERENT(-502,"两次密码不一致");


    private int code;
    private String msg;

    PASSWORD_EXCEPTION_TYPE(int code, String msg) {
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
