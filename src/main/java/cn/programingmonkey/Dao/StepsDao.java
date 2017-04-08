package cn.programingmonkey.Dao;

import cn.programingmonkey.Exception.BaseException;
import cn.programingmonkey.Table.StepsTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by cai on 2017/3/20.
 */
public class StepsDao  {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 根据用户id来进行查找
     * @param userId
     * @return
     */
    public List<StepsTable> getStepsByUserId(String userId,int offset,int limit)
    {
        String sql = "select  s from StepsTable  as s order by s.date desc ";
        Query query = entityManager.createQuery(sql).setFirstResult(offset).setMaxResults(limit);
       return  query.getResultList() ==null ||query.getResultList().size() <1 ?  null :query.getResultList();
    }
    public StepsTable getStepTableByStepId(String stepId)
    {
      return  entityManager.find(StepsTable.class,stepId);
    }

    public void deleteSteps(String stepsId)
    {
        StepsTable stepsTable = entityManager.find(StepsTable.class,stepsId);
        if (stepsTable == null) throw new BaseException(-5000,"没有找到相关记录");
        entityManager.remove(stepsTable);
    }

    /**
     * 删除用户的全部历史记录
     * @param userId
     */
    public void clearAllSteps(String userId)
    {
        String sql = "delete   from  StepsTable as steps where steps.userId=:userId";
        Query query = entityManager.createQuery(sql).setParameter("userId",userId);
        query.executeUpdate();
    }

}
