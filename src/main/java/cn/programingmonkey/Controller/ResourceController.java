package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Table.ImageInfor;
import cn.programingmonkey.Utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by cai on 2017/2/20.
 */
@Controller
@RequestMapping(path = "upload",method = RequestMethod.POST)
public class ResourceController extends BaseController {



    @RequestMapping(path = "image",method = RequestMethod.POST)
    @ResponseBody
    public Success uploadImage(@RequestParam(value = "image",required = true) CommonsMultipartFile file,
                               @RequestParam("sift") final String sift,
                               @RequestParam("location") final String location,
                               @RequestParam("longitude") double longitude,
                               @RequestParam("latitute") double latitute,
                               @RequestHeader(Constant.REQUEST_HEADER_USERID) final String userId,
                               HttpServletRequest  request,
                               HttpServletResponse response) throws IOException {

        // 用户上传一张图片，先将图片保存
        String imageName = userId + Utils.getCurrentTimeStamp();
        File   image     = ImageService.saveImage(file,imageName);
        //ImageService.getSIFT(imageName);

        // 获取指纹编码
        String finger = ImageService.fingerPrint(image);
        ImageInfor imageInfor = new ImageInfor();
        System.out.println("指纹:"+ finger);
        // 获取图片的指纹和sift 后，再根据这两项去获取图片的具体分类，
        // ImageInfor imageInfor = new ImageInfor();

        // 将图片信息保存到数据库中

        // 从mongodb中获取数据，获取的数据包括百科链接，图片集合，视频()

        return null;
    }
}
