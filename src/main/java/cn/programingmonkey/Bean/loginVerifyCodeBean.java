package cn.programingmonkey.Bean;

/**
 * Created by cai on 31/01/2017.
 */
public class loginVerifyCodeBean {

    private String mobile;
    private String verifycode;
    int code;

    public String getMobile() {
        return mobile;
    }


    public String getVerifycode() {
        return verifycode;
    }

    public int getCode() {
        return code;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
