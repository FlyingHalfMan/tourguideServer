package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.Error;
import cn.programingmonkey.Exception.type.USER_EXCEPTION_TYPE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by cai on 2017/2/15.
 */
@Controller
@RequestMapping(path = "/exception")
public class ExceptionController extends BaseController {

   // private static  Logger logger = Logger.getLogger(ExceptionController.class);

    // 没有填写userid和securitytoken
    @RequestMapping(path = "/user",method = RequestMethod.GET)
    public ResponseEntity<Error> userException(){

        Error error = new Error( USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTLOGIN.getCode()
                                ,USER_EXCEPTION_TYPE.USER_EXCEPTION_NOTLOGIN.getMsg());
        return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
    }


    // 无效的用户id
    @RequestMapping(path = "/invalidid",method = RequestMethod.GET)
    public ResponseEntity<Error>invalidUser()
    {
        Error error = new Error( USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDUSER.getCode()
                                ,USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDUSER.getMsg());
        return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
    }

    // 用户密码错误
    @RequestMapping(path = "/wrongpwd",method = RequestMethod.GET)
    public ResponseEntity<Error>wrongPWD()
    {
        Error error = new Error( USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDPWD.getCode()
                ,USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALIDPWD.getMsg());
        return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
    }

    // 验证文件失效
    @RequestMapping(path = "expired",method = RequestMethod.GET)
    public ResponseEntity<Error> expired()
    {
        Error error = new Error( USER_EXCEPTION_TYPE.USER_EXCEPTION_EXPIRED.getCode()
                ,USER_EXCEPTION_TYPE.USER_EXCEPTION_EXPIRED.getMsg());
        return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(path = "invalidrole")
    public ResponseEntity<Error> invalidRole()
    {
        Error error = new Error( USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALID_ROLE.getCode()
                ,USER_EXCEPTION_TYPE.USER_EXCEPTION_INVALID_ROLE.getMsg());
        return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
    }

}
