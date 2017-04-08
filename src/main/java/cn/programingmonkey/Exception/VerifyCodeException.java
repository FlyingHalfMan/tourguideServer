package cn.programingmonkey.Exception;

/**
 * Created by cai on 30/01/2017.
 */


public class VerifyCodeException  extends  BaseException{



    public VerifyCodeException(int code, String msg){
        super(code,msg);
    }


}
