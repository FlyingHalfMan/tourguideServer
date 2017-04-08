package cn.programingmonkey.Bean;

import cn.programingmonkey.Table.UserTable;

import java.util.Date;

/**
 * Created by cai on 26/01/2017.
 */
public class UserBean {

    private String  userId;         // 用户主键
    private String  userName;       // 用户昵称
    private String  gender;         // 用户性别
    private String  userImage;      // 用户头像
    private Date    birthDate;      // 出生日期
    private String  rigistDate;     // 注册日期

    private String  email;
    private String  mobile;

    public UserBean(UserTable userTable){

        this.userId = userTable.getUserId();
        this.userName = userTable.getUserName();
        this.gender = userTable.getGender();
        this.userImage = userTable.getUserImage();
        this.birthDate = userTable.getBirthDate();
        this.rigistDate = userTable.getRegistDate();
        this.email = userTable.getEmail();
        this.mobile = userTable.getEmail();
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRigistDate() {
        return rigistDate;
    }

    public void setRigistDate(String rigistDate) {
        this.rigistDate = rigistDate;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
