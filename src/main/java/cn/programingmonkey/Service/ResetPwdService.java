package cn.programingmonkey.Service;

import cn.programingmonkey.Bean.ResetPwdBean;
import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Bean.VerifycodeBean;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Dao.VerifyCodeDao;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Exception.MobileException;
import cn.programingmonkey.Exception.PasswordException;
import cn.programingmonkey.Exception.ResetPwdException;
import cn.programingmonkey.Exception.type.*;
import cn.programingmonkey.Table.AuthorityChangLog;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Table.VerifyCodeTable;
import cn.programingmonkey.Utils.EncryptUtil;
import cn.programingmonkey.Utils.Utils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by cai on 2017/3/30.
 */
@Service
public class ResetPwdService {

    @Autowired
    private VerifyCodeDao verifyCodeDao;

    @Autowired
    private UserDao userDao;


    /**
     * 获取密码重置验证码
     * @param mobile
     * @return
     */
    public void getResetPwdVerifycode(@PathVariable("mobile") final String mobile) throws ClientException {

        // 检查手机号格式是否正确
        if (!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        // 检查手机号是否已经注册
        if( userDao.findUserByUserMobile(mobile) ==null)
            throw new ResetPwdException(RESET_PWD_EXCEPTION_TYPE.RESET_PWD_EXCEPTION_MOBILE_UNRIGHTED);

        // 是否频繁发送验证码
        VerifyCodeTable table = verifyCodeDao.findByMobile(mobile,VERIFYCODE_TYPE.VERIFYCODE_TYPE_RESETPWD.getCode());
        if (table != null && new Date().getTime() - table.getSendDate().getTime() <= 60 *1000)
            throw new ResetPwdException(RESET_PWD_EXCEPTION_TYPE.RESET_PWD_EXCEPTION_VERIFYCODE_SEND_TOO_OFTEN);

//      String verifycode = EncryptUtil.getVerifyCode();
        String verifycode = "123456";
        VerifyCodeTable verifyCodeTable = new VerifyCodeTable(mobile,
                                                              verifycode,
                                                              VERIFYCODE_TYPE.VERIFYCODE_TYPE_RESETPWD.getCode());
        verifyCodeDao.add(verifyCodeTable);
       // SMSService.sendResetPwdVerifyCode(mobile,verifycode);
    }

    /**
     * 验证验证码是否正确
     * @param verifycodeBean
     * @return
     */


    public Success checkVerifycode( VerifycodeBean verifycodeBean){

        VerifyCodeTable table = verifyCodeDao.findByMobile(verifycodeBean.getMobile(),verifycodeBean.getCode());

        // 没有向该手机号发送过验证码
        if (table == null)
            throw new ResetPwdException(RESET_PWD_EXCEPTION_TYPE.RESET_PWD_EXCEPTION_INVALID_VEIFYCODE);
        // 验证码失效
        if (table.getExpiredDate().before(new Date()))
            throw new ResetPwdException(RESET_PWD_EXCEPTION_TYPE.RESET_PWD_EXCEPTION_VERIFYCODE_EXPIRED);
        // 验证码错误
        if (!table.getVerifycode().equals(verifycodeBean.getVerifycode()))
            throw new ResetPwdException(RESET_PWD_EXCEPTION_TYPE.RESET_PWD_EXCEPTION_INVALID_VEIFYCODE);

//        String authorityCode = EncryptUtil.randomString();
        UserTable userTable = userDao.findUserByUserMobile(verifycodeBean.getMobile());
//
//        AuthorityChangLog authorityChangLog = new AuthorityChangLog();
//        authorityChangLog.setUserId(userTable.getUserId());
//        authorityChangLog.setAuthorityId(authorityCode);
//        authorityChangLog.setExecuterId(userTable.getUserId());
//        authorityChangLog.setDate(Utils.getCurrentTime());
//        authorityChangLog.setOther("用户更新密码");

        return new Success(1,"验证成功",userTable.getSalt());
    }


    /**
     * 重新设置密码
     * @param resetPwdBean
     */
    public void resetPwd(ResetPwdBean resetPwdBean){

        String newPwd = resetPwdBean.getNewPwd();
        String newPwds = resetPwdBean.getNewPwds();

        if (newPwd ==null)
            throw new PasswordException(403,"请输入密码");
        if (newPwds == null)
            throw new PasswordException(403,"请再次输入密码");


        // 密码长度不一致
        if (newPwd.length() < 6 || newPwds.length() < 6)
            throw new PasswordException(PASSWORD_EXCEPTION_TYPE.PASSWORD_EXCEPTION_INVALIDLENGTH);

        // 两次密码输入不一致
        if (!newPwd.equals(newPwds))
            throw new PasswordException(PASSWORD_EXCEPTION_TYPE.PASSWORD_EXCEPTION_DIFFERENT);
        UserTable table = userDao.findUserByUserMobile(resetPwdBean.getMobile());
        table.setSecurityToken(EncryptUtil.encrypt(table.getSalt(),newPwd));
        userDao.update(table);
    }
}
