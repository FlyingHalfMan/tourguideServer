package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Utils.MongoUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cai on 2017/2/27.
 */
@Repository
public class ImageInforDao {

    /**
     * 根据图片的id来进行查找
     * @param id
     * @return
     */
    public ImageInfor find(String id) {

        return MongoUtil.getInstanceDataStore().find(ImageInfor.class).field("_id").equal(id).get();
    }


    /**
     * 添加一张图片
     * @param imageInfor
     */
    public void addImageInfor(ImageInfor imageInfor){

        MongoUtil.getInstanceDataStore().save(imageInfor);
    }

    /**
     * 删除一张图片信息
     * @param imageInfor
     */
    public void deleteImageInfor(ImageInfor imageInfor){

        MongoUtil.getInstanceDataStore().delete(imageInfor);
    }
}
