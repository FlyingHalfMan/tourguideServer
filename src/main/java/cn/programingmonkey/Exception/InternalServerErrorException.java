package cn.programingmonkey.Exception;

/**
 * Created by cai on 30/01/2017.
 */
public class InternalServerErrorException extends BaseException {


    public InternalServerErrorException(int code,String message){

        super(code,message);
    }
}
