package cn.programingmonkey.Controller.user;

import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Bean.UserBean;
import cn.programingmonkey.Constant.UrlConstant;
import cn.programingmonkey.Controller.BaseController;
import cn.programingmonkey.Exception.type.INVALID_EXCEPTION_TYPE;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Service.SMSService;
import cn.programingmonkey.Service.UserService;
import cn.programingmonkey.Utils.EncryptUtil;
import cn.programingmonkey.Utils.Utils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.naming.InvalidNameException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static cn.programingmonkey.Constant.Constant.OK_CODE;
import static cn.programingmonkey.Constant.Constant.*;


/**
 * Created by cai on 30/01/2017.
 */
@Controller
public class UserController  extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户的详细信息
     * @param userId
     * @return
     */
    @RequestMapping(path = UrlConstant.USER_URL_GET_PROFILE,method = RequestMethod.GET)
    @ResponseBody
    public Success getUserProfile(@RequestHeader(REQUEST_HEADER_USERID) String userId) {

        Success success = new Success(OK_CODE,"信息获取成功");
        success.setModel(userService.findUserById(userId));
        return success;
    }



    /**
     * 修改用户的头像
     * @param userid
     * @param image
     * @return
     * @throws IOException
     */
    @RequestMapping(path = UrlConstant.USER_URL_GET_UPDATE_HEADIMAGE,method = RequestMethod.POST)
    @ResponseBody
    public Success  updateHeadImage(@RequestHeader(REQUEST_HEADER_USERID) final String userid,
                                    @RequestParam(REQUEST_PARAM_IMAGE)    final CommonsMultipartFile image)
                                                  throws IOException {

        String realName = image.getOriginalFilename();      //获取图片原来的名字
        int extpoint = realName.indexOf(".");
        String ext =  realName.substring(extpoint,realName.length());
        String imageName = userid +ext;
        ImageService.saveImage(image,imageName);
       return  new Success(200,"头像修改成功",userService.updateUserImage(imageName,userid));

    }

    // 检查用户名是否有效

    /**
     * 检查用户修改的用户名是否有效,
     * @param name
     * @return
     * @throws InvalidException
     */
    @RequestMapping(path = UrlConstant.USER_URL_CHECK_NAME,method = RequestMethod.GET)
    @ResponseBody
    public Success isEffectiveName(@PathVariable(PATH_PARAM_NAME) final String name) throws InvalidNameException {

       if (!Utils.isRightName(name))
           throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_NAME);
       else return new Success(OK_CODE,name);
    }


    /**
     * 修改姓名
     * @param request
     * @param name
     * @return
     * @throws Exception
     */
    // 修改姓名
    @RequestMapping(path = UrlConstant.USER_URL_GET_UPATE_NAME,method = RequestMethod.GET)
    @ResponseBody
    public Success updateName(HttpServletRequest  request,
                              HttpServletResponse response,
                              @RequestHeader(REQUEST_HEADER_USERID) final String userId,
                              @PathVariable(PATH_PARAM_NAME)           final String name) throws Exception {

        return new Success(OK_CODE,"用户名修改成功",userService.updateUserName(userId,name));
    }

    /**
     * 修改性别
     * @param userId
     * @param gender
     * @return
     */

    // 修改性别
    @RequestMapping(path = UrlConstant.USER_URL_UPDATE_GENDER,method = RequestMethod.GET)
    @ResponseBody
    public Success updateGender(@RequestHeader(REQUEST_HEADER_USERID) final  String userId
                               ,@PathVariable(REQUEST_PARAM_GENDER)   final  String gender) {

        return new Success(200,"修改成功",userService.updateGender(userId,gender));
    }




    /**
     * 修改地点
     * @param userid
     * @param location
     * @return
     */
    // 修改地点
    @RequestMapping(path = UrlConstant.USER_URL_UPDATE_LOCATION,method = RequestMethod.GET)
    @ResponseBody
    public Success updateLocation(@RequestHeader(REQUEST_HEADER_USERID) final String userid
                                 ,@PathVariable(REQUEST_PARAM_LOCATION) final String location) {

       return new Success(OK_CODE,"修改成功",userService.updateLocation(userid,location));
    }


    /**
     * 修改出生日期，只能选择给定的日期
     * @param request
     * @param birthdate
     * @return
     */
    // 修改出生日期
    @RequestMapping(path = UrlConstant.USER_URL_UPDATE_BIRTHDATE,method = RequestMethod.GET)
    @ResponseBody
    public Success updateBirthday(HttpServletRequest request,
                                  HttpServletResponse response,
                                   @PathVariable(REQUEST_PARAM_BIRTHDATE) final String birthdate,
                                   @RequestHeader(REQUEST_HEADER_USERID)  final String usereId) throws Exception {

        return new Success(OK_CODE,
                "修改成功",
                userService.updateUserbirthdate(usereId,
                Utils.parseStringToDate(birthdate,
                        "yyyy-MM-dd HH-mm-ss.SSS")));
    }


    /**
     *获取更换手机号码验证码
     * @param request
     * @param response
     * @param userId
     * @param mobile
     * @return
     * @throws ClientException
     */
    @RequestMapping(path = UrlConstant.USER_URL_MOBILE_UPDATE_VERIFYCODE)
    public Success getUpdateMobileVerifyCode(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestHeader(REQUEST_HEADER_USERID) final String userId,
                                             @PathVariable(REQUEST_PARAM_MOBILE)   final String mobile) throws ClientException {

        // 绑定的新手机和已经绑定的手机号一致
       if(userService.findUserById(userId)!=null && userService.findUserById(userId).getMobile().equals(mobile))
           throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_MOBILE_BIND);

        //无效的手机号
        if (!Utils.isRightMobile(mobile)) throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);

        //手机已经注册了
        if(userService.isMobileReigsted(mobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT);


        String verifyCode = EncryptUtil.getVerifyCode();                    //获取一个验证码
        request.getSession().setAttribute("resetpwd",verifyCode);        // 设置手机重置的验证码，将验证码写入到session中
        request.getSession().setAttribute("resetmobile",mobile);         // 设置请求密码重置的手机，将请求验证码的手机号一起写入到session中
        request.getSession().setAttribute("verifycodeInvalidDate",
                Utils.getTimeWithDuration(5*60*1000));                 // 设置验证码的失效时间
        SMSService.sendResetPwdVerifyCode(mobile,verifyCode);               // 发送验证码

        return new Success(200,"验证码已经发送至手机"+mobile+",请注意查收");
    }


    /**
     * 更换手机号码
     * @param mobile
     * @param verifycode
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @RequestMapping(path = UrlConstant.USER_URL_UPDATE_MOBILE,  method = RequestMethod.POST)
    public Success updateMobile(@RequestParam(REQUEST_PARAM_MOBILE)     final String mobile,
                                @RequestParam(REQUEST_PARAM_VERIFYCODE) final String verifycode,
                                @RequestHeader(REQUEST_HEADER_USERID)   final String userId,
                                HttpServletRequest                      request,
                                HttpServletResponse                     response) throws ParseException {
        HttpSession session = request.getSession();

        String sessionCode = (String)session.getAttribute("verifycode");
        Date date = Utils.parseStringToDate((String) session.getAttribute("verifycodeInvalidDate"),"yyyy-MM-dd HH:mm:ss.SSS");
        String requestMobile = (String)session.getAttribute("requestmobile");

        //手机号错误
        if(mobile.equals(requestMobile))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_COUNT_NOT_REQUEST_REGIST);
        //验证码失效
        if(date.before(new Date()))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_VERIFYCODE_EXPIRED);
        // 验证码错误
        if(!sessionCode.equals(verifycode))
            throw new InvalidException(INVALID_EXCEPTION_TYPE.INVALID_EXCEPTION_VERIFYCODE);

        UserBean userBean = userService.updateMobile(userId,mobile);
        return new Success(OK_CODE,"手机号修改成功",userBean);
    }

    // 获取我的发帖
    // 获取我的收藏
    // 获取我的脚步

    //
}
