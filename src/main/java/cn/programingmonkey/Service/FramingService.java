package cn.programingmonkey.Service;

import cn.programingmonkey.Dao.ImageInforDao;
import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Utils.Sift.MyPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by cai on 2017/3/31.
 */
@Service
public class FramingService extends BaseService {

    @Autowired
    ImageInforDao imageInforDao;

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
                              List<MyPoint> sift,
                              String pHash,
                              Double la,
                              Double lo,
                              String location){

        ImageInfor imageInfor = new ImageInfor();
        imageInfor.setImageId(imageName);
        imageInfor.setUserId(userId);
        imageInfor.setSift(sift);
        imageInfor.setpHash(pHash);
        imageInfor.setLongitude(lo);
        imageInfor.setLatitute(la);
        imageInfor.setLocation(location);
        imageInfor.setPostDate(new Date());

        imageInforDao.addImageInfor(imageInfor);
    }

    public ImageInfor find(String id){

        return imageInforDao.find(id);
    }
}
