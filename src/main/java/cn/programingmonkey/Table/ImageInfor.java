package cn.programingmonkey.Table;

import cn.programingmonkey.Utils.Sift.MyPoint;
import org.aspectj.apache.bcel.verifier.statics.DOUBLE_Upper;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cai on 13/02/2017.
 */

@Entity
public class ImageInfor implements Serializable{

    @Id
    private ObjectId id;
    private String imageId;     // 图片id 根据
    private String classId;     // 图片分类号
    private String location;    // 图片上传的地，系统自动识别或者用户自己添加
    private Double latitute;    // 精度
    private Double longitude;   // 纬度
    private Date postDate;    // 上传日期
    private String userId;      // 上传的用户
    private String pHash;
    private List<MyPoint> sift;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Double getLatitute() {
        return latitute;
    }

    public void setLatitute(Double latitute) {
        this.latitute = latitute;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getpHash() {
        return pHash;
    }

    @Column(unique = true)
    public void setpHash(String pHash) {
        this.pHash = pHash;
    }

    public List<MyPoint> getSift() {
        return sift;
    }

    public void setSift(List<MyPoint> sift) {
        this.sift = sift;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }



}
