package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 31/01/2017.
 */
public enum  MOBILE_EXCEPTION_TYPE {

    MOBILE_EXCEPTION_UNREGIST(-400,"手机号未注册"),
    MOBILE_EXCEPTION_REGISTED(-401,"手机号已经注册"),
    MOBILE_EXCEPTION_INVAlIDMOBILE(-402,"无效的手机号");

    private int code;
    private String msg;

    MOBILE_EXCEPTION_TYPE(int code,String msg){
            this.code =code;
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

}
