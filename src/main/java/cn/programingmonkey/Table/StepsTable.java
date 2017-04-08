package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by cai on 13/02/2017.
 */

@Table(name = "tg_steps")
@Entity
@Component
public class StepsTable {

    private int id;
    private String userId;
    private String date;
    private double longitude;   // 用户的经纬度
    private double latitute;

    @Id
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
