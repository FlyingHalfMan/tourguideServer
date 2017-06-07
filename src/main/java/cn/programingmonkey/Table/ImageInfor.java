package cn.programingmonkey.Table;



import javax.persistence.*;
import java.io.*;
import java.util.Date;

/**
 * Created by cai on 13/02/2017.
 */

@Entity
public class ImageInfor {


    private int id;
    private String imageId;     // 图片id
    private Date postDate;    // 上传日期
    private String userId;      // 上传的用户

    public ImageInfor(){};


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void writeExternal(ObjectOutput out) throws IOException {

    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
