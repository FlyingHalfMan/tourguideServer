package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Bean.UploadImageBean;
import cn.programingmonkey.Exception.FramingException;
import cn.programingmonkey.Exception.type.FRAMING_EXCEPTION_TYPE;
import cn.programingmonkey.Service.FramingService;
import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Utils.EncryptUtil;
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
import java.util.List;

/**
 * Created by cai on 2017/3/31.
 */
@Controller
@RequestMapping("framing")
public class FramingController extends BaseController {

    @Autowired
    private FramingService framingService;

    /**
     *
     * @param
     * @return
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public Success uploadImage(@RequestParam("image")CommonsMultipartFile image,
                               @RequestParam("longitude") Double longitude,
                               @RequestParam("latitute") Double latitute,
                               @RequestParam(value = "location",required = false) final String location,
                               @RequestHeader(value = "userid",required = false) final String userId,
                               HttpServletRequest request,
                               HttpServletResponse response)  {

        try {
            // 重新命名图片
            String imageName ="";
            if (userId == null) {
                 imageName = "I" + EncryptUtil.getUUID() + Utils.getCurrentTimeStamp();
            }
            else {
                imageName = "I" + userId + Utils.getCurrentTimeStamp();
            }
            // 保存图片至本地和图片服务器
            File file = ImageService.saveImage(image, imageName);
            //计算图片的SIFT值大小
            // ImageService.getSIFT(file.getAbsolutePath());

            //framingService.addImageInfor(imageName,userId,sift,null,latitute,longitude,location);
        }catch (IOException e){
            throw new FramingException(FRAMING_EXCEPTION_TYPE.FRAMING_EXCEPTION_IMAGE_SAVE_FAIL);
        }


        return new Success(200,"图片上传成功");
    }
}
