package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.Error;
import cn.programingmonkey.Exception.*;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by cai on 2017/3/23.
 */
@Controller
public class BaseController {


    // 内部异常
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Error> serverError(InternalServerErrorException e){

        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.SERVICE_UNAVAILABLE);
    }

    // 用户错误
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Error> userException(UserException e){

        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Error> nullPointException(NullPointerException e){

        Error error = new Error( 500,""+e.getMessage());
        return new ResponseEntity<Error>(error,HttpStatus.SERVICE_UNAVAILABLE);
    }



    @ExceptionHandler(PostException.class)
    public ResponseEntity<Error> postException(PostException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Error> loginException(LoginException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResetPwdException.class)
    public ResponseEntity<Error> resetPwdException(ResetPwdException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FramingException.class)
    public ResponseEntity<Error> framingEception(LoginException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistException.class)
    public ResponseEntity<Error> registException(RegistException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Error> passwordException(PasswordException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Error> invalidException(InvalidException e){

        System.out.println(e.getCode()  +e.getMsg());
        Error error = new Error( e.getCode(),""+e.getMsg());
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }


}

