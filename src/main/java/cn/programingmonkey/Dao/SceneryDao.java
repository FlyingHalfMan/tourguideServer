package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.Scenery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by cai on 2017/5/15.
 */

@Repository
@Transactional
public class SceneryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Scenery getSceneryById(String id){

        String sql = "select  s from Scenery  as s where s.sceneryId = :id";
        Query query = entityManager.createQuery(sql).setParameter("id",id);
        return query.getResultList() == null ? null : (Scenery) query.getResultList().get(0);
    }

    public List<Scenery> getSceneries(){

        String sql = "select s from Scenery as s";
        Query query = entityManager.createQuery(sql);
        return query.getResultList() == null ? null : query.getResultList();
    }
}
