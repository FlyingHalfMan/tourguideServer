package cn.programingmonkey.Bean;

import java.io.Serializable;

/**
 * Created by cai on 31/01/2017.
 */
public class VerifycodeBean  implements Serializable {

    private String mobile;
    private String verifycode;
    private int code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifycode() {
        return verifycode;
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
