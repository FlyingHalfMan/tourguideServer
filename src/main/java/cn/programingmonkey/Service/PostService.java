package cn.programingmonkey.Service;

import cn.programingmonkey.Bean.PostBean;
import cn.programingmonkey.Bean.PostHomeBean;
import cn.programingmonkey.Dao.BaseDao;
import cn.programingmonkey.Dao.PostDao;
import cn.programingmonkey.Dao.PostImageDao;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Exception.type.POST_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.PostException;
import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Table.nearBy.PostImage;
import cn.programingmonkey.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cai on 2017/2/17.
 */
@Service
public class PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostImageDao postImageDao;


    public List<PostHomeBean> getPostsBySearchContentAndType(String search,int type,int offset,int limit){

        List<PostHomeBean> postHomeBeans = null;
        // 不使用分类搜索
        if (type == -1){
            postHomeBeans = paraseToPostHomeBean(postDao.searchPostsByContent(search,offset,limit));
        }
        else {
           postHomeBeans = paraseToPostHomeBean(postDao.findPostByOption(offset,limit,type));
        }
        return postHomeBeans;
    }



    /**
     *  添加一个新的post。
     *  post数据将被保存在mongo数据库中
     *  而post和图片的关系将被保存在mysql数据库中
     */
    public void addPost(Post post) {

       if (post.getImages() !=null && post.getVedio() != null){
           throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_IMAGE_VEDIO_BOTH);
       }

       // 将图片写入到mongo数据库中。
        postDao.add(post);
       //将图片和postId建立关联并保存在mysql 数据库中
        Map<String,String> images = post.getImages();
        if(images != null && images.size() > 1) {
           for (String str :images.keySet()){
               PostImage postImage = new PostImage();
               postImage.setImageId(str);
               postImage.setPostId(post.getId().toString());
               postImageDao.add(postImage);
           }
        }
    }

    /**
     * 删除post
     *
     * @param id
     */
    public void deletePost(String id,String userId) {
        Post post = postDao.findByPostId(id);
        if (post == null)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_POST_NOTFOUND);
        UserTable userTable = userDao.findUserByUserId(userId);
        if (!post.getUserid().equals(userId)  && userTable.getRole() < 2 )
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_UNAUTHORIZATION);
        postDao.delete(post);
        // 删除图片
        postImageDao.deleteByPostId(id);
    }



    public ArrayList<Boolean> postStatus(String postId,String userId){


        return null;
    }


    /**
     * 修改点赞数
     *
     * @param id
     * @return
     */
    public PostHomeBean updateLikes(String  id) {

        Post post = postDao.findByPostId(id);
        if (post == null)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_NONE);

        post.setLikes(post.getLikes() + 1);
        postDao.update(post);
        return parseToPostHomeBean(post);
    }


    /**
     * 修改收藏数目
     *
     * @param id
     * @return
     */
    public PostHomeBean updateCollection(String id) {

        Post post = postDao.findByPostId(id);
        if (post == null)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_NONE);
        post.setCollection(post.getCollection() + 1);
        postDao.update(post);
        return parseToPostHomeBean(post);
    }

    /**
     * @param id
     * @return
     */
    public PostHomeBean findPostByPostId(String id) {

        Post post = postDao.findByPostId(id);
        // 查询的数据为null,抛出异常
        if (post == null)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_NONE);
        // 返回数据
        post.setViews(post.getViews() +1);
        postDao.update(post);
        return parseToPostHomeBean(post);
    }


    /**
     * 根据用户Id来进行查找
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<PostHomeBean> findPostByUserId(String userId, int offset, int limit) {

        List<Post> posts = postDao.findByUserId(userId, offset, limit);
        // 查询的数据为null,抛出异常
        if (posts == null || posts.size() < 1)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_USER_NONE);
        // 返回数据
        return paraseToPostHomeBean(posts);
    }
//
    /**
     *  根据热度来查找
     * @param offset
     * @param limit
     * @return
     */
    public List<PostHomeBean> findPostByHot(int offset, int limit) {

        List<Post> posts = postDao.findByHot(offset, limit);
        // 查询的数据为null,抛出异常
        if (posts == null || posts.size() < 1)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_DATABASE_NONE);
        // 返回数据
        return paraseToPostHomeBean(posts);
    }

//
    /**
     *  时间顺序来查找,返回当前数据库中最新的数据，默认是按距离来查找
     * @param offset
     * @param limit
     * @return
     */

    public List<PostHomeBean> findPostByOption(int offset,int limit,int option) {

        List<Post> posts = postDao.findPostByOption(offset,limit, option);
        // 查询的数据为null,抛出异常
        if (posts == null || posts.size() < 1)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_DATABASE_NONE);
        return  paraseToPostHomeBean(posts);
    }
//
    /**
     *   地点查找
     *   @param latitute    // 经度
     *   @param longitude   // 纬度
     *   @param range       // 范围
     *   @param offset
     *   @param limit
     *   根据用户的经纬度和range 去计算出有效经纬度范围(最大，最小)
     * */
    public List<PostHomeBean> findPostByLocation(double latitute,double longitude,double range,int offset,int limit) {

        // 在这个地方去获取最大经纬度和最小经纬度
        double[] ground = Utils.getAround(latitute,longitude,range);
        List<Post> posts = postDao.findPostwithLocation(ground[0],ground[1],ground[2],ground[3],offset,limit);
        if (posts == null || posts.size() <1)
            throw new PostException(POST_EXCEPTION_TYPE.POST_EXCEPTION_TYPE_DATABASE_NONE);
        return paraseToPostHomeBean(posts);
    }



    private List<PostHomeBean> paraseToPostHomeBean(List<Post> posts) {

        List<PostHomeBean> postHomeBeans = new ArrayList<PostHomeBean>();
        for(Post post :posts)
        {
            UserTable userTable = userDao.findUserByUserId(post.getUserid());
            PostHomeBean postHomeBean = new PostHomeBean(post,userTable);
            postHomeBeans.add(postHomeBean);
        }
        return postHomeBeans;
    }
//
    private PostHomeBean parseToPostHomeBean(Post post) {

        UserTable userTable = userDao.findUserByUserId(post.getUserid());
        return new PostHomeBean(post,userTable);
    }



    private PostBean parseToPostBean(Post post,UserTable userTable){

        return new PostBean(userTable,post);
    }

}
