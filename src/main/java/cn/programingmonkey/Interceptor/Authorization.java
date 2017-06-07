package cn.programingmonkey.Interceptor;

import cn.programingmonkey.Dao.RoleDao;
import cn.programingmonkey.Dao.UserDao;
import cn.programingmonkey.Table.UserTable;
import cn.programingmonkey.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by cai on 2017/3/21.
 */
class Authorization implements HandlerInterceptor {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 获取请求的uri
       String requesetUri = httpServletRequest.getRequestURI();
       // 获取用户名
       String userId = httpServletRequest.getHeader("userid");
       String token  = httpServletRequest.getHeader("securitytoken");
       String expiredDate = httpServletRequest.getHeader("date");
       Date date = Utils.parseStringToDate(expiredDate,"yyyyMMddHHmmssSSS");


       //用户还没有登录
       if(userId == null || userId.length() <1 || token ==null || token.length() < 1)
           httpServletResponse.sendRedirect("/Exception/user");


       // 用户id错误
        UserTable userTable = userDao.findUserByUserId(userId);
        if (userTable == null)
            httpServletResponse.sendRedirect("/Exception/invalidid");

        // 获得访问该路径的权限
        else {
            int requestRole =  roleDao.findRoleByPath(requesetUri);
            if(userTable.getRole()  < requestRole)
                httpServletResponse.sendRedirect("/Exception/invalidrole");

            else  return true;
        }

        // 权限不足

        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
