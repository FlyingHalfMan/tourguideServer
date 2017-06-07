package cn.programingmonkey.Table.nearBy;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cai on 2017/3/29.
 */

@Entity
@Table
@Component
public class PostImage {

    private int id;
    private String postId;
    private String imageId;
    private String userId;
    private Date   postDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
