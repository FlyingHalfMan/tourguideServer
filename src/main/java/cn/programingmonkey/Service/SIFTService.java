package cn.programingmonkey.Service;

import cn.programingmonkey.Service.SIFT.*;
import cn.programingmonkey.Service.SIFT.Point;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static cn.programingmonkey.Service.SIFT.GaussPyramid.imageToDoubleArray;

/**
 * Created by cai on 2017/4/12.
 */
public class SIFTService {

    public static final String ORIGION_PATH = "/resources/tourguide/images/origion/";

    public static List<Point> getSiftCharacter(String imageName) {
        try {

            File image = new File(ORIGION_PATH + imageName);
            // 获取高斯模糊后的图像
            BufferedImage bufferedImage = GaussBlur.grayTran(image);
            // 获取高斯金字塔
            HashMap<Integer, double[][]> gaussPyramid = GaussPyramid.getGaussPyramid(imageToDoubleArray(bufferedImage), 20, 3, 1.6);
            // 获取差分高斯公式
            HashMap<Integer, double[][]> difGaussPyramid = DiffGaussPyramid.gaussToDog(gaussPyramid, 6);
            // 获取特征点
            HashMap<Integer, List<Point>> roughKeyPoint = KeyPoint.getRoughKeyPoint(difGaussPyramid, 6);
            // 过滤特征点
            HashMap<Integer, List<Point>> filterPoint = KeyPoint.filterPoints(difGaussPyramid, roughKeyPoint, 10, 0.03);
            // 生成特征向量
            bufferedImage = drawPoints(gaussPyramid, filterPoint);
            ImageIO.write(bufferedImage, "JPEG", new File("/resources/tourguide/images/vector/" + imageName));
            List<Point> v1 = getVectors(gaussPyramid, filterPoint);
            return v1;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Point> getSiftCharacter(CommonsMultipartFile file, String name) {

        try {
            // 先将图片保存在本地
            File image = ImageService.saveImage(file, name);
            // 获取高斯模糊后的图像
            BufferedImage bufferedImage = GaussBlur.grayTran(image);
            // 获取高斯金字塔
            HashMap<Integer, double[][]> gaussPyramid = GaussPyramid.getGaussPyramid(imageToDoubleArray(bufferedImage), 20, 3, 1.6);
            // 获取差分高斯公式
            HashMap<Integer, double[][]> difGaussPyramid = DiffGaussPyramid.gaussToDog(gaussPyramid, 6);
            // 获取特征点
            HashMap<Integer, List<Point>> roughKeyPoint = KeyPoint.getRoughKeyPoint(difGaussPyramid, 6);
            // 过滤特征点
            HashMap<Integer, List<Point>> filterPoint = KeyPoint.filterPoints(difGaussPyramid, roughKeyPoint, 10, 0.03);
            // 生成特征向量
            // bufferedImage = drawPoints(gaussPyramid, filterPoint);
            // ImageIO.write(bufferedImage, "JPEG", new File("/resources/tourguide/images/vector/" + name));
            return getCharacterVectors(bufferedImage);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取相似点

    /**
     * 计算两个特征向量中欧式距离的最小值和最大值，如果两个的特征点的距离小于某个阀值则认为两个点匹配上
     *d = sqrt((x1-x2)^+(y1-y2)^)
     * @param vectors1
     * @param vectors2
     * @return
     */
    public static int getSimilarPointsNum(List<Point> vectors1, List<Point> vectors2) {

        int similarNum = 0;
        List<Point> vt1 = vectors1;
        List<Point> vt2 = vectors2;

        Point mp1;
        int index1 = 0;
        for (; index1 < vt1.size(); index1++) {
            mp1 = vt1.get(index1);

            double minED = 10;
            double sMinED = 0;
            Point mp2 = null;
            int index2 = 0;
            for (; index2 < vt2.size(); index2++) {
                mp2 = vt2.get(index2);
                double tempED = 0.0;
                double[] v1 = mp1.getGrads();
                double[] v2 = mp2.getGrads();
                for (int i = 0; i < v1.length; i++) {
                    tempED = tempED + (v1[i] - v2[i]) * (v1[i] - v2[i]);
                }
                tempED = Math.sqrt(tempED);
                if (tempED < minED) {
                    sMinED = minED;
                    minED = tempED;
                }
            }
            if (minED / sMinED > 0.8 || minED > 0.1) {
                continue;
            }
            vt1.set(index1, mp1);
            vt2.set(index2 - 1, mp2);
            similarNum++;
        }
        return similarNum;
    }


    private static double[] normalize(double[] source, double sum) {
        double[] result = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i] / sum;
            if (result[i] < 0.0001) {
                result[i] = 0;
            }
        }
        return result;
    }


    private static double[][] getGaussTemplate(double sigma) {

        int width, height;
        width = (int) (6 * sigma + 1);
        if (width < 6 * sigma + 1) {
            width++;
        }
        height = width;
        double[][] template = new double[height][width];
        double sum = 0.0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double value;
                double index;
                index = (i - height / 2) * (i - height / 2) + (j - width / 2) * (j - width / 2);
                index = -index / (2 * sigma * sigma);
                value = (1 / (2 * sigma * Math.PI)) * (Math.pow(Math.E, index));
                template[i][j] = value;
                sum = sum + value;
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                template[i][j] = template[i][j] / sum;
            }
        }
        return template;
    }

