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


    /*
        是否存在图片集合
        文字描述 或者 网站连接地址
        AR 模型
        视频地址


     */


    /*
     */

    private int id;
    private String sceneryId;
    private String descrition;
    private String location;


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
}
