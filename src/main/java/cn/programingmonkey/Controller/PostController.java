package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.PostHomeBean;
import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Bean.UploadImageBean;
import cn.programingmonkey.Constant.UrlConstant;
import cn.programingmonkey.Dao.ImageInforDao;
import cn.programingmonkey.Exception.PostException;
import cn.programingmonkey.Service.FramingService;
import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Service.PostService;
import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Utils.Sift.MyPoint;
import cn.programingmonkey.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import cn.programingmonkey.Constant.Constant;

/**
 * Created by cai on 2017/2/17.
 */

@Controller
public class PostController  extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private FramingService framingService;


    /**
     * 根据距离来获取用户周边的信息
     * @param latitute
     * @param longitude
     * @param offset
     * @param limit
     * @return
     */

    @RequestMapping(path = UrlConstant.POST_URL_GET_POST_NEARBY,method = RequestMethod.GET)
    @ResponseBody
    public Success getPostByLocation(@RequestParam(Constant.REQUEST_PARAM_LATITUTE)   final double latitute,
                                     @RequestParam(Constant.REQUEST_PARAM_LOGITUDE)   final double longitude,
                                     @RequestParam(Constant.REQUEST_PARAM_OFFSET)     final int    offset,
                                     @RequestParam(Constant.REQUEST_PARAM_LIMIT)      final int    limit) {

       List<PostHomeBean>posts =  postService.findPostByLocation(latitute,longitude,5,offset,limit);
        return new Success(200,"数据请求成功",posts);
    }


    /**
     *根据时间来进行排序
     * @param offset
     * @param limit
     * @return
     */

    /* 根据时间来获取动态*/
    @RequestMapping(path = UrlConstant.POST_URL_GET_POST_DATE,method = RequestMethod.GET)
    @ResponseBody
    public Success getPostByDate(@RequestParam(Constant.REQUEST_PARAM_OFFSET) final int offset,
                                 @RequestParam(Constant.REQUEST_PARAM_LIMIT)  final int limit,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) {

        List<PostHomeBean> posts =   postService.findPostByDate(offset,limit);
        return new Success(200,"数据请求成功",posts);
    }

    /**
     * 根据热度还进行排序
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping(path = UrlConstant.POST_URL_GET_POSTZ_HOT,method = RequestMethod.GET)
    @ResponseBody
    public Success getPostByHot(@RequestParam(Constant.REQUEST_PARAM_OFFSET) final int offset,
                                @RequestParam(Constant.REQUEST_PARAM_LIMIT)  final int limit,
                                HttpServletRequest  request,
                                HttpServletResponse response) {

        List<PostHomeBean> posts =   postService.findPostByHot(offset,limit);
        return new Success(200,"数据请求成功",posts);
    }



    /**
     * 获取帖子的详情
     * @param postId
     * @return
     */
    @RequestMapping(path =UrlConstant.POST_URL_GET_POST_DETAIL,method = RequestMethod.GET)
    @ResponseBody
    public Success getPostByPostId(@PathVariable(Constant.PATH_PARAM_POSTID) final String postId,
                                   HttpServletRequest   request,
                                   HttpServletResponse  response) throws PostException {

        PostHomeBean post =  postService.findPostByPostId(postId);
        return new Success(200,"帖子获取成功",post);
    }


    /**
     * 根据userid来获取用户创建的全部帖子，同样需要进行授权验证
     * @param userid
     * @param requstuserid
     * @param offset
     * @param limit
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(path = UrlConstant.POST_URL_GET_POST_USER,method = RequestMethod.GET)
    @ResponseBody
    public Success getPostByUserId(@PathVariable(Constant.PATH_PARAM_USERID)        final String requstuserid,
                                   @RequestHeader(Constant.REQUEST_HEADER_USERID)   final String userid,
                                   @RequestParam(Constant.REQUEST_PARAM_OFFSET)     final int    offset,
                                   @RequestParam(Constant.REQUEST_PARAM_LIMIT)      final int    limit,
                                   HttpServletRequest                   request,
                                   HttpServletResponse                  response) throws PostException {

        String id = userid;
        if (requstuserid == null|| requstuserid.length() <1) id =userid;
        else if(userid == null || userid.length() < 1) id = requstuserid;

        List<PostHomeBean> postList = postService.findPostByUserId(id,offset,limit);
        return new Success(200,"数据获取成功",postList);
    }



    /**
     * 删除帖子。拦截器会进行授权验证码
     * @param postId
     * @return
     */
    @RequestMapping(path = UrlConstant.POST_URL_GET_DELTET_POST,method = RequestMethod.GET)
    @ResponseBody
    public Success deletePost(@PathVariable(Constant.PATH_PARAM_POSTID) final String postId,
                              @RequestHeader(Constant.PATH_PARAM_USERID)final String userId) throws PostException {


        postService.deletePost(postId,userId);
        return new Success(1,"删除成功");
    }

    /**
     * 点赞
     * @param postId
     * @return
     */
    @RequestMapping(path = UrlConstant.POST_URL_LIKE_POST)
    @ResponseBody
    public Success likePost(@PathVariable("postId") final String postId){

        postService.updateLikes(postId);
        return new Success(200,"点赞成功");
    }

    /**
     * 收藏帖子
     * @param postId
     * @return
     */
    @RequestMapping(path = UrlConstant.POST_URL_COLLECT_POST)
    @ResponseBody
    public Success collectPost(@PathVariable(Constant.PATH_PARAM_POSTID) final String postId){

        postService.updateCollection(postId);
        return new Success(200,"收藏成功");
    }


    /**
     * 上传图片
     * @param uploadImageBean
     * @param userId
     * @return
     * @throws IOException
     */
    @RequestMapping(path = UrlConstant.POST_URL_UPLOAD_IMAGE,method = RequestMethod.POST)
    @ResponseBody
    public Success uploadImage(@RequestBody UploadImageBean uploadImageBean,
                               @RequestHeader(Constant.REQUEST_HEADER_USERID) final String userId) throws IOException {

        //将图片重新命名，并上传到图片服务器
        String imageName = userId + Utils.getCurrentTimeStamp();
        File file = ImageService.saveImage(uploadImageBean.getImage(),imageName);

        // 计算图片的SIFT值
        List<MyPoint> sift = ImageService.getSIFTCharacteristicValue(file);

        framingService.addImageInfor(imageName,
                                        userId,
                                          sift,
                                   null,
                 uploadImageBean.getLatitude(),
                uploadImageBean.getLongitute(),
                uploadImageBean.getLocation());

        return new Success(200,"图片上传成功",imageName);
    }


    /**
     * 上传视频
     * @param vedio
     * @param userId
     * @return
     */
    @RequestMapping(path = UrlConstant.POST_URL_UPLOAD_VEDIO,method = RequestMethod.POST)
    @ResponseBody
    public Success uploadVideo(@RequestParam("video")  final CommonsMultipartFile vedio,
                               @RequestParam("userid") final String               userId){

        return new Success(200,"视频上传成功");
    }

    /**
     * 创建一个新的post
     * @param post
     * @param userId
     */
    @RequestMapping(path = UrlConstant.POST_URL_CREATE,method = RequestMethod.POST)
    @ResponseBody
    public Success createNewPost(@RequestBody               final Post post,
                                 @RequestHeader("userid")   String userId,
                                 HttpServletRequest         request,
                                 HttpServletResponse        response) {
        post.setUserid(userId);
        post.setViews(1L);
        post.setCollection(1L);
        post.setLikes(1L);
        post.setHot(post.getViews() * 0.2 + post.getLikes() * 0.3 + post.getCollection() * 0.5);
        post.setDate(new Date());
        postService.addPost(post);
        return new Success(200,"发布成功");
    }
}
