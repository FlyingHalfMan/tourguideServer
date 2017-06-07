package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by cai on 13/02/2017.
 */
@Entity
@Table(name = "tg_scenery")
@Component
public class Scenery {


    private int id;
    private String name;
    private String sceneryId;
    private String descrition;
    private String location;
    private String imageHref;
    private String vedioHref;
    private String baikeHref;
    private String image;
    private int classify;

    private float latitude;
    private float longitude;


    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSceneryId() {
        return sceneryId;
    }

    public void setSceneryId(String sceneryId) {
        this.sceneryId = sceneryId;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getVedioHref() {
        return vedioHref;
    }

    public void setVedioHref(String vedioHref) {
        this.vedioHref = vedioHref;
    }

    public String getBaikeHref() {
        return baikeHref;
    }

    public void setBaikeHref(String baikeHref) {
        this.baikeHref = baikeHref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
