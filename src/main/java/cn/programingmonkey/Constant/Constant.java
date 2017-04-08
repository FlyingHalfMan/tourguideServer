package cn.programingmonkey.Constant;

/**
 * Created by cai on 2017/3/20.
 */

/**
 * 这里定义一些常用的常量
 */
public class Constant {

    public static final String  OSS_INTERNAL_ENDPOINT       = "oss-cn-shanghai-internal.aliyuncs.com";
    public static final String  OSS_ENDPOINT                = "oss-cn-shanghai.aliyuncs.com";
    public static final String  OSS_ACCESSKEYID             = "YUWa6JyS0EwLZfP1";
    public static final String  OSS_SECRETKEYID             = "V6T1YtMJDFoJzB6OrtCYHUJkU558CG";
    public static final String  OSS_BUCKET_NAME             = "fudatour";
    public static final String  OSS_CONTENTTYPE_JPEG        = "image/jpeg";
    public static final String  OSS_CONTENTTYPE_PNG         = "image/png";



    public static final int  OK_CODE           = 200;
    public static final int  SERVER_ERROR_CODE = 500;

    public static final String DATE_FORATE     = "yyyy-MM-dd HH:mm:ss.SSS";


    /**
     *  request param path  中的一些请求参数
     */

    public final static String PATH_PARAM_NAME              =   "name";
    public final static String PATH_PARAM_MOBILE            =   "mobile";
    public final static String PATH_PARAM_USERID            =   "userId";
    public final static String PATH_PARAM_POSTID            =   "postId";


    /**
     * 请求中头部的参数
     */
    public final static String REQUEST_HEADER_USERID        =   "userid";
    public final static String REQUEST_HEADER_SECURITYTOKEN =   "securitytoken";
    public final static String REQUEST_HEADER_EXPIREDDATE   =   "date";


    /**
     * 请求参数
     */
    public final static String REQUEST_PARAM_IMAGE          =   "image";
    public final static String REQUEST_PARAM_GENDER         =   "gender";
    public final static String REQUEST_PARAM_LOCATION       =   "location";
    public final static String REQUEST_PARAM_BIRTHDATE      =   "birthdate";
    public final static String REQUEST_PARAM_MOBILE         =   "mobile";
    public final static String REQUEST_PARAM_VERIFYCODE     =   "verifycode";
    public final static String REQUEST_PARAM_LOGITUDE       =   "longitude";
    public final static String REQUEST_PARAM_LATITUTE       =   "latitute";
    public final static String REQUEST_PARAM_OFFSET         =   "offset";
    public final static String REQUEST_PARAM_LIMIT          =   "limit";
}
