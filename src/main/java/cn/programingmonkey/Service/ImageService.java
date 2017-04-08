package cn.programingmonkey.Service;

import cn.programingmonkey.Constant.Constant;
import cn.programingmonkey.Exception.InvalidException;
import cn.programingmonkey.Utils.Sift.ImageTransform;
import cn.programingmonkey.Utils.Sift.MyPoint;
import cn.programingmonkey.Utils.Sift.utility.Image_Utility;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cai on 2017/2/20.
 */
public class ImageService {

//    在这里进行图片处理服务，包括指纹算法和SIFT算法


//    public static void  getSIFT(String fileName){
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat mat = Highgui.imread(fileName);
//        Mat desc = new Mat();
//        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
//        MatOfKeyPoint mkp =new MatOfKeyPoint();
//        fd.detect(mat, mkp);
//        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
//        de.compute(mat,mkp,desc );//提取sift特征
//        System.out.println(desc.cols());
//        System.out.println(desc.rows());
//    }

    public static String fingerPrint(File  file) throws IOException {

         int width  = 8;
         int height = 8;
//        <2>灰度化—对32*32大小的图像进行灰度化
        System.out.println(file);
        if (file == null){
            System.out.println("file is null");
        }
        InputStream is = new FileInputStream(file);
        BufferedImage bi = ImageIO.read(is);

        double sx = (double) width/bi.getWidth();
        double sy = (double) height/bi.getHeight();

        BufferedImage resultImage = new BufferedImage(width,height,bi.getType());
        // 调用画图类画缩小尺寸后的图
        Graphics2D g = resultImage.createGraphics();
        //smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(bi,AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        resultImage.getHeight();
        resultImage.getWidth();

//        <3>离散余弦变换(DCT)—对32*32大小图像进行DCT
        int [] pixels = new int[width*height];
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i* height + j] = rgbToGray(resultImage.getRGB(i, j));
            }
        }
//        <4>计算均值—用32*32大小图片前面8*8大小图片处理并计算这64个像素的均值
        int avgPixel= 0;
        int m = 0;
        for (int i =0; i < pixels.length; ++i) {
            m +=pixels[i];
        }
        m = m /pixels.length;
        avgPixel = m;

//        <4>得到8*8图像的phash—8*8的像素值中大于均值的则用1表示，小于的用0表示，这样就得到一个64位二进制码作为该图像的phash值。

        int[] comps= new int[width * height];
        for (int i = 0; i < comps.length; i++) {
            if(pixels[i] >= avgPixel) {
                comps[i]= 1;
            }else {
                comps[i]= 0;
            }
        }