    private static List<Point> getCharacterVectors(BufferedImage img) {

        HashMap<Integer, double[][]> result = GaussPyramid.getGaussPyramid(imageToDoubleArray(img), 20, 3, 1.6);

        HashMap<Integer, double[][]> dog = DiffGaussPyramid.gaussToDog(result, 6);
        HashMap<Integer, List<Point>> keyPoints = KeyPoint.getRoughKeyPoint(dog, 6);
        keyPoints = KeyPoint.filterPoints(dog, keyPoints, 10, 0.03);

        List<Point> vctors = getVectors(result, keyPoints);
        return vctors;
    }

    /**
     * 为特征点分配方向，获取特征向量
     *
     * @param image
     * @param keyPoints
     * @param nLayer
     * @param baseSigma
     * @param largestDistance
     * @return
     */
    private static List<Point> getFeatureVector(double[][] image, List<Point> keyPoints, int nLayer, double baseSigma, int largestDistance) {

        List<Point> vectorList = new ArrayList<Point>();
        if (keyPoints.isEmpty()) {
            return null;
        }
        int s = keyPoints.get(0).getS();
        int octave = keyPoints.get(0).getOctave();

        for (Point mp : keyPoints) {
            int x = mp.getX();
            int y = mp.getY();

            int width = image[0].length;
            int height = image.length;
            double y2 = image[y + 1][x];
            double y1 = image[y - 1][x];
            double x2 = image[y][x + 1];
            double x1 = image[y][x - 1];

            double theta = Math.atan2(y2 - y1, x2 - x1) * (180 / Math.PI) + 180;
            double[] keyTM = new double[45];
            double[] keyAngle = new double[45];
            double[] angleRatio = new double[45];
            double max = 0;
            int index = 0;

            double s_oct = baseSigma * Math.pow(2, (double) mp.getS() / nLayer);
            double sigma = 1.5 * s_oct;
            double[][] gtemplate = getGaussTemplate(sigma);
            int radius = (int) (3 * sigma);
            int diameter = 2 * radius;

            int gtCenter = gtemplate.length / 2;
            if (x >= diameter && x < width - diameter && y >= diameter && y < height - diameter) {
                for (int j = 0; j <= 2 * radius; j++) {
                    for (int i = 0; i <= 2 * radius; i++) {
                        double ty2 = image[y - radius + j + 1][x - radius + i];
                        double ty1 = image[y - radius + j - 1][x - radius + i];
                        double tx2 = image[y - radius + j][x - radius + i + 1];
                        double tx1 = image[y - radius + j][x - radius + i - 1];
                        double tM = Math.sqrt((ty2 - ty1) * (ty2 - ty1) + (tx2 - tx1) * (tx2 - tx1));
                        double tTheta = Math.atan2(ty2 - ty1, tx2 - tx1) * (180 / Math.PI) + 180;
                        int section = (int) (tTheta / 9);
                        if (360 - Math.abs(tTheta) < 0.0001) {
                            section = 0;
                        }
                        keyTM[section] = keyTM[section] + tM * gtemplate[gtCenter - radius + j][gtCenter - radius + i];
                        keyAngle[section] = keyAngle[section] + tTheta * gtemplate[gtCenter - radius + j][gtCenter - radius + i];////������������������Ƕȹ���;
                        angleRatio[section] += gtemplate[gtCenter - radius + j][gtCenter - radius + i];
                    }
                }

                for (int key = 0; key < keyTM.length; key++) {
                    if (keyTM[key] > max) {
                        max = keyTM[key];
                        index = key;
                    }
                }
                theta = keyAngle[index] / angleRatio[index];
            }
            for (int key = 0; key < keyTM.length; key++) {
                if (keyTM[key] > max * 0.8) {
                    theta = keyAngle[key] / angleRatio[key];
                    if (x >= largestDistance + 1 && x < width - 1 - largestDistance && y >= largestDistance + 1 && y < height - 1 - largestDistance) {

                        int secNum = 15;
                        int secAngle = 360 / secNum;
                        double[] grads = new double[secNum * (largestDistance / 2)];

                        double sum = 0;
                        for (int j = y - largestDistance; j <= y + largestDistance; j++) {
                            for (int i = x - largestDistance; i <= x + largestDistance; i++) {

                                if ((j == y) && (i == x)) {
                                    continue;
                                }

                                double ty2 = image[j + 1][i];
                                double ty1 = image[j - 1][i];
                                double tx2 = image[j][i + 1];
                                double tx1 = image[j][i - 1];

                                double tM = Math.sqrt((ty2 - ty1) * (ty2 - ty1) + (tx2 - tx1) * (tx2 - tx1));
                                sum = sum + tM;
                                double tTheta = Math.atan2(ty2 - ty1, tx2 - tx1) * (180 / Math.PI) + 180;
                                double absTheta = tTheta - theta;
                                int section = (int) (absTheta / secAngle);
                                if (360 - Math.abs(absTheta) < 0.0001) {
                                    section = 0;
                                }
                                if (section < 0) {
                                    section = section + secNum;
                                }
                                int distance = Math.max(Math.abs(y - j), Math.abs(x - i));

                                if (distance <= 2) {
                                    grads[section] = grads[section] + tM;
                                } else if (distance <= 4) {
                                    grads[section + 1 * secNum] = grads[section + 1 * secNum] + tM;
                                } else if (distance <= 6) {
                                    grads[section + 2 * secNum] = grads[section + 2 * secNum] + tM;
                                } else if (distance <= 8) {
                                    grads[section + 3 * secNum] = grads[section + 3 * secNum] + tM;
                                } else if (distance <= 10) {
                                    grads[section + 4 * secNum] = grads[section + 4 * secNum] + tM;
                                }

                            }
                        }
                        grads = normalize(grads, sum);
                        Point rmp = new Point(x, y, s, octave, theta, grads);
                        vectorList.add(rmp);

                    }
                }
            }
        }

        return vectorList;
    }


