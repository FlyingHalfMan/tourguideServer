package cn.programingmonkey.Bean;

/**
 * Created by cai on 2017/3/21.
 */

import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Utils.Utils;

import javax.rmi.CORBA.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 帖子首页的数据
 */
public class PostHomeBean  implements Serializable {

    private String postId;
    private String postImage;
    private int    imageCount;
    private String vedio;
    private String title;
    private String postDate;

    private long   likes;       // 点赞数
    private long   collects;    // 收藏数
    private long   views;       // 查看数量

    private String location;    // 帖子发布的地点
    private String distance;    // 与用户位置当前未知的距离

    private String userId;
    private String userName;
    private String userImage;
    private Map<String,String> images;

    public PostHomeBean(){};

    public PostHomeBean(Post post,UserTable user)
    {
        this.postId    = post.getId().toString();
        this.vedio     = post.getVedio();
        this.title     = post.getTitle();
        this.postDate  = Utils.parseDateToString(post.getDate(), Constant.DATE_FORATE);
        this.likes     = post.getLikes();
        this.collects  = post.getCollection();
        this.views     = post.getViews();
        this.userId    = user.getUserId();
        this.userImage = user.getUserImage();
        this.userName  = user.getUserName();
        Map<String,String> images = post.getImages();
        this.images    = images;
        this.postImage = (String)images.keySet().toArray()[0];
        this.imageCount = images.size();

    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public void setCollects(long collects) {
        this.collects = collects;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getCollects() {
        return collects;
    }

    public void setCollects(int collects) {
        this.collects = collects;
    }

    public long getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }
}
