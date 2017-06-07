package cn.programingmonkey.Exception.type;

/**
 * Created by cai on 2017/3/31.
 */
public enum  FRAMING_EXCEPTION_TYPE {
    FRAMING_EXCEPTION_IMAGE_UPLOAD_FAIL(-1101,"图片上传失败"),
    FRAMING_EXCEPTION_IMAGE_SAVE_FAIL(-1102,"图片保存失败"),
    FRAMING_EXCEPTION_IMAGE_UNRECNIZE(-1103,"图片无法识别"),
    FRAMING_EXCEPTION_CLASSIFY_NOT_FOUND(-1104,"没有找到图像分类");



    private int code;
    private String msg;

    FRAMING_EXCEPTION_TYPE(int code, String msg) {
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
