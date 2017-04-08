package cn.programingmonkey.Bean;

import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Utils.Utils;

import java.util.Date;


/**
 * Created by cai on 30/01/2017.
 */
public class SecurityToken  {

    private String userId;
    private String userName;
    private String registDate;
    private String securityToken;
    private String  expiredDate;


    public SecurityToken(UserTable userTable){
        super();

        this.userId = userTable.getUserId();
        this.userName = userTable.getUserName();
        this.registDate = userTable.getRegistDate();
        this.securityToken = userTable.getSecurityToken();
        this.expiredDate = Utils.parseDateToString(new Date(new Date().getTime() +7*24 *60*60 *1000),"yyyyMMddHHmmssSSS");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

}
