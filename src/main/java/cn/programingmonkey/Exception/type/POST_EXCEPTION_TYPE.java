package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 2017/2/16.
 */
public enum POST_EXCEPTION_TYPE {

    POST_EXCEPTION_TYPE_NONE(-1001,"查找的帖子不存在或者已经被删除"),
    POST_EXCEPTION_TYPE_USER_NONE(-1002,"没有找到该用户的发帖记录"),
    POST_EXCEPTION_TYPE_DATABASE_NONE(-1003,"没有找到发帖记录,请稍后再试"),
    POST_EXCEPTION_TYPE_INVALIDEPOST(-1004,"无效的发帖，帖子中至少要包含图片，视频，文字中的一种"),
    POST_EXCEPTION_TYPE_POST_NOTFOUND(-1006,"帖子不存在或者已经被删除"),
    POST_EXCEPTION_TYPE_UNAUTHORIZATION(-1005,"无权进行该操作"),
    POST_EXCEPTION_TYPE_IMAGE_VEDIO_BOTH(-1007,"不能同时包含视频和图片内容");


    private int code;
    private String msg;

    POST_EXCEPTION_TYPE(int code, String msg) {
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
