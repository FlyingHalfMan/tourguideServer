package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 31/01/2017.
 */
public enum  USER_EXCEPTION_TYPE {

    USER_EXCEPTION_NOTFOUND(-601,"没有找到该用户"),
    USER_EXCEPTION_FROZEN(-602,"用户账户已经被冻结"),
    USER_EXCEPTION_NOTLOGIN(-603,"还没有登录，请先登录"),
    USER_EXCEPTION_INVALIDUSER(-604,"无效的用户"),
    USER_EXCEPTION_INVALIDPWD(-605,"密码错误"),
    USER_EXCEPTION_EXPIRED(-606,"验证失效，请重新登录"),
    USER_EXCEPTION_INVALID_ROLE(-607,"你的权限不足，无法继续进行操作"),
    USER_EXCEPTION_INVALIDE_DATE(-608,"无效的时间，有效时间范围为1999-01-01 至 2016-12-31"),
    USER_EXCEPTION_MOBILE_REGISTED(-609,"手机号码已经注册");



    private int code;
    private String msg;

    USER_EXCEPTION_TYPE(int code, String msg) {
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
