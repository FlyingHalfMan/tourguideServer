package cn.programingmonkey.Controller;

import cn.programingmonkey.Bean.Success;
import cn.programingmonkey.Dao.SceneryDao;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Service.FramingService;
import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Service.SIFT.Point;
import cn.programingmonkey.Service.SIFTService;
import cn.programingmonkey.Table.Scenery;
import cn.programingmonkey.Utils.EncryptUtil;

import cn.programingmonkey.Utils.JsonUtil;
import cn.programingmonkey.Utils.Utils;

import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.*;


/**
 * Created by cai on 2017/3/31.
 */
@Controller
@RequestMapping("framing")

public class FramingController extends BaseController {

    @Autowired
    private FramingService framingService;


    @Autowired
    private SceneryDao sceneryDao;

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Success uploadImage(@RequestParam("image") CommonsMultipartFile image,
                               @RequestHeader(value = "userid", required = false) final String userId,
                               @RequestParam("sift") final String sift,
                               @RequestParam("keypoint") final String keyPoint,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        // 重新命名图片
        String imageName = "";
        if (userId == null)
            imageName = "I" + EncryptUtil.randomString(8) + Utils.getCurrentTimeStamp();
        else
            imageName = "I" + userId + Utils.getCurrentTimeStamp();

        File file = ImageService.saveImage(image, imageName);

        Socket socket = new Socket("10.211.55.3", 10000);

        Map requestMap = new HashMap();
        requestMap.put("sift", sift);
        requestMap.put("keypoint", keyPoint);

        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            String requestStr = JsonUtil.toJson(requestMap);
            byte[] requestBytes = JsonUtil.toUTF8Json(requestStr);
            dos.write(requestBytes);
            dos.flush();
            dos.close();
            dis.close();
            dis = new DataInputStream(socket.getInputStream());

            byte[] data = new byte[dis.available()];
            while (dis.read(data) != -1) {
                Map resp = JsonUtil.fromUTF8Json(data, Map.class);
                System.out.println(resp);
                //获取返回的景物ID
                String id = resp.get("id").toString();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Success(200, "图片上传成功", null, null);
    }


    @RequestMapping(path = "compare", method = RequestMethod.POST)
    @ResponseBody
    public Success compare(@RequestParam("image") CommonsMultipartFile image
    ) {
        // 重新命名图片
        String imageName = "I" + EncryptUtil.randomString(8) + Utils.getCurrentTimeStamp();

        // 获取特征值
        int max_match = 0;
        Scenery bestMatchScenery = new Scenery();
        List<Scenery> sceneries = sceneryDao.getSceneries();

        if (sceneries == null)
            throw new InvalidException(404, "数据库没有数据");
        List<Point> points = SIFTService.getSiftCharacter(image, imageName);

        for (Scenery scenery : sceneries) {
            List<Point> matchPoints = SIFTService.getSiftCharacter(scenery.getImage());
            int number = SIFTService.getSimilarPointsNum(points, matchPoints);
            System.out.println("与" + scenery.getName() + "匹配率:" + (float) number / points.size());
             if (number > max_match) {
                bestMatchScenery = scenery;
                max_match = number;
            }
        }
        return new Success(200, "获取信息成功", bestMatchScenery);
    }


}
