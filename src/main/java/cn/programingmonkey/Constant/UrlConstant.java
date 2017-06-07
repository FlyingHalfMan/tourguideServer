package cn.programingmonkey.Constant;

/**
 * 各项URL请求
 * Created by cai on 2017/4/2.
 */
public class UrlConstant {

    /**
     * ************************登录URL**************************
     */
    public static final String Login_URL_GET_LOGIN_VERIFYCODE       =   "login/verifycode/{mobile}";
    public static final String Login_URL_Verify_Verifycode          =   "login/verify";
    public static final String Login_URL_Login_Password             =   "login/password";

    /**
     * *************************注册URL*************************
     */

    public static final String Regist_URL_GET_REGIST_VERIFYCODE     =   "regist/verifycode/{mobile}";
    public static final String Regist_URL_REGIST                    =   "regist";

    /**
     * *************************密码重置URL**********************
     */
    public static final String RESETPWD_URL_GET_RESET_VERIFYCODE    =   "reset/verifycode/{mobile}";
    public static final String RESETPWD_URL_CHECK_VERIFYCODE        =   "reset/verifycode/check";
    public static final String RESETPWD_URL_RESET_PWD               =   "reset/password";

    /**
     * *************************用户信息URL***********************
     */

    public static final String USER_URL_GET_PROFILE                 =   "user/profile";
    public static final String USER_URL_GET_UPDATE_HEADIMAGE        =   "user/update/headImage";
    public static final String USER_URL_CHECK_NAME                  =   "user/check/name/{name}";
    public static final String USER_URL_GET_UPATE_NAME              =   "user/update/name/{name}";
    public static final String USER_URL_UPDATE_GENDER               =   "user/update/gender/{gender}";
    public static final String USER_URL_UPDATE_LOCATION             =   "user/update/location/{location}";
    public static final String USER_URL_UPDATE_BIRTHDATE            =   "user/update/birthday/{birthdate}";
    public static final String USER_URL_MOBILE_UPDATE_VERIFYCODE    =   "user/update/mobile/verifycode/{mobile}";
    public static final String USER_URL_UPDATE_MOBILE               =   "user/update/mobile";


    /**
     *  *************************帖子URL**************************
     */

    public static final String POST_URL_GET_POST                    =   "post";
    public static final String POST_URL_GET_POST_USER               =   "post/user/{userid}";
    public static final String POST_URL_GET_POST_DETAIL             =   "post/detail";
    public static final String POST_URL_GET_DELTET_POST             =   "post/delete/{postId}";
    public static final String POST_URL_LIKE_POST                   =   "post/like/{postId}";
    public static final String POST_URL_COLLECT_POST                =   "post/collection/{postId}";
    public static final String POST_URL_UPLOAD_IMAGE                =   "post/upload/image";
    public static final String POST_URL_UPLOAD_VEDIO                =   "post/upload/vedio";
    public static final String POST_URL_CREATE                      =   "post/create";
}
