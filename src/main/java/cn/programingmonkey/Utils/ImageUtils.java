package cn.programingmonkey.Utils;

import cn.programingmonkey.Constant.Constant;

/**
 * Created by cai on 2017/4/14.
 */
public class ImageUtils {

//    public static  void imageSift(String name){
//        System.out.println(System.getProperty("java.library.path"));
//
//
////        System.load("/opt/local/bin/OpenCV/java/libopencv_java320.dylib");
//        Mat mat = new Mat();
//        System.loadLibrary("opencv_java320");
//
//        // 读取图片
//        Mat origion_img = Imgcodecs.imread(Constant.IMAGE_SAVE_PATH_RIGION + name);
//
//        // 图片灰度话
//        Mat grayImage =  new Mat();
//        Imgproc.cvtColor(origion_img,grayImage,Imgproc.COLOR_GRAY2BGR);
//        // 图片保存
//        Imgcodecs.imwrite(Constant.IMAGE_SAVE_PATH_GRAY +name,grayImage);
//
//        Mat desc = new Mat();
//        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
//
//        // 获取特征点
//        MatOfKeyPoint mkp =new MatOfKeyPoint();
//        fd.detect(origion_img, mkp);
//
//        // 获取特征向量
//        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
//        de.compute(origion_img,mkp,desc);//提取sift特征
//        Mat outputImage = new Mat();
//        Features2d.drawKeypoints(grayImage, mkp, outputImage);
//        Imgcodecs.imwrite(Constant.IMAGE_SAVE_PATH_VECTOR +name,outputImage);
//
//        origion_img.release();
//        grayImage.release();
//        desc.release();
//        mkp.release();
//        System.out.println(desc.cols());
//        System.out.println(desc.rows());
//    }

}
