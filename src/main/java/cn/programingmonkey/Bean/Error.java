package cn.programingmonkey.Bean;

/**
 * Created by cai on 30/01/2017.
 */
public class Error {

    private int code;
    private String message;

    public Error (int statusCode,String errorMessage){

        this.code = statusCode;
        this.message = errorMessage;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
