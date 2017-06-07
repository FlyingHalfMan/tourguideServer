package cn.programingmonkey.Service.SIFT;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cai on 2017/4/12.
 */
public class GaussBlur {

    // 二维高斯模糊
    public static BufferedImage getGaussBlur(File image) {
        BufferedImage bufferedImage = null;
        try {
            // 将图片读取到缓存中
            bufferedImage = ImageIO.read(image);
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            int[][] martrix = new int[3][3];
            int[] values = new int[9];

            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++) {
                    readPixel(bufferedImage, i, j, values);
                    fillMatrix(martrix, values);
                    bufferedImage.setRGB(i, j, avgMatrix(martrix));
                }
            ImageIO.write(bufferedImage, "jpeg", new File("/resources/tourguide/images/gray/" + image.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }


    /**
     * @param img
     * @param x
     * @param y
     * @param pixels
     */
    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        for (int i = xStart; i < 3 + xStart; i++)
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;

                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);

            }
    }

    /**
     * @param matrix
     * @param values
     */
    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();

    }


    public static BufferedImage doubleArrayToGreyImage(double[][] sourceArray) {
        int width = sourceArray[0].length;
        int height = sourceArray.length;
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int greyRGB = (int) sourceArray[j][i];
                int rgb = (greyRGB << 16) | (greyRGB << 8) | greyRGB;
                targetImage.setRGB(i, j, rgb);
            }
        }

        return targetImage;
    }
    // 对图像进行高斯模糊化处理（灰度话图像）
    public static BufferedImage grayTran(File image) throws IOException {

        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = bimg.getWidth();
        int height = bimg.getHeight();
        int type = bimg.getType();
        BufferedImage targetImage = null;

        targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = bimg.getRGB(i, j);
                int c_red = (rgb >> 16) & 0xFF;// 255 颜色0-255
                int c_green = (rgb >> 8) & 0xFF;
                int c_blue = rgb & 0xFF;
                int grayRGB = (int) (0.3 * c_red + 0.59 * c_green + 0.11 * c_blue);
                rgb = (255 << 24) | (grayRGB << 16) | (grayRGB << 8) | grayRGB;
                targetImage.setRGB(i, j, rgb);
            }
        }
        // 输出灰度化后的结果
        ImageIO.write(targetImage, "jpeg", new File("/resources/tourguide/images/gray/" + image.getName()));
        return targetImage;
    }
}
