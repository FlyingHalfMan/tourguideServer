package cn.programingmonkey.Bean;

import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Utils.Utils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cai on 2017/4/7.
 */
public class PostBean implements Serializable {

    private String                          postId;
    private String                          title;                       // 文章的标题
    private String                          location;                    //地理位置
    private Long                            likes;                       // 点赞数量
    private Long                            collection;                  // 收藏数量
    private Long                            views;                       // 浏览量
    private String                          vedio;                       // 视频地址
    private Map<String,String>              images;                      // 图片地址+描述
    private String                          content;                     // 文字内容
    private String                          postDate;                    // 发布时间
    private String                          userId;
    private String                          userImage;
    private String                          userName;


    public PostBean(UserTable userTable, Post post){

        this.postId = post.getId().toString();
        this.title  = post.getTitle();
        this.likes = post.getLikes();
        this.views = post.getViews();
        this.collection = post.getCollection();
        this.vedio = post.getVedio();
        this.images = post.getImages();
        this.postDate = Utils.parseDateToString(post.getDate(), Constant.DATE_FORATE);
        this.userId = userTable.getUserId();
        this.userImage = userTable.getUserImage();
        this.userName = userTable.getUserName();
    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getCollection() {
        return collection;
    }

    public void setCollection(Long collection) {
        this.collection = collection;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
