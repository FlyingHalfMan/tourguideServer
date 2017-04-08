package cn.programingmonkey.Bean;

/**
 * Created by cai on 2017/2/17.
 */
public class BaseBean {

    protected  int resultCode;
    protected  String message;
    protected  String responseTime;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
