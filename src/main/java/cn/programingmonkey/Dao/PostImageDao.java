package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.nearBy.PostImage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by cai on 2017/3/29.
 */
@Repository
@Transactional
public class PostImageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(PostImage postImage){
        entityManager.persist(postImage);
    }

    public void delete(PostImage postImage){
        entityManager.remove(postImage);
    }

    public String findPostIdByImageId(String imageId){

        String sql = "select p.postId from PostImage  as p where p.imageId =:imageId ";
        Query query = entityManager.createQuery(sql).setParameter("imageId",imageId);
        return query.getResultList() == null  || query.getResultList().size() < 1 ?null : (String) query.getResultList().get(0);
    }

    public void deleteByPostId(String postId){

        String sql ="delete  from PostImage  as p  where p.postId = :postId";
        Query query = entityManager.createQuery(sql).setParameter("postId",postId);
        query.executeUpdate();
    }

}
