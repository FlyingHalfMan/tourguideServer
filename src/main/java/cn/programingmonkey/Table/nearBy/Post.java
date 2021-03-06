package cn.programingmonkey.Table.nearBy;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by cai on 2017/2/16.
 */

@Entity("post")
@Indexes(
        @Index(value = "userid",fields = @Field("userid"))
)
public class Post  {

    @Id
    private ObjectId id;
    private String                          userid;
    private String                          userName;
    private String                          userImage;
    private String                          title;                       // 文章的标题

    private Long                            likes;                       // 点赞数量
    private Long                            collection;                  // 收藏数量
    private Long                            views;                       // 浏览量
    private Double                          hot;                         // 帖子的热度，由点赞量(0.3)和收藏量(0.5)和阅读量(.2)计算
    private String                          vedio;                       // 视频地址
    private Map<String,String>              images;                      // 图片地址
    private Date                            date;                        // 发布时间


    public Post(){};

    public Post(
                String userid,
                String title,
                long likes,
                long collection,
                long views,
                String vedio,
                Date date) {

        this.userid = userid;
        this.title = title;
        this.likes = likes;
        this.collection = collection;
        this.views = views;
        this.vedio = vedio;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getHot() {
        return hot;
    }

    public void setHot(Double hot) {
        this.hot = hot;
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
}
