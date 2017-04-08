package cn.programingmonkey.Dao;

import cn.programingmonkey.Table.UserTable;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cai on 29/01/2017.
 */
@Repository
public class UserDao extends BaseDao<UserTable> {


    /**
     * 查看全部的用户
     * @param offset
     * @param length
     * @return
     */
    // 获取全部用户
    public List<UserTable> listAllUser(int offset,int length){

        String sql = "select u from  UserTable as u";
        Query query = entityManager.createQuery(sql)
                                   .setFirstResult(offset)
                                   .setMaxResults(offset + length);
        return query.getResultList();
    }

    /**
     * 获取用户的详细资料
     * @param userId
     * @return
     */
    // 根据userId来查找
    public UserTable findUserByUserId(Serializable userId){

        String sql = "select u from UserTable as u where u.userId =:userId";
        Query query = entityManager.createQuery(sql)
                                   .setParameter("userId",userId);

        List<UserTable> userTables = query.getResultList();
        if (userTables == null || userTables.size() < 1)
            return null;
        else return userTables.get(0);
    }

    /**
     * 检查用户的手机号是不是已经注册
     * @param mobile
     * @return
     */
    // 根据手机号来查找
    public UserTable findUserByUserMobile(Serializable mobile){

        String sql = "select u from UserTable as u where u.mobile =:mobile";
        Query query = entityManager.createQuery(sql)
                .setParameter("mobile",mobile);

        List<UserTable> userTables = query.getResultList();
        if (userTables == null || userTables.size() < 1)
            return null;
        else return userTables.get(0);
    }

    // 根据用户名来查找,判断用户名是否重复

    /**
     * 检查用户名是否重复
     * @param userName
     * @return
     */
    public List<UserTable> findUserByUserName(String userName){

        String sql = "select u from UserTable as u where u.userName = :userName";
        Query query = entityManager.createQuery(sql).setParameter("userName",userName);

        return query.getResultList();
    }


}
