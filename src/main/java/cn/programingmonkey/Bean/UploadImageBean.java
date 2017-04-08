package cn.programingmonkey.Bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cai on 2017/3/12.
 */
public class UploadImageBean implements Serializable {

    /**
     *  image 上传的图片文件
     *  desc 图片的描述内容，可选参数
     *  location 图片的拍摄地点
     *
     */

    private CommonsMultipartFile image;
    private String location;
    private Double longitute;
    private Double latitude;




    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLongitute() {
        return longitute;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
