package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cai on 2017/3/21.
 */
@Table(name = "tg_role")
@Entity
@Component
public class RoleTable implements Serializable {

    private int id;
    private String path;
    private String role;   // 0 标示管理员  1标示普通用户，-1表示未注册用户


    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
