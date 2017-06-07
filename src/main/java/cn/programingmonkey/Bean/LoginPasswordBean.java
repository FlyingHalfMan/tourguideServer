package cn.programingmonkey.Bean;

import java.io.Serializable;

/**
 * Created by cai on 31/01/2017.
 */
public class LoginPasswordBean  implements Serializable{

    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
