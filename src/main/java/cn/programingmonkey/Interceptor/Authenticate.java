package cn.programingmonkey.Interceptor;
import cn.programingmonkey.Bean.UserBean;
import cn.programingmonkey.Exception.UserException;
import cn.programingmonkey.Exception.type.USER_EXCEPTION_TYPE;
import cn.programingmonkey.Service.UserService;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by cai on 2017/2/16.
 */
public class Authenticate implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

       String userid =  httpServletRequest.getHeader("userid");
       String securityToken = httpServletRequest.getHeader("securitytoken");
       // 注意时间的格式,以时间戳的方式上传
       String date = httpServletRequest.getHeader("date");
       String path = httpServletRequest.getContextPath();

       // 不存在userId securityToken expiredDate
       if (userid ==null || securityToken == null ||date == null) {
           httpServletResponse.sendRedirect(path +"/exception/user");
           return false;
       }

       // 登录信息已经过期，要求用户重新登录
       Date expiredDate = Utils.parseStringToDate(date,"yyyyMMddHHmmss");
       if (expiredDate.before(new Date())) {
           httpServletResponse.sendRedirect(path +"/exception/expired");
           return false;
       }


       try {
            // 验证用户是否存在
            userService.findUserById(userid);
            // 验证密码是否正确
           if (!userService.isRightPassword(userid,securityToken))
               throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDPWD);
            // 验证登录是否已经失效
           if (expiredDate.before(new Date()))
               throw new UserException(USER_EXCEPTION_TYPE.USER_EXCEPTION_EXPIRED);
            return true;

       }catch (UserException e) {

           switch (e.getCode()){
               case -601:{
                   httpServletResponse.sendRedirect(path + "/exception/invalidid");
                   return false;
               }

               case -605:{
                   httpServletResponse.sendRedirect(path + "/exception/wrongpwd");
                   return false;
               }
               case -606:{
                   httpServletResponse.sendRedirect(path+"/exception/expired");
                   return false;
               }

               default: return false;
           }
       }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("inteceptor complete");

    }
}
