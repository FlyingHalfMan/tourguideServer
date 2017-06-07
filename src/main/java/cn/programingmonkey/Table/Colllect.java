package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by cai on 2017/4/21.
 */
@Table(name = "tg_collect")
@Component
@Entity
public class Colllect {

    private int id;
    private String userId;
    private String postId;
    private String addDate;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