//        <5>计算两幅图像ahash值的汉明距离，距离越小，表明两幅图像越相似；距离越大，表明两幅图像距离越大。
        StringBuffer hashCode = new StringBuffer();
        for (int i = 0; i < comps.length; i+= 4) {
            int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
            hashCode.append(Integer.toHexString(result));//二进制转为16进制
        }
        return hashCode.toString();
    }

    // 计算图片的灰度
    public static int rgbToGray(int pixels) {

        // int _alpha =(pixels >> 24) & 0xFF;
        int _red = (pixels >> 16) & 0xFF;
        int _green = (pixels >> 8) & 0xFF;
        int _blue = (pixels) & 0xFF;
        return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
    }

    // 计算两个phah之间的汉明距离
    public static int hammingDistance(String phash,String pHash2) {

        int distance;
        if (phash.length() != pHash2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < pHash2.length(); i++) {
                System.out.println(phash.charAt(i)+ "  " + pHash2.charAt(i));
                if (phash.charAt(i) != pHash2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    /**
     *
     * @param commonsMultipartFile
     * @param
     * @param imageName
     * @return
     * @throws IOException
     */
    public static File saveImage(CommonsMultipartFile commonsMultipartFile,
                                 String               imageName) throws IOException {
        System.out.println("begin" + new Date().getTime());
        String basePath = "";
        if(!SystemUtils.IS_OS_WINDOWS)
            basePath = "/resources/tourguide/images";

        //获取图片的真实名称判断是不是一张png格式或者是jpeg格式的图片
        String realName = commonsMultipartFile.getOriginalFilename();
        System.out.println(realName);
        int extpoint = realName.indexOf(".");
        String ext =  realName.substring(extpoint,realName.length());
        // 判断上传的文件是不是一个图片(png,jpeg)
        if (!ext.toUpperCase().equals(".PNG") && !ext.toUpperCase().equals(".JPG"))
            throw new InvalidException("无效的文件,请选择png文件或者jpg文件");

        // 判断文件夹是否存在不存在就保存文件夹
        File file = new File(basePath);
       // BufferedImage  shrink = shrinkImage(file,16,16);
        // 文件夹不存在创建文件夹
        if (!file.exists()) {file.mkdirs();}
        // 创建保存文件

        // 将文件保存到本地文件夹中
        File image = new File(basePath + "/" + imageName+".jpg");
        FileUtils.copyInputStreamToFile(commonsMultipartFile.getInputStream(),image);
        commonsMultipartFile.getInputStream().close();

        System.out.println("end" + new Date().getTime());
        System.gc();
        return  image;
    }

    /**
     * 保存用户的头像文件
     * @param image
     * @param request
     * @param userId
     * @throws IOException
     */

    // 保存用户头像文件
    public void saveUserHeadImage(File image, HttpServletRequest request,String userId) throws IOException {
        String basePath = request.getSession().getServletContext().getRealPath("/headImage/");
        String realName = image.getName();
        System.out.println(realName);
        int extpoint = realName.indexOf(".");
        String ext = realName.substring(extpoint, realName.length());

        // 判断上传的文件是不是一个图片(png,jpeg)
        if (!ext.toUpperCase().equals(".PNG") && !ext.toUpperCase().equals(".JPG"))
            throw new InvalidException("无效的文件,请选择png文件或者jpg文件");

        File dir = new File(basePath);
        if (!dir.exists())
            dir.mkdir();

        String imageName = userId +"."+ext;
        File file = new File(basePath + imageName);
        FileUtils.copyInputStreamToFile(new FileInputStream(image),file);
    }

    /**
     * 将图片发送到图片服务器上，图像命名规则采用用户id进行命名
     * 如果用户的图片已经存在则直接替换
     * @param image     // 图片资源
     * @param imageName // 重新命名的文件名
     * @throws IOException
     */
    public static void uploadImageToImageServer(File image,String imageName) throws IOException {

        Long start =  new Date().getTime();
        InputStream inputStream = new FileInputStream(image);
        String realName = image.getName();
        // 判断上传的文件是不是一个图片(png,jpeg)
        if (!realName.toUpperCase().endsWith(".PNG") && !realName.toUpperCase().endsWith(".JPG"))
            throw new InvalidException("无效的文件,请选择png文件或者jpg文件");

        String endpoint;
        if (SystemUtils.IS_OS_LINUX) {
            endpoint = Constant.OSS_INTERNAL_ENDPOINT;
        }
        else {
            endpoint = Constant.OSS_ENDPOINT;
        }

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        meta.setCacheControl("public,max-age=" + 60*60*24*30);
        if (realName.endsWith("jpg"))
            meta.setContentType(Constant.OSS_CONTENTTYPE_JPEG);
        else meta.setContentType(Constant.OSS_CONTENTTYPE_PNG);
        meta.setLastModified(new Date());
        String key = "fudatour/" + imageName +"jpg";
        OSSClient client = null;
        try {
            client = new OSSClient(endpoint, Constant.OSS_ACCESSKEYID, Constant.OSS_SECRETKEYID);
            boolean tf = client.doesObjectExist(Constant.OSS_BUCKET_NAME,imageName);
            if (tf == true)
                client.deleteObject(Constant.OSS_BUCKET_NAME,imageName);
            client.putObject(Constant.OSS_BUCKET_NAME, key, inputStream, meta);
        } finally {
            if (client != null)
                client.shutdown();
        }
        // 计算上传话费的时间
        System.out.println("upload to server duration " + (new Date().getTime()-start));
    }

    /**
     * 获取SIFT算法的特征值
     * @param image
     * @return
     */
    public static List<MyPoint> getSIFTCharacteristicValue(File image) throws IOException {

        String name =  image.getName();
        String grayDir = "/resources/tourguide/images/gray/";                   // 保存灰度化后的文件
        String vectorDir = "/resources/tourguide/images//vector/";              //  保存获取特征值后的文件

        // 将图片进行灰度化，去除掉光和其他外因的影响
        BufferedImage bufferedImage =  grayTran(image);
        // 将灰度化后的文件保存到本地
        System.out.println("begin write" +new Date().getTime());
        ImageIO.write(bufferedImage,"JPG",new File(grayDir +name));
        // 获取高斯金字塔
        System.out.println("end write" +new Date().getTime());
        HashMap<Integer,double[][]> result= ImageTransform.getGaussPyramid(Image_Utility.imageToDoubleArray(bufferedImage), 20, 3,1.6);
        HashMap<Integer,double[][]> dog=ImageTransform.gaussToDog(result, 6);
        // 获取特征值
        HashMap<Integer, java.util.List<MyPoint>> keyPoints=ImageTransform.getRoughKeyPoint(dog,6);
        // 描绘特征向量
        keyPoints=ImageTransform.filterPoints(dog, keyPoints, 10,0.03);
        bufferedImage = ImageTransform.drawPoints(result,keyPoints);
        // 将图片保存到本地
        ImageIO.write(bufferedImage,"JPG",new File(vectorDir+name));

        //获取特征向量
        List<MyPoint> v1=ImageTransform.getCharacterVectors(bufferedImage);
        for(MyPoint point : v1){ System.out.println("point " +point.toString()); System.out.println("size" + v1.size());}

        return v1;
    }

    @Test
    public void getSIFTCharacteristicValueTest() throws IOException {
        long begintime = new Date().getTime();
//        String filePath = System.getProperty("user.dir");
//        filePath +="/cn/programingmonkey/Resources/Images/test.jpg";
        String filePath = "/Users/caihongfeng/Downloads/test.jpg";
        BufferedImage bufferedImage = grayTran(new File(filePath));
        ImageIO.write(bufferedImage,"JPG",new File("/Users/caihongfeng/Downloads/testGray.jpg"));

        //获取高斯金字塔
        HashMap<Integer,double[][]> result= ImageTransform.getGaussPyramid(Image_Utility.imageToDoubleArray(bufferedImage), 20, 3,1.6);
        System.out.println(result);
        HashMap<Integer,double[][]> dog=ImageTransform.gaussToDog(result, 6);
        HashMap<Integer, java.util.List<MyPoint>> keyPoints=ImageTransform.getRoughKeyPoint(dog,6);
        keyPoints=ImageTransform.filterPoints(dog, keyPoints, 10,0.03);
        bufferedImage = ImageTransform.drawPoints(result,keyPoints);
        ImageIO.write(bufferedImage,"JPG",new File("/Users/caihongfeng/Downloads/testGauss.jpg"));

        //获取特征向量
        List<MyPoint> v1=ImageTransform.getCharacterVectors(bufferedImage);
        for(MyPoint point : v1){ System.out.println(point.toString()); System.out.println(v1.size());}


        bufferedImage=Image_Utility.arrayToGreyImage(Image_Utility.open(Image_Utility.imageToArray(bufferedImage),20));
        ImageIO.write(bufferedImage,"JPG",new File("/Users/caihongfeng/Downloads/testArrayToGrey.jpg"));
        bufferedImage=Image_Utility.doubleArrayToGreyImage(dog.get(1));
        ImageIO.write(bufferedImage,"JPG",new File("/Users/caihongfeng/Downloads/testDoubleArrayToGrey.jpg"));
        System.out.println(new Date().getTime() - begintime);
    }

    private static BufferedImage grayTran(File file) {

        BufferedImage bimg = null;
        try {
            System.out.println(file.getName());
            bimg = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取图片的宽高
        int width = bimg.getWidth();
        int height = bimg.getHeight();

        BufferedImage targetImage =  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//BufferedImage.TYPE_BYTE_BINARY);

        // 图片灰度化
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = bimg.getRGB(i, j);
                int c_red = (rgb >> 16) & 0xFF;
                int c_green = (rgb >> 8) & 0xFF;
                int c_blue = rgb & 0xFF;
                int grayRGB = (int) (0.3 * c_red + 0.59 * c_green + 0.11 * c_blue);////�ҶȻ�
                rgb = (255 << 24) | (grayRGB << 16) | (grayRGB << 8) | grayRGB;///�ҶȻ��ָ�
                targetImage.setRGB(i, j, rgb);
            }
        }
        bimg = null;
        return targetImage;
    }


    public static BufferedImage  shrinkImage(CommonsMultipartFile file,int height,int width) throws IOException {


        InputStream is = file.getInputStream();
        BufferedImage bi = ImageIO.read(is);

        double sx = (double) width / bi.getWidth();
        double sy = (double) height / bi.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, bi.getType());
        // 调用画图类画缩小尺寸后的图
        Graphics2D g = resultImage.createGraphics();
        //smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(bi, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        is.close();
        return resultImage;

    }



}
