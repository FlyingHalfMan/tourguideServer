package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.*;
import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Exception.PasswordException;
import cn.programingmonkey.Exception.UserException;
import cn.programingmonkey.Service.LoginService;
import cn.programingmonkey.Constant.UrlConstant;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by cai on 30/01/2017.
 */
@Controller
public class LoginController  extends BaseController{

    @Autowired
    private LoginService loginService;


    @RequestMapping(path = UrlConstant.Login_URL_GET_LOGIN_VERIFYCODE,method = RequestMethod.GET)
    @ResponseBody

    /**
     *  获取手机验证码
     *  @Param mobile
     */
    public Success getloginVerifycode(@PathVariable(Constant.REQUEST_PARAM_MOBILE) final String mobile) throws ClientException {

        loginService.getloginVerifycode(mobile);
        return new Success(200,"验证码成功发送到"+mobile);
    }


    /**
     * 验证码登录
     * @param loginVerifyCodeBean
     * @return
     */
    @RequestMapping(path = UrlConstant.Login_URL_Verify_Verifycode,method = RequestMethod.POST)
    @ResponseBody
    public Success loginWithVerifyCode(@RequestBody LoginVerifyCodeBean loginVerifyCodeBean) {

       SecurityToken securityToken =  loginService.loginWithVerifyCode(loginVerifyCodeBean.getMobile(),
                                                                      loginVerifyCodeBean.getVerifycode());
        return new Success(200,"登录成功",securityToken);
    }


    /**
     * 账号密码登录
     * @param loginPasswordBean
     * @return
     * @throws UserException
     * @throws PasswordException
     */
    @RequestMapping(path = UrlConstant.Login_URL_Login_Password,method = RequestMethod.POST )
    @ResponseBody
    public Success loginWithPassword(@RequestBody LoginPasswordBean loginPasswordBean) {

        SecurityToken token = loginService.loginWithPassword(loginPasswordBean.getMobile(),
                                                             loginPasswordBean.getPassword());

        return  new Success(200,"信息获取成功",token);
    }

}
