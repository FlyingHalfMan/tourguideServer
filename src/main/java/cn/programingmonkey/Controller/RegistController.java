package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.RegistBean;
import cn.programingmonkey.Bean.SecurityToken;
import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Exception.MobileException;
import cn.programingmonkey.Service.RegistService;
import cn.programingmonkey.Constant.UrlConstant;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by cai on 29/01/2017.
 */
@Controller

public class RegistController extends BaseController {

    @Autowired
    private RegistService registService;


    /**
     *
     * @param mobile
     * @return
     * @throws ClientException
     * @throws MobileException
     */
    @RequestMapping(path = UrlConstant.Regist_URL_GET_REGIST_VERIFYCODE,method = RequestMethod.GET )
    @ResponseBody
    public Success getVerifyCode(@PathVariable(Constant.REQUEST_PARAM_MOBILE) final String mobile) throws ClientException,MobileException {

        registService.getRegistVerifyCode(mobile);
        return new Success(HttpStatus.ACCEPTED.value(),"验证码已经成功发送至手机"+mobile);
    }

    /**
     *
     * @param registBean
     * @return
     */
    @RequestMapping(path = UrlConstant.Regist_URL_REGIST,method = RequestMethod.POST)
    @ResponseBody
    public Success regist(@RequestBody RegistBean registBean){

       SecurityToken token = registService.registWithVerifyCode(registBean.getMobile(),registBean.getVerifycode());
       return new Success(200,"注册成功",token);
    }
}
