package cn.programingmonkey.Table;

import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cai on 26/01/2017.
 */
@Entity
@Table(name = "tg_user")
@Component
public class UserTable {

    private int id;                 // id
    private String userId;          // 用户主键
    private String userName;        // 用户昵称
    private String gender;          // 用户性别
    private String userImage;       // 用户头像
    private String salt;            // 盐
    private Date birthDate;         // 出生日期
    private String registDate;      // 注册日期
    private String securityToken;   // 安全验证码
    private String expiredDate;     // 验证失效时间
    private int     role;               // 用户角色

    private String email;
    private String mobile;
    private String nativePlace;


    public static class Build
    {
        private  String userId;     // required
        private  String mobile;     // required

        // optional
        private  String userName;
        private  String gender;
        private  String userImage;
        private  String salt;
        private  Date   birthdate;
        private  String registDate;
        private  String securityToken;
        private  String expiredDate;
        private  String email;
        private  String nativePlace;

        public Build(String userId, String mobile) {
            this.userId = userId;
            this.mobile = mobile;
        }



        public Build name(String name) { userName = name;return this;}
        public Build gender(String gen){gender =gen;return this; }
        public Build userImage(String image){userImage = image;return this;}
        public Build salt(String sa){salt = sa;return this;}
        public Build birthdate(Date date){birthdate = date;return this;}
        public Build registDate(String date){registDate = date;return this;}
        public Build securityToken(String token){securityToken = token;return this;}
        public Build expiredDate(String date){expiredDate = date;return this;}
        public Build email(String em){email = em;return this;}
        public Build nativePlace(String place){nativePlace = place;return this;}

        public UserTable build(){
            return new UserTable(this);
        }

    }

    public UserTable(){};
    private UserTable(Build build)
    {
        this.userId    = build.userId;
        this.userName  = build.userName;
        this.userImage = build.userImage;
        this.gender    = build.gender;
        this.salt      = build.salt;
        this.birthDate = build.birthdate;
        this.registDate = build.registDate;
        this.securityToken = build.securityToken;
        this.expiredDate = build.expiredDate;
        this.email = build.email;
        this.mobile = build.mobile;
        this.nativePlace = build.nativePlace;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NotNull
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String rigistDate) {
        this.registDate = rigistDate;
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
