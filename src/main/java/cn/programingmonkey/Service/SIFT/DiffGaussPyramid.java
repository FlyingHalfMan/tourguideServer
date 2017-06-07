package cn.programingmonkey.Service.SIFT;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by cai on 2017/4/12.
 */
public class DiffGaussPyramid {

    /**
     * 差分高斯金字塔
     * 高斯差分金字塔是通过高斯金字塔两层相减获取
     * @param gaussPyramid
     * @param num
     * @return
     */
    public static HashMap<Integer,double[][]> gaussToDog(HashMap<Integer,double[][]> gaussPyramid, int num){

        HashMap<Integer,double[][]> dogPyramid=new HashMap<Integer, double[][]>();

        Set<Integer> iset=gaussPyramid.keySet();
        int length=iset.size();
        for(int i=0;i<length-1;i++){
            double[][]  source1=gaussPyramid.get(i);
            double[][] source2=gaussPyramid.get(i+1);
            int width=source1[0].length;
            int height=source1.length;

            double[][] dogImage=new double[height][width];
            if(((i+1)%num)!=0){
                for(int m=0;m<height;m++){
                    for(int n=0;n<width;n++){
                        dogImage[m][n]=source2[m][n]-source1[m][n];
                    }
                }

                dogPyramid.put(i, dogImage);
            }
        }
        return dogPyramid;
    }
}
