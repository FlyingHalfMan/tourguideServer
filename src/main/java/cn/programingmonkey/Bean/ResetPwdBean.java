package cn.programingmonkey.Bean;

import java.io.Serializable;

/**
 * Created by cai on 31/01/2017.
 */
public class ResetPwdBean  implements Serializable {

    private String mobile;
    private String newPwd;
    private String newPwds;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwds() {
        return newPwds;
    }

    public void setNewPwds(String newPwds) {
        this.newPwds = newPwds;
    }
}
