package cn.programingmonkey.Service;

import cn.programingmonkey.Bean.SecurityToken;
import cn.programingmonkey.Bean.UserBean;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Dao.VerifyCodeDao;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Exception.LoginException;
import cn.programingmonkey.Exception.PasswordException;
import cn.programingmonkey.Exception.UserException;
import cn.programingmonkey.Exception.type.*;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.VerifyCodeTable;
import cn.programingmonkey.Utils.EncryptUtil;
import cn.programingmonkey.Utils.Utils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by cai on 2017/3/30.
 */
@Service
public class LoginService {

    @Autowired
    private VerifyCodeDao verifyCodeDao;

    @Autowired
    private UserDao userDao;

    /**
     * 根据手机验证码来进行登录
     * @param mobile
     * @param verifycode
     * @return SecurityToken
     * @throws  LoginException,InvalidException
     */

    public SecurityToken loginWithVerifyCode(String mobile,String verifycode) throws InvalidException,LoginException{

        // 判断手机号码是否正确
        if (!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);
        //判断手机号是否已经注册
        if (userDao.findUserByUserMobile(mobile) == null)
            throw new LoginException(LOGIN_EXCEPTION_TYPE.LOGIN_EXCEPTION_MOBILE_UNRIGHTED);
        // 判断验证码是否正确(包含格式正确)

        VerifyCodeTable verifyCodeTable = verifyCodeDao.findByMobile(mobile, VERIFYCODE_TYPE.VERIFYCODE_TYPE_LOGIN.getCode());
        //没有向该手机号发送过验证啊吗
        if (verifyCodeTable == null)
            throw new LoginException(LOGIN_EXCEPTION_TYPE.LOGIN_EXCEPTION_VERIFYCODE_NOT_SEND);
        // 验证码错误
        if (!verifyCodeTable.getVerifycode().equals(verifycode))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_VERIFYCODE);
        if (verifyCodeTable.getExpiredDate().before(new Date()))
            throw new LoginException(LOGIN_EXCEPTION_TYPE.LOGIN_EXCEPTION_VERIFYCODE_EXPIRED);

        return new SecurityToken(userDao.findUserByUserMobile(mobile));
    }

    /**
     *
     * @param mobile
     */
    public void getloginVerifycode(String mobile) throws InvalidException, LoginException, ClientException {

        // 判断手机号格式是否正确
        if (!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        // 判断手机号是否已经注册
        if (userDao.findUserByUserMobile(mobile) == null)
            throw new LoginException(LOGIN_EXCEPTION_TYPE.LOGIN_EXCEPTION_MOBILE_UNRIGHTED);


        VerifyCodeTable table =  verifyCodeDao.findByMobile(mobile,VERIFYCODE_TYPE.VERIFYCODE_TYPE_LOGIN.getCode());
        // 判断验证码是否已经发送（1分钟，避免联系发送）
        if (table !=null && new Date().getTime() - table.getSendDate().getTime() <= 60 * 1000)
            throw new LoginException(LOGIN_EXCEPTION_TYPE.LOGIN_EXCEPTION_VERIFYCDOE_SEND_OFFTEN);

        // 获取随机验证码
        String verifycode = EncryptUtil.getVerifyCode();
        // 短信服务发送验证码
        SMSService.sendLoginVerifyCode(mobile,verifycode);
        // 将验证码写入到数据库中
        if(table == null) {
            VerifyCodeTable verifyCodeTable = new VerifyCodeTable(mobile, verifycode, VERIFYCODE_TYPE.VERIFYCODE_TYPE_LOGIN.getCode());
            verifyCodeDao.add(verifyCodeTable);
        }
        else{
            table.setExpiredDate(Utils.getDateWithDuration(5 * 60 * 1000));
            table.setVerifycode(verifycode);
            table.setSendDate(new Date());
            table.setMobile(mobile);
            verifyCodeDao.update(table);
        }
    }

    /**
     *
     * @param mobile
     * @param password
     * @return
     * @throws LoginException,InvalidException
     */
    public SecurityToken loginWithPassword(String mobile,String password) throws PasswordException,InvalidException,LoginException{

        // 判断手机格式是否正确
        if (!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        UserTable userTable = userDao.findUserByUserMobile(mobile);
        if (userTable == null)
            throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTFOUND);

        if (!userTable.getSecurityToken().equals(EncryptUtil.encrypt(userTable.getSalt(),password)))
            throw new PasswordException(PASSWORD_EXCEPTION_TYPE.PASSWORD_EXCEPTION_WRONG);

        return new SecurityToken(userTable);
    }
}
