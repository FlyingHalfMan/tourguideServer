package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.ResetPwdBean;
import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Bean.VerifycodeBean;
import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Service.ResetPwdService;
import cn.programingmonkey.Constant.UrlConstant;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cai on 2017/3/30.
 */
@Controller
public class ResetPwdController extends BaseController {

    @Autowired
    private ResetPwdService resetPwdService;


    /**
     *
     * @param mobile
     * @return
     * @throws ClientException
     */
    @RequestMapping(path = UrlConstant.RESETPWD_URL_GET_RESET_VERIFYCODE,method = RequestMethod.GET)
    @ResponseBody
    public Success getResetPwdVerifycode(@PathVariable(Constant.REQUEST_PARAM_MOBILE) final String mobile) throws ClientException {

        resetPwdService.getResetPwdVerifycode(mobile);
        return new Success(200,"验证码已经发送至手机"+mobile);
    }


    /**
     *
     * @param verifycodeBean
     * @return
     */
    @RequestMapping(path = UrlConstant.RESETPWD_URL_CHECK_VERIFYCODE,method = RequestMethod.POST)
    @ResponseBody
    public Success verify(@RequestBody VerifycodeBean verifycodeBean){

        return resetPwdService.checkVerifycode(verifycodeBean);
    }


    @RequestMapping(path = UrlConstant.RESETPWD_URL_RESET_PWD,method = RequestMethod.POST)
    @ResponseBody
    public Success resetPwd(@RequestBody ResetPwdBean resetPwdBean){
        resetPwdService.resetPwd(resetPwdBean);
        return new Success(200,"密码重置成功");
    }

}
