package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.ImageInfor;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by cai on 2017/3/20.
 */
public class ImageMangoDao {

    private MongoTemplate mongoTemplate;

    private static MongoClient mongoClient;
    private static MongoDatabase database;


    static {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase("fudaTour");

            if (database.getCollection("tg_imageInfor") == null) {
                database.createCollection("tg_imageInfor");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据postId来获取图片数组
     * @param postId
     * @return
     */
   public List<ImageInfor> findByPostId(String postId)
   {
       return mongoTemplate.find(new Query(Criteria.where("postId").is(postId)),ImageInfor.class);
   }

    /**
     * 根据用户id来进行查找
     * @param userId
     * @return
     */

   public List<ImageInfor> findByUserId(String userId) {

      return mongoTemplate.find(new Query(Criteria.where("userId").is(userId)),ImageInfor.class);
   }

    public void insert(ImageInfor imageInfor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

       DBObject object = (DBObject) BeanUtils.cloneBean(imageInfor);
       mongoTemplate.insert(object);
    }

    public void update(ImageInfor imageInfor)
    {

    }
}
