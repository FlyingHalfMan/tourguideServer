package cn.programingmonkey.Bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by cai on 2017/3/12.
 */
public class UploadPostBean implements Serializable{

    private List<Map<String,String>> images;
    private String vedio;
    private long  date;
    private String conent;

    public List<Map<String,String>> getImages() {
        return images;
    }

    public void setImages(List<Map<String,String>> images) {
        this.images = images;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent;
    }
}
