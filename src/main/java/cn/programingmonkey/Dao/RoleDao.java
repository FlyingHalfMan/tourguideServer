package cn.programingmonkey.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by cai on 2017/3/21.
 */
@Repository
@Transactional
public class RoleDao  {

    @PersistenceContext
    private EntityManager entityManager;

    public  int findRoleByPath(String path)
    {
        String sql = "select r.role from RoleTable  as r where :path like r.path%";
        Query query =  entityManager.createQuery(sql).setParameter("path",path);
        return query.getResultList() == null || query.getResultList().size() < 1 ? -100 : Integer.parseInt((String) query.getResultList().get(0));
    }



}
