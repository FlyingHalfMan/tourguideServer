package cn.programingmonkey.Service;

import cn.programingmonkey.Bean.SecurityToken;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Dao.VerifyCodeDao;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Exception.RegistException;
import cn.programingmonkey.Exception.type.INVALID_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.type.REGIST_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.type.VERIFYCODE_TYPE;
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
public class RegistService {

    @Autowired
    private VerifyCodeDao verifyCodeDao;

    @Autowired
    private UserDao userDao;

    public void getRegistVerifyCode(String mobile) throws InvalidException,RegistException,ClientException {

        // 无效的手机号码
        if(!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        // 手机号已经注册
        if (userDao.findUserByUserMobile(mobile) != null)
            throw  new RegistException(REGIST_EXCEPTION_TYPE.REGIST_EXCEPTION_MOBILE_REGISTED);

        VerifyCodeTable verifyCodeTable =verifyCodeDao.findByMobile(mobile,VERIFYCODE_TYPE.VERIFYCODE_TYPE_RESETPWD.getCode());

        if (verifyCodeTable !=null && new Date().getTime() - verifyCodeTable.getSendDate().getTime() <= 60 * 1000)
            throw new RegistException(REGIST_EXCEPTION_TYPE.REGIST_EXCEPTION_VERIFYCODE_EXPIRED);


        // 发送验证码
        String verifyCode = EncryptUtil.getVerifyCode();
        SMSService.sendRegistVerifyCode(mobile,verifyCode);

        // 如果之前请求过验证码那么本次执行的就是更新
        if (verifyCodeTable ==null){
            verifyCodeTable = new VerifyCodeTable(mobile,verifyCode,VERIFYCODE_TYPE.VERIFYCODE_TYPE_RESETPWD.getCode());
            verifyCodeDao.add(verifyCodeTable);
        }
        else{
            verifyCodeTable.setMobile(mobile);
            verifyCodeTable.setVerifycode(verifyCode);
            verifyCodeTable.setSendDate(new Date());
            verifyCodeTable.setExpiredDate(Utils.getDateWithDuration(5 * 60 * 1000));
            verifyCodeDao.update(verifyCodeTable);
        }
    }

    /**
     * 注册
     * @param mobile
     * @param verifycode
     */
    public SecurityToken registWithVerifyCode(String mobile, String verifycode){

        // 无效的手机号码
        if(!Utils.isRightMobile(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        // 手机号已经注册
        if (userDao.findUserByUserMobile(mobile) != null)
            throw  new RegistException(REGIST_EXCEPTION_TYPE.REGIST_EXCEPTION_VERIFYCODE_NOT_SEND);

        VerifyCodeTable verifyCodeTable = verifyCodeDao.findByMobile(mobile,VERIFYCODE_TYPE.VERIFYCODE_TYPE_RESETPWD.getCode());
        //没有向手机号码发送过验证码

        if (verifyCodeTable == null)
            throw new RegistException(REGIST_EXCEPTION_TYPE.REGIST_EXCEPTION_VERIFYCODE_NOT_SEND);

        // 验证码失效
        if (verifyCodeTable !=null && verifyCodeTable.getExpiredDate().before(new Date()))
            throw new RegistException(REGIST_EXCEPTION_TYPE.REGIST_EXCEPTION_VERIFYCODE_EXPIRED);

        String userId = EncryptUtil.userId();
        UserTable userTable =new UserTable.Build(userId,mobile)
                                          .name("用户"+userId)
                                          .salt(EncryptUtil.salt())
                                          .gender("保密")
                                          .build();
        userDao.add(userTable);
        return new SecurityToken(userTable);
    }

}