    private static List<Point> getVectors(HashMap<Integer, double[][]> gaussPyramid, HashMap<Integer, List<Point>> keyPoints) {

        List<Point> testPoint = new ArrayList<Point>();
        Set<Integer> iset = gaussPyramid.keySet();
        int length = iset.size();
        for (int i = 0; i < length; i++) {
            double[][] tempImage = gaussPyramid.get(i);
            List<Point> imagePoint = keyPoints.get(i);
            if (null != imagePoint) {
                List<Point> vector = getFeatureVector(tempImage, imagePoint, 6, 1.6, 8);
                if (vector == null) {
                    continue;
                }
                testPoint.addAll(vector);
            }
        }
        return testPoint;
    }

    // 描线
    private static BufferedImage drawPoints(HashMap<Integer, double[][]> gaussPyramid, HashMap<Integer, List<Point>> keyPoints) {

        HashMap<Integer, double[][]> result = new HashMap<Integer, double[][]>();

        List<Point> testPoint = new ArrayList<Point>();
        Set<Integer> iset = gaussPyramid.keySet();
        int length = iset.size();
        for (int i = 0; i < length; i++) {

            double[][] tempImage = gaussPyramid.get(i);
            List<Point> imagePoint = keyPoints.get(i);

            if (null != imagePoint) {

                List<Point> vector = getFeatureVector(tempImage, imagePoint, 6, 1.6, 8);
                if (vector == null) {
                    result.put(i, tempImage);
                    continue;
                }
                testPoint.addAll(vector);

                for (Point mp : imagePoint) {
                    int x = mp.getX();
                    int y = mp.getY();
                    tempImage[y][x] = 255;
                    tempImage[y + 1][x] = 255;
                    tempImage[y][x + 1] = 255;
                    tempImage[y - 1][x] = 255;
                    tempImage[y][x - 1] = 255;
                }
            }
            result.put(i, tempImage);
        }
        BufferedImage bimg = getP2P(result.get(1), testPoint, null, null);
        return bimg;
    }

