package cn.programingmonkey.Service;

import cn.programingmonkey.Dao.VerifyCodeDao;
import cn.programingmonkey.Exception.VerifyCodeException;
import cn.programingmonkey.Exception.type.VERIFYCODE_EXCEPTION_TYPE;
import cn.programingmonkey.Table.VerifyCodeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cai on 30/01/2017.
 */
@Service
public class VerifyCodeService {

    @Autowired
    VerifyCodeDao verifyCodeDao;

    public void add(String mobile, String content,int code) throws VerifyCodeException{

        // 根据手机号查询是否发送过验证码

      VerifyCodeTable table = verifyCodeDao.findByMobile(mobile,code);

      // 发送过一次验证码
      if(table !=null) {
          // 检查验证码是否发送过于频繁，如果是抛出异常
          long currentTime = new Date().getTime();
          if (currentTime - table.getSendDate().getTime() < 60 * 1000)
              throw new VerifyCodeException(VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_OFTEN);


          table.setVerifycode(content);
          table.setSendDate(new Date());
          table.setExpiredDate(new Date(table.getSendDate().getTime() + 5 * 60 * 1000));
          verifyCodeDao.update(table);

      }else {
          // 保存验证码信息
          table = new VerifyCodeTable();
          table.setMobile(mobile);
          table.setVerifycode(content);
          table.setCode(code);
          table.setSendDate(new Date());
          table.setExpiredDate(new Date(table.getSendDate().getTime() + 5 * 60 * 1000));
          verifyCodeDao.add(table);
      }
    }


    public void find(Serializable id){

    }

    // 根据手机号和验证码用途码来获取全部验证码
    public void listAllByMobile(){

    }

    public boolean isVerifyCodeRight(String mobile,String verifyCode,int code)throws VerifyCodeException{

        VerifyCodeTable verifyCodeTable = verifyCodeDao.findByMobile(mobile,code);

        // 没有找到
        if (verifyCodeTable == null)
            throw new VerifyCodeException(VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_NOTFOUND.getCode()
                                         ,VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_NOTFOUND.getMsg());
        // 验证码错误
        if (!verifyCodeTable.getVerifycode().equals(verifyCode))
            throw new VerifyCodeException(VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_WRONG.getCode()
                                         ,VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_WRONG.getMsg());
        // 验证码失效
        if ( verifyCodeTable.getExpiredDate().getTime()-new Date().getTime() > 5 *60 *1000 )
            throw new VerifyCodeException(VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_USERLESS.getCode()
                                         ,VERIFYCODE_EXCEPTION_TYPE.VERIFYCODE_EXCEPTION_USERLESS.getMsg());
        return true;
    }


}
