package cn.programingmonkey.Service.SIFT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by cai on 2017/4/12.
 */
public class KeyPoint {


    /**
     * 获取关键点，关键点的提取需要在上一层，下一层 同层的26个相邻点（上下9 *2 同层 8个点）中进行提取，
     * 如果在这26个点中关键点是极值点（最大或者最小）那么就确认为关键点
     *
     * @param dogPyramid
     * @param num
     * @return
     */
    public static HashMap<Integer, List<Point>> getRoughKeyPoint(HashMap<Integer, double[][]> dogPyramid, int num) {

        HashMap<Integer, List<Point>> resultMap = new HashMap<Integer, List<Point>>();
        Set<Integer> dogIndex = dogPyramid.keySet();
        for (int i : dogIndex) {
            if (((i % num) != 0) && ((i % num) != num - 2)) {
                double[][] dogImage = dogPyramid.get(i);
                double[][] dogImageDown = dogPyramid.get(i - 1);
                double[][] dogImageUp = dogPyramid.get(i + 1);

                List<Point> mpList = new ArrayList<Point>();

                int width = dogImage[0].length;
                int height = dogImage.length;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if ((x > 0) && x < width - 1 && y > 0 && y < height - 1) {
                            double[] values = new double[26];
                            double keyValue = dogImage[y][x];
                            //  同层点
                            values[0] = dogImage[y - 1][x - 1];
                            values[1] = dogImage[y - 1][x];
                            values[2] = dogImage[y - 1][x + 1];
                            values[3] = dogImage[y][x - 1];
                            values[4] = dogImage[y][x + 1];
                            values[5] = dogImage[y + 1][x - 1];
                            values[6] = dogImage[y + 1][x];
                            values[7] = dogImage[y + 1][x + 1];
                            // 下一层点
                            values[8] = dogImageDown[y - 1][x - 1];
                            values[9] = dogImageDown[y - 1][x];
                            values[10] = dogImageDown[y - 1][x + 1];
                            values[11] = dogImageDown[y][x - 1];
                            values[12] = dogImageDown[y][x];
                            values[13] = dogImageDown[y][x + 1];
                            values[14] = dogImageDown[y + 1][x - 1];
                            values[15] = dogImageDown[y + 1][x];
                            values[16] = dogImageDown[y + 1][x + 1];

                            //上一层点
                            values[17] = dogImageUp[y - 1][x - 1];
                            values[18] = dogImageUp[y - 1][x];
                            values[19] = dogImageUp[y - 1][x + 1];
                            values[20] = dogImageUp[y][x - 1];
                            values[21] = dogImageUp[y][x];
                            values[22] = dogImageUp[y][x + 1];
                            values[23] = dogImageUp[y + 1][x - 1];
                            values[24] = dogImageUp[y + 1][x];
                            values[25] = dogImageUp[y + 1][x + 1];

                            boolean isOrNote = isExtremeVlaue(values, keyValue);
                            if (isOrNote == true) {

                                Point mp = new Point();
                                mp.setX(x);
                                mp.setY(y);
                                mp.setOctave(i / 6);
                                mp.setS(i % 6);
                                mpList.add(mp);
                            }

                        }
                    }
                }
                resultMap.put(i, mpList);
            }
        }
        return resultMap;
    }


    /**
     * 判断点在上下同层中是否是最大值或者最小值
     *
     * @param values
     * @param keyValue
     * @return
     */
    public static boolean isExtremeVlaue(double[] values, double keyValue) {

        if (keyValue > values[0] + 0.001) {

            for (double v : values)
                if (keyValue <= v + 0.001) return false;
            return true;

        } else if (keyValue < values[0] - 0.001) {

            for (double v : values)
                if (keyValue >= v - 0.001) return false;
            return true;

        } else return false;
    }


    /**
     * 关键点过滤
     *
     * @param dogPyramid
     * @param keyPoints
     * @param r
     * @param contrast
     * @return
     */

    public static HashMap<Integer, List<Point>> filterPoints(HashMap<Integer, double[][]> dogPyramid,
                                                               HashMap<Integer, List<Point>> keyPoints,
                                                               int r,
                                                               double contrast) {

        HashMap<Integer, List<Point>> resultMap = new HashMap<Integer, List<Point>>();
        Set<Integer> pSet = keyPoints.keySet();

        for (int i : pSet) {
            List<Point> points = keyPoints.get(i);
            List<Point> resultPoints = new ArrayList<Point>();
            double[][] gaussImage = dogPyramid.get(i);
            for (Point mp : points) {
                int x = mp.getX();
                int y = mp.getY();

                double xy00 = gaussImage[y - 1][x - 1];
                double xy01 = gaussImage[y - 1][x];
                double xy02 = gaussImage[y - 1][x + 1];
                double xy10 = gaussImage[y][x - 1];
                double xy11 = gaussImage[y][x];
                double xy12 = gaussImage[y][x + 1];
                double xy20 = gaussImage[y + 1][x - 1];
                double xy21 = gaussImage[y + 1][x];
                double xy22 = gaussImage[y + 1][x + 1];

                double dxx = xy10 + xy12 - 2 * xy11;
                double dyy = xy01 + xy21 - 2 * xy11;
                double dxy = (xy22 - xy20) - (xy02 - xy00);
                double trH = dxx + dyy;
                double detH = dxx * dyy;

                double avg = (xy00 + xy01 + xy02 + xy10 + xy11 + xy12 + xy20 + xy21 + xy22) / 9;
                double DX = (xy00 - avg) * (xy00 - avg) + (xy01 - avg) * (xy01 - avg) + (xy02 - avg) * (xy02 - avg) + (xy10 - avg) * (xy10 - avg) +
                        (xy11 - avg) * (xy11 - avg) + (xy12 - avg) * (xy12 - avg) + (xy20 - avg) * (xy20 - avg) + (xy21 - avg) * (xy21 - avg) + (xy22 - avg) * (xy22 - avg);
                DX = DX / 9;
                double threshold = (double) (r + 1) * (r + 1) / r;
                if ((detH > 0 && (trH * trH) / detH < threshold) && (DX >= contrast)) resultPoints.add(mp);
            }
            resultMap.put(i, resultPoints);
        }
        return resultMap;
    }
}
