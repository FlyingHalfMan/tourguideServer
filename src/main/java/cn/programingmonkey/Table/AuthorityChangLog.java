package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by cai on 31/01/2017.
 */
@Entity
@Component
@Table
public class AuthorityChangLog {

    private long id;                // 数据库id
    private String authorityId;     // 执行代码
    private String userId;          // 用户编码
    private String executerId;      // 执行用户ID
    private String Date;            // 执行日期
    private String other;           // 其他说明


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExecuterId() {
        return executerId;
    }

    public void setExecuterId(String executerId) {
        this.executerId = executerId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
