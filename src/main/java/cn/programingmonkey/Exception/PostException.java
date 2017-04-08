package cn.programingmonkey.Exception;

import cn.programingmonkey.Exception.type.POST_EXCEPTION_TYPE;

/**
 * Created by cai on 2017/2/16.
 */
public class PostException extends BaseException {

    public PostException(POST_EXCEPTION_TYPE post_exception_type)
    {
        super(post_exception_type.getCode(),post_exception_type.getMsg());
    }
}
