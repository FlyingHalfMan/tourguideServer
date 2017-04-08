package cn.programingmonkey.Table;

import cn.programingmonkey.Utils.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cai on 29/01/2017.
 */
@Table(name = "tg_verifycode")
@Entity
@Component
public class VerifyCodeTable {

    private long    id;
    private String  mobile;         // 手机号码
    private String  verifycode;     // 验证码
    private int    code;           // 用途码
    private Date    sendDate;       // 发送日期
    private Date    expiredDate;    // 过期日期


    public VerifyCodeTable(String mobile,String verifycode,int code){
        this.mobile = mobile;
        this.verifycode = verifycode;
        this.code = code;
        this.sendDate = new Date();
        this.expiredDate = Utils.getDateWithDuration( 5 *60 * 1000);
    }

    public VerifyCodeTable() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int  getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
