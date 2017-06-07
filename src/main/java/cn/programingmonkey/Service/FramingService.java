package cn.programingmonkey.Service;

import cn.programingmonkey.Dao.ImageInforDao;
import cn.programingmonkey.Dao.PostImageDao;
import cn.programingmonkey.Service.SIFT.Point;
import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by cai on 2017/3/31.
 */
@Service
public class FramingService extends BaseService {

    @Autowired
    private ImageInforDao imageInforDao;

    @Autowired
    private PostImageDao postImageDao;

    private final static  String  SIFT_PROPERTIES = "/resources/tourguide/properties/sift.properties";

    /**
     *
     * @param imageName   上传图片的名称
     * @param userId      用户的Id
     * @param sift
     * @param pHash
     * @param la
     * @param lo
     * @param location
     */




    public void addImageInfor(String imageName,
                              String userId,
                              List sift,
                              String pHash,
                              Double la,
                              Double lo,
                              String location){

        ImageInfor imageInfor = new ImageInfor();
        imageInfor.setImageId(imageName);
        imageInfor.setUserId(userId);
        //imageInfor.setSift(sift);
        imageInfor.setPostDate(new Date());

        imageInforDao.addImageInfor(imageInfor);
    }

    /**
     * 根据SIFT查看相似的图片
     * @param Points
     * @return
     */
    public List<String> getSimilarImage(List<Point> Points,String imageName){

        List<String> imageNames = new ArrayList<String>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(SIFT_PROPERTIES));
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object key :properties.keySet()){
                if (key.toString().equals(imageName)) continue;
                List<Point> points = JsonUtils.parseJson(properties.getProperty(key.toString()));
                int sim =  SIFTService.getSimilarPointsNum(Points,points);
                float percent = (float) sim / Points.size();
                if (percent < .5f) continue;
                else {imageNames.add(key.toString());}
            }

        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageNames;
    }


    public String isSameImageExits(List<Point> Points){

        try {
            FileInputStream inputStream = new FileInputStream(new File(SIFT_PROPERTIES));
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object key :properties.keySet()){
                List<Point> points = JsonUtils.parseJson(properties.getProperty(key.toString()));
                int sim =  SIFTService.getSimilarPointsNum(Points,points);
                float percent = (float) sim / Points.size();
                if (percent > 0.98) return key.toString();
            }
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Point> getSIFTByImageName(String imageName){

        try {
            Properties properties  = new Properties();
            File sifFile = new File(SIFT_PROPERTIES);
            FileInputStream fis  = new FileInputStream(sifFile);
            properties.load(fis);
            String str = properties.getProperty(imageName);
            List<Point> Points = JsonUtils.parseJson(str);
            fis.close();
            return Points;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public void saveSIFT(List<Point> points,String imageName){

        try {
            Properties properties  = new Properties();
            File sifFile = new File(SIFT_PROPERTIES);
            if (!sifFile.exists()){
                boolean tf = sifFile.createNewFile();
                if (tf == false){
                    System.out.println("文件创建失败");
                }
                else {
                    System.out.println("文件创建成功");
                }
            }
            FileInputStream fis  = new FileInputStream(sifFile);
            FileOutputStream fos = new FileOutputStream(sifFile,true);
            properties.load(fis);
            String str = new ObjectMapper().writeValueAsString(points);
            System.out.println("parse" +str);
            properties.setProperty(imageName,str);
            properties.store(fos,"save");
            fis.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public ImageInfor find(String id){

        return imageInforDao.find(id);
    }

    /**
     * 根据相似图片提取相识的帖子信息
     * @param images
     * @return
     */
    public Set<String> getSimlarPost(List<String> images){

        Set<String> posts = new HashSet<String>();
        for(String imageId : images){
            String postId =  postImageDao.findPostIdByImageId(imageId);
            if (postId == null || postId.length() <1)
                continue;
            else {
                posts.add(postId);
            }
        }
        return posts;
    }
}
