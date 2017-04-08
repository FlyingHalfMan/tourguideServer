package cn.programingmonkey.Table;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by cai on 13/02/2017.
 */
@Table(name = "tg_stepimages")
@Entity
@Component
public class StepImages {

    private int id;
    private String stepsId;
    private String imageInforId;
    private String date;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStepsId() {
        return stepsId;
    }

    public void setStepsId(String stepsId) {
        this.stepsId = stepsId;
    }

    public String getImageInforId() {
        return imageInforId;
    }

    public void setImageInforId(String imageInforId) {
        this.imageInforId = imageInforId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
