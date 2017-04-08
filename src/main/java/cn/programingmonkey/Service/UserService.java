package cn.programingmonkey.Service;

import cn.programingmonkey.Bean.LoginPasswordBean;
import cn.programingmonkey.Bean.SecurityToken;
import cn.programingmonkey.Bean.UserBean;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Dao.VerifyCodeDao;
import cn.programingmonkey.Exception.type.INVALID_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.type.PASSWORD_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.type.USER_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.*;
import cn.programingmonkey.Exception.type.VERIFYCODE_TYPE;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.VerifyCodeTable;
import cn.programingmonkey.Utils.EncryptUtil;
import cn.programingmonkey.Utils.Utils;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cai on 29/01/2017.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    /**
     *  创建新用户
     * @param userTable
     */
    @Transactional
    public void createUser(UserTable userTable){

        userDao.add(userTable);
    }

    /**
     * 删除用户
     * @param userId
     * @throws UserException
     */
    public void deleteUser(Serializable userId) throws  UserException{

        UserTable userTable = userDao.findUserByUserId(userId);

        if (userTable == null)
            throw  new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        userDao.delete(userTable);
    }


    /**
     * 查看全部用户
     * @param offset
     * @param length
     * @return
     */
    public List<UserBean> listAllUsers(int offset,int length){

       List<UserTable> userTables =  userDao.listAllUser(offset,length);
       List<UserBean> userBeans = new ArrayList<UserBean>(10);

       // 数据格式转换
       for(UserTable userTable :userTables){
           UserBean userBean = new UserBean(userTable);
           userBeans.add(userBean);
       }
       return userBeans;
    }

    /**
     * 修改用户名称
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    public UserBean updateUserName(Serializable userId,String userName) throws UserException,InvalidException {

        UserTable userTable = userDao.findUserByUserId(userId);

        // 异常
        // 1.没有该用户
        if (userTable == null)
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        // 用户名无效
        if (!Utils.isRightName(userName))
            throw new InvalidException("无效的的用户名称");

        // 用户名被占用
        if ((userDao.findUserByUserName(userName).size() >= 1))
            throw  new InvalidException("用户名已经被使用");

        userTable.setUserName(userName);
        userDao.update(userTable);
        return new UserBean(userTable);
    }

    /**
     *
     * @param userImage
     * @param userId
     * @return
     */
    public UserBean updateUserImage(String userImage,String userId) {

        UserTable userTable = userDao.findUserByUserId(userId);
        userTable.setUserImage(userImage);
        userDao.update(userTable);
        return new UserBean(userTable);
    }

    /**
     *
     * @param userId
     * @param date
     * @return
     * @throws Exception
     */
    public UserBean updateUserbirthdate(Serializable userId,Date date) throws  Exception{

        UserTable userTable = userDao.findUserByUserId(userId);

        // 1.没有该用户
        if (userTable == null) throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        // 无效的时间
        if (date.before(Utils.parseStringToDate("1900-01-01 00:00:00.000",null))
                || date.after(new Date()))
           throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDE_DATE);

        userTable.setBirthDate(date);
        userDao.update(userTable);
        return new UserBean(userTable);
    }


    /**
     *
     * @param userid
     * @param gender
     * @return
     */
    // 修改性别
    public UserBean updateGender(Serializable userid,String gender) {

        UserTable userTable  = userDao.findUserByUserId(userid);

        // 用户不存在
        if (userTable == null) throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        userTable.setGender(gender);
        userDao.update(userTable);
        return new UserBean(userTable);
    }

    /**
     *
     * @param userId
     * @param email
     * @return
     */
    public UserBean updateUserEmail(Serializable userId,String email){

        UserTable userTable  = userDao.findUserByUserId(userId);

        // 用户不存在
        if (userTable == null) throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        if (!Utils.isRightEmail(email))
            throw new InvalidException("无效的邮箱号码");

        userTable.setEmail(email);
        userDao.update(userTable);
        return new UserBean(userTable);
    }

    public UserBean updateMobile(String userId,String mobile){

        // 判断手机号码是不是已经注册
        if(isMobileReigsted(mobile))
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_MOBILE_REGISTED);

        //判断用户是否存在
        UserTable userTable  =   userDao.findUserByUserId(userId);
        if (userTable == null)
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);
        userTable.setMobile(mobile);
        userDao.update(userTable);
        return new UserBean(userTable);
    }


    /**
     * 修改地址
     * @param userId
     * @param location
     * @return
     */

    // 修改地点
    public UserBean updateLocation(Serializable userId, String location) {

        UserTable userTable = userDao.findUserByUserId(userId);

        if (userTable ==null) throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        userTable.setNativePlace(location);
        userDao.update(userTable);
        return new UserBean(userTable);
    }


    /**
     * 重新设置密码
     * @param pwd
     * @param mobile
     * @return
     */
    public SecurityToken resetPwd(String pwd, String mobile){

        UserTable userTable = userDao.findUserByUserMobile(mobile);
        if (userTable == null)
            throw  new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);
        userTable.setSecurityToken(EncryptUtil.encrypt(userTable.getSalt(),pwd));
        userDao.update(userTable);

        return new SecurityToken(userTable);
    }

    /**
     * 手机号码是否已经注册
     * @param mobile
     * @return
     */
    public boolean isMobileReigsted(String mobile){

        UserTable userTable = userDao.findUserByUserMobile(mobile);
        return userTable == null ? false : true;
    }


    /**
     * 根据手机号码查找用户
     * @param mobile
     * @return
     */
    public UserBean findUserByUserMobile(String mobile){

       return  new UserBean(userDao.findUserByUserMobile(mobile));
    }


    /**
     * 根据用户的Id 去查找数据
     * @param id
     * @return
     */
    public UserBean findUserById(String id) {

        UserTable userTable = userDao.findUserByUserId(id);
        if (userTable == null){
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);
        }
        return new UserBean(userTable);
    }

    /**
     *
     */
    public boolean isRightPassword(String userId,String password){

        UserTable userTable = userDao.findUserByUserId(userId);
        if (userTable == null)
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);
        if (!userTable.getSecurityToken().equals(password))
            return false;
        else return true;
    }

}