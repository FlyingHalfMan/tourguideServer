package cn.programingmonkey.Bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by cai on 2017/3/12.
 */
public class UploadPostBean implements Serializable{

    private Map<String,String> images;
    private String vedio;
    private String title;

    public Map<String,String> getImages() {
        return images;
    }

    public void setImages(Map<String,String> images) {
        this.images = images;
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
}
