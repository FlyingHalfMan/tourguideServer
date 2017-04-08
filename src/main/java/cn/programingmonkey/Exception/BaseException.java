package cn.programingmonkey.Exception;

/**
 * Created by cai on 31/01/2017.
 */
public class BaseException extends RuntimeException {

    protected int code;
    protected String msg;

    public BaseException(){};

    public BaseException(Enum type){

    }

    public BaseException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
