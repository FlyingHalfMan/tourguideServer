package cn.programingmonkey.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by cai on 29/01/2017.
 */
@Repository
public class BaseDao<T>  {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void add( T t){
        entityManager.persist(t);
    }

    @Transactional
    public void update(T t){
        entityManager.merge(t);
    }

    @Transactional
    public void delete(T t){
        entityManager.remove(t);
    }

    public T find( Class<T> tClass, Serializable id){
       return  entityManager.find(tClass,id);
    }
}
