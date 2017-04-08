package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.VerifyCodeTable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;

/**
 * Created by cai on 30/01/2017.
 */
@Repository
public class VerifyCodeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public VerifyCodeTable find(Serializable id){

        return entityManager.find(VerifyCodeTable.class,id);
    }

    @Transactional
    public VerifyCodeTable findByMobile(String mobile,int code){

        String sql = "select v from VerifyCodeTable  as v where v.mobile =:mobile and v.code = :code";
        Query query = entityManager.createQuery(sql)
                                   .setParameter("mobile",mobile)
                                   .setParameter("code",code);

        return (query.getResultList() == null || query.getResultList().size() < 1)
                ?  null
                : (VerifyCodeTable) query.getResultList().get(0);
    }
    @Transactional
    public void add(VerifyCodeTable verifyCodeTable){

        entityManager.persist(verifyCodeTable);
    }

    @Transactional
    public void delete(VerifyCodeTable verifyCodeTable){

        entityManager.remove(verifyCodeTable);
    }

    @Transactional
    public void update(VerifyCodeTable verifyCodeTable)
    {
        entityManager.merge(verifyCodeTable);
    }



}
