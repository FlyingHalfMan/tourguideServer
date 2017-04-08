package cn.programingmonkey.Bean;

import java.util.List;

/**
 * Created by cai on 30/01/2017.
 */
public class Success {

    private int code;
    private String message;
    private List   models;
    private Object model;


    public Success(){};

    public Success(int code,String message,Object model,List models)
    {
        this.code    = code;
        this.message = message;
        this.model   = model;
        this.models  = models;
    }

    public Success(int code,String message,Object model)
    {
        this.code = code;
        this.message = message;
        this.model = model;
        this.models = null;

    }
    public Success(int code,String message,List models)
    {
        this.code = code;
        this.message = message;
        this.model = null;
        this.models = models;

    }

    public Success(int code,String message){

        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List getModels() {
        return models;
    }

    public void setModels(List models) {
        this.models = models;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
