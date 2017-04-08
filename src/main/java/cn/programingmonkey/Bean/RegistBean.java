package cn.programingmonkey.Bean;

/**
 * Created by cai on 30/01/2017.
 */
public class RegistBean {

    private String  mobile;
    private String  verifycode;
    private int     code;

    public String getMobile() {
        return mobile;
    }


    public String getVerifycode() {
        return verifycode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
