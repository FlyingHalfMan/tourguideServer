package cn.programingmonkey.Service.SIFT;

import cn.programingmonkey.Utils.GaussTemplate;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by cai on 2017/4/12.
 */
public class GaussPyramid {

    // 获取高斯金字塔（构建图像的尺度空间 ，）

    /**
     *
     * @param source     图像的像素矩阵
     * @param minSize    尺度空间中最小层图像的尺寸
     * @param s          每个空间需要检测的极值点个数
     * @param baseSigma  高斯模糊因子
     *                   在每组中检测S个尺度的极值点，则DOG金字塔每组需S+2层图像，
     *                   而DOG金字塔由高斯金字塔相邻两层相减得到，则高斯金字塔每组需S+3层图像，
     *                   实际计算时S在3到5之间
     * @return
     */
    public static HashMap<Integer, double[][]> getGaussPyramid(double[][] source, int minSize, int s, double baseSigma) {

        int width = source[0].length;
        int height = source.length;
        int small = width > height ? height : width;  // 最小的尺寸
        int octave = (int) (Math.log(small / minSize) / Math.log(2.0)); // 计算高斯金字塔的层数
        int gaussS = s + 3;

        // 对于超出(6σ+1)*(6σ+1) 的点不进行考虑
        double[] sig = new double[6];
        sig[0] = baseSigma;
        for (int i = 1; i < gaussS; i++) {
            double preSigma = baseSigma * Math.pow(2, (double) (i - 1) / s);
            double nextSigma = preSigma * Math.pow(2, (double) 1 / s);
            sig[i] = Math.sqrt(nextSigma * nextSigma - preSigma * preSigma);
        }

        HashMap<Integer, double[][]> gaussPyramid = new HashMap<Integer, double[][]>();
        double[][] tempSource = gaussTran(source, 0);

        for (int i = 0; i < octave; i++) {
            int j = 0;
            int index = 0;
            for (; j < gaussS; j++) {
                if (j == 0) {
                    index = i * gaussS + j;
                    gaussPyramid.put(index, tempSource);
                    continue;
                }
                tempSource = gaussTran(tempSource, j);
                index = i * gaussS + j;
                gaussPyramid.put(index, tempSource);

            }
            tempSource = getGapSimpleImg(gaussPyramid.get(index - 2));
        }
        return gaussPyramid;
    }

    /**
     *  将图像转换成一个二维的像素点矩阵
     * @param image
     * @return
     */
    public static double[][] imageToDoubleArray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        double[][] result = new double[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = image.getRGB(i, j);
                int grey = (rgb >> 16) & 0xFF;
                result[j][i] = grey;
            }
        }
        return result;
    }

    /**
     * 高斯模糊
     * @param source
     * @param index
     * @return
     */

    private static double[][] gaussTran(double[][] source, int index) {

        int height = source.length;
        int width = source[0].length;
        double[][] result = new double[height][width];
        double[][] template = GaussTemplate.getTemplate(index);
        int tWH = template[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double sum = 0.0;
                for (int m = 0; m < tWH; m++) {
                    for (int n = 0; n < tWH; n++) {
                        int x = j -  tWH / 2 + n;
                        int y = i -  tWH / 2 + m;
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            sum = sum + source[y][x] * template[m][n];
                        }
                    }
                }
                result[i][j] = sum;
            }
        }
        int i = 0;
        i++;
        return result;
    }


    /**
     * 获取某一层的图像
     * @param source
     * @return
     */
    private static double[][] getGapSimpleImg(double[][] source) {

        int width =  source[0].length / 2;
        int height = source.length / 2;
        double[][] result = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = 2 * i;
                int x = 2 * j;
                result[i][j] = source[y][x];
            }
        }
        return result;
    }
}
