package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.AuthorityChangLog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by cai on 31/01/2017.
 */
@Repository
public class AuthorityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public AuthorityChangLog find(long id){

        return entityManager.find(AuthorityChangLog.class,id);
    }

    @Transactional
    public void add(AuthorityChangLog authorityChangLog){

         entityManager.persist(authorityChangLog);
    }

    @Transactional
    public void delete(AuthorityChangLog authorityChangLog){

        entityManager.remove(authorityChangLog);
    }


}