    private static BufferedImage getP2P(double[][] sourceArray, List<Point> vectors1, double[][] targetArray, List<Point> vectors2) {

        BufferedImage resultImage = GaussBlur.doubleArrayToGreyImage(sourceArray);
        List<Point> vt1 = vectors1;
        List<Point> vt2 = vectors1;
        Point mp1 = null;
        int index1 = 0;
        for (; index1 < vt1.size(); index1++) {

            mp1 = vt1.get(index1);
            mp1.setPreX(mp1.getX() * (int) Math.pow(2, mp1.getOctave()));
            mp1.setPreY(mp1.getY() * (int) Math.pow(2, mp1.getOctave()));
            if (mp1.getPreX() > sourceArray[0].length * 2 / 3) {
                continue;
            }
            double minED = 10;
            double sMinED = 0;
            int x2 = 0;
            int y2 = 0;
            Point mp2 = null;
            int index2 = 0;
            for (; index2 < vt2.size(); index2++) {

                mp2 = vt2.get(index2);
                mp2.setPreX(mp2.getX() * (int) Math.pow(2, mp2.getOctave()));
                mp2.setPreY(mp2.getY() * (int) Math.pow(2, mp2.getOctave()));
                if (mp2.getPreX() == mp1.getPreX() || (mp2.getPreX() < sourceArray[0].length * 2 / 3)) {
                    continue;
                }

                double tempED = 0.0;
                double[] v1 = mp1.getGrads();
                double[] v2 = mp2.getGrads();
                for (int i = 0; i < v1.length; i++) {
                    tempED = tempED + (v1[i] - v2[i]) * (v1[i] - v2[i]);
                }
                tempED = Math.sqrt(tempED);
                if (tempED < minED) {
                    sMinED = minED;
                    minED = tempED;
                    x2 = mp2.getPreX();
                    y2 = mp2.getPreY();
                }
            }

            if (minED / sMinED > 0.4 || minED > 0.1) {
                continue;
            }
            vt1.set(index1, mp1);
            vt1.set(index2 - 1, mp2);
            vt2.set(index1, mp1);
            vt2.set(index2 - 1, mp2);

            Graphics2D g = resultImage.createGraphics();
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(4));
            g.drawLine(mp1.getPreX(), mp1.getPreY(), x2, y2);
            resultImage.setRGB(x2, y2, 255 << 16);
            resultImage.setRGB(x2 + 1, y2 + 1, 255 << 16);
            resultImage.setRGB(mp1.getPreX(), mp1.getPreY(), 255 << 16);
            resultImage.setRGB(mp1.getPreX() + 1, mp1.getPreY() + 1, 255 << 16);
        }
        return resultImage;
    }


}

