
package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Utils.MongoUtil;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
//
//
///**
// * Created by cai on 2017/2/16.
// */
@Repository
public class PostDao  {

    /**
     * 插入
     * @param post
     */
   public void add(Post post){
       MongoUtil.getInstanceDataStore().save(post);
   }

    /**
     * 删除
     * @param post
     */
   public void delete(Post post){
    MongoUtil.getInstanceDataStore().delete(post);
   }


    /**
     * 更新操作
     * @param post
     */
   public void update(Post post){

       Query<Post> query = MongoUtil.getInstanceDataStore()
                                    .createQuery(Post.class)
                                    .field("id").equal(post.getId());
       UpdateOperations<Post>  updateOperations = MongoUtil.getInstanceDataStore()
                                                           .createUpdateOperations(Post.class)
                                                           .set("views",post.getViews())
                                                           .set("collection",post.getCollection())
                                                           .set("likes",post.getLikes())
                                                           .set("hot",post.getViews() * 0.2 + post.getCollection() * 0.5 +post.getLikes() * 0.3);

       MongoUtil.getInstanceDataStore().update(query,updateOperations);
   }

    /**
     * 根据用户的ID查找-3.3
     *  @param  userId
     */
    public List<Post>  findByUserId(String userId,int offset,int limit){

       return MongoUtil.getInstanceDataStore().createQuery(Post.class)
                                              .filter("userid ==",userId).order("-date")
                                              .offset(offset)
                                              .limit(limit)
                                              .asList();
    }

    /**
     * 根据帖子的Id来进行查找
     * @param postId
     * @return
     */
    public Post findByPostId(String postId){

       return MongoUtil.getInstanceDataStore().find(Post.class).filter("id ==",new ObjectId(postId)).get();
    }

    public List<Post> findByHot(int offset,int limit){

        return MongoUtil.getInstanceDataStore().createQuery(Post.class)
                                               .order("-hot")
                                               .offset(offset)
                                               .limit(limit)
                                               .asList();
    }

    public List<Post> findByDate(int offset,int limit){

        return MongoUtil.getInstanceDataStore().createQuery(Post.class)
                                               .order("-date")
                                               .offset(offset)
                                               .limit(limit)
                                               .asList();
    }

    public List<Post> findPostwithLocation(double minLa,double minLo,double maxLa, double maxLo ,int offset, int limit){

        return MongoUtil.getInstanceDataStore().find(Post.class)
                                               .field("latitute").lessThanOrEq(maxLa)
                                               .field("latitute").greaterThanOrEq(minLa)
                                               .field("longitude").lessThanOrEq(maxLo)
                                               .field("longitude").greaterThanOrEq(minLo)
                                               .offset(offset).limit(limit)
                                               .asList();
    }

}
