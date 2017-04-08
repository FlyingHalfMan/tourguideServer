package cn.programingmonkey.Utils.Sift;

import cn.programingmonkey.Utils.Sift.utility.Image_Utility;
import cn.programingmonkey.Utils.Sift.utility.Math_Utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 * sift�㷨��ͼ��任��
 * @author Administrator
 *
 */
public class ImageTransform {
	
	
	/**
	 * * ��ͼ����и�˹ģ����������ģ�����������˹ģ�����Ȼ����о�����㡣
	 *  
	 *	@��˹ģ�� :˹ģ����һ��ͼ���˲�������ʹ����̬�ֲ�(��˹����)����ģ��ģ�壬��ʹ�ø�ģ����ԭͼ����������㣬�ﵽģ��ͼ���Ŀ�ġ�
	 *	��ʵ��Ӧ���У��ڼ����˹��������ɢ����ʱ���ڴ��3�Ҿ���֮������ض����Կ����������ã���Щ���صļ���Ҳ�Ϳ��Ժ��ԡ�
	 *	ͨ����ͼ�������ֻ��Ҫ����ľ���Ϳ��Ա�֤�������Ӱ�졣
	 *  
	 * @param source
	 * @param index ��ʾ
	 * @return double[][] ģ�����ͼ����Ϣ����
	 */
	private static double[][] gaussTran(double[][] source,int index){
		
		int height=source.length;
		int width=source[0].length;
		///�����˹���˺�Ľ��
		double[][] result=new double[height][width];
		///��ȡ��˹ģ��
		double[][] template=GaussTemplate.getTemplate(index);
		int tWH=template[0].length;///ģ��ά��
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				
				///����ģ�����������������������
				double sum=0.0;///������
				for(int m=0;m<tWH;m++){
					for(int n=0;n<tWH;n++){
						///������ģ���Ӧ��ͼ���ϵ�λ��
						int x=j-(int)tWH/2+n;
						int y=i-(int)tWH/2+m;
						
						//���ģ������û�г����߽�
						if(x>=0&&x<width&&y>=0&&y<height){
							sum=sum+source[y][x]*template[m][n];
						}
					}
				}
				result[i][j]=sum;
			}
		}
		int i=0;
		i++;
		return result;
	}
	
		
	/**
	 * �ڲ�˽�з����� ����sigma���������˹ģ��ģ��
	 * @param sigma
	 * @return
	 */
	private static double[][] getGaussTemplate(double sigma){
		//sigma=1.6;
		int width,height;
		width=(int) (6*sigma+1);
		if(width<6*sigma+1){
			///6*sigma+1���ΪС������Ҫ�Ծ���ά�ȼ�һ
			width++;
		}
		height=width;
		
		double[][] template=new double[height][width];
		double sum=0.0;///���ڹ�һ����˹ģ��
 
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				double value;	
				double index;//��Ȼ����e��ָ��
				index=(i-height/2)*(i-height/2)+(j-width/2)*(j-width/2);
				index=-index/(2*sigma*sigma);
				
				value=(1/(2*sigma*Math.PI))*(Math.pow(Math.E, index));
				
				template[i][j]=value;//��ֵ��ģ���Ӧλ��
				sum=sum+value;
			}
		}
		///��һ��ģ��
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				template[i][j]=template[i][j]/sum;//��ֵ��ģ���Ӧλ��	
//				System.out.print("    "+template[i][j]);
			}
//			System.out.println();
		}
		////System.out.println("sigma:"+sigma);
	    return template;	
	}
	
	
	
	
	/**
	 *  ������˹������
	 * @param source  ͼ����Ϣ����
	 * @param minSize ����������ͼƬ��С�� ����������octave=(int) (Math.log(small)/Math.log(2.0));
	 * @param s		    ÿһ����s���߶ȣ�����ÿһ��ĸ�˹ͼ����ĿΪS+3��һ��sȡֵΪ3��
	 * @param baseSigma	 ��׼sigma	
	 *   
	 * @return		 ����һ�������ý���������ͼƬ��HashMap<Integer,double[][]> ����������ڣ�0��1�������˳��
	 */
	public static HashMap<Integer,double[][]> getGaussPyramid(double[][] source,int minSize,int s,double baseSigma){

		int width=source[0].length;
		int height=source.length;
		int small=width>height?height:width;
		///�����������
		int octave=(int) (Math.log(small/minSize)/Math.log(2.0));
		///ÿһ���˹ͼ����ĿΪS+3
		int gaussS=s+3;
		
		double[] sig=new double[6];
		sig[0]=baseSigma;
		for(int i=1;i<gaussS;i++){
			double preSigma=baseSigma*Math.pow(2, (double)(i-1)/s);
			double nextSigma=preSigma*Math.pow(2, (double)1/s);
			sig[i]=Math.sqrt(nextSigma*nextSigma-preSigma*preSigma);
		}

		HashMap<Integer,double[][]> gaussPyramid=new HashMap<Integer, double[][]>();///����������		
		double[][] tempSource=gaussTran(source, 0);	//��ʱ�洢
		////��������һ���Ÿ�˹ͼ��
		for(int i=0;i<octave;i++){
			//tempSource =gaussTran(tempSource, baseSigma);
			int j=0;///���ڲ���
			int index = 0;///ÿ��ͼƬ������(hashmap)���������
			for(;j<gaussS;j++){
				if(0==j){
					///��һ�Ų�����ģ������
					index=i*gaussS+j;
					///�����˹������
					gaussPyramid.put(index, tempSource);
					continue;
				}				
				///����õ���һ��ͼƬ�����ڳ߶�		
				//sigma=sigma*Math.pow(2, (double)1/s);
				double start=System.currentTimeMillis();

				///���и�˹ģ��
				tempSource=gaussTran(tempSource, j);
				double end=System.currentTimeMillis();
				System.out.println("ģ��"+(end-start));
				index=i*gaussS+j;
				///�����˹������
				gaussPyramid.put(index, tempSource);
				
			}
//			System.out.println(i+":"+j);
			
			///ѡÿһ��ĵ���������ͼƬ���н�����,ע���ʱ��j��6������5,���Լ�ȥ3������2
			tempSource=getGapSimpleImg(gaussPyramid.get(index-2));
		}
		return gaussPyramid;
	}
	
	
	/**
	 * ˽�з������н����������������
	 * @param source
	 * @param width
	 * @param height
	 * @return
	 */
	private static double[][] getGapSimpleImg(double[][] source){
		
		///����ÿһ���˹ͼ�Ĵ�С,**********************�����������ȡż��λ�ϵĵ���У������С�������󣡣�������
		int width=(int) source[0].length/2;
		int height=(int) source.length/2;
		///�洢�������
		double[][] result=new double[height][width];
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				///����ԭͼ�ϵĶ�Ӧ����
				int y=2*i;
				int x=2*j;
				result[i][j]=source[y][x];
			}
		}
		return result;
	}


	/**
	 * ��ȡ��˹��ֽ�������DoG�������������������Ƹ�˹������˹����������ȡ�ǳ��ȶ��ļ���Сֵ���������㡣
	 * @param source
	 * @param num	ÿһ���˹���������飩�ж�����ͼ��
	 * @return
	 */
	public static HashMap<Integer,double[][]> gaussToDog(HashMap<Integer,double[][]> gaussPyramid,int num){
		
		 HashMap<Integer,double[][]> dogPyramid=new HashMap<Integer, double[][]>();
		
		 ////��ȡ��˹�����������ͼ��
		Set<Integer> iset=gaussPyramid.keySet();
		int length=iset.size();
		for(int i=0;i<length-1;i++){
			double[][]  source1=gaussPyramid.get(i);
			double[][] source2=gaussPyramid.get(i+1);
			int width=source1[0].length;
			int height=source1.length;
			
			 double[][] dogImage=new double[height][width];///��ʱdogͼ��
						
			///����ּ���
			if(((i+1)%num)!=0){
				///�������ÿһ������һ��ͼ��
				for(int m=0;m<height;m++){
					for(int n=0;n<width;n++){
						dogImage[m][n]=source2[m][n]-source1[m][n];						
					}
				}
				
				///����dog������
				dogPyramid.put(i, dogImage);				
			}///end of if 
			
			
		}
		
		
		return dogPyramid;
	}
	
	
	/**
	 * �������DoGͼ��ļ�ֵ��
	 * @param dogPyramid  	���hashmap��key�������������֣������ڸ�˹��������ͼ������ֵ
	 * @param num 		��˹��������������dog��������ÿ���ͼ����Ŀ
	 * @return			HashMap<Integer,List<MyPoint>>  integer�Ǹ�˹�������ڵ�ͼ�������
	 */
	public static HashMap<Integer, List<MyPoint>> getRoughKeyPoint(HashMap<Integer,double[][]> dogPyramid,int num){
		HashMap<Integer, List<MyPoint>> resultMap=new HashMap<Integer, List<MyPoint>>();         
		Set<Integer> dogIndex=dogPyramid.keySet();
		///��ǿforѭ��
		for(int i:dogIndex){
			///����dog������ÿ��ĵ�һ�ź����һ�ţ�Ҳ���Ǹ�˹�ĵ����ڶ��ţ�ͼ�񲻽�����ֵ����
			if(((i%num)!=0)&&((i%num)!=num-2)){
				double[][] dogImage=dogPyramid.get(i);
				///��ȡ�ò�ͼ�ռ�λ���ϵ���һ�����һ��
				double[][] dogImageDown=dogPyramid.get(i-1);
				double[][] dogImageUp=dogPyramid.get(i+1);
				
				List<MyPoint> mpList=new ArrayList<MyPoint>();
				
				int width =dogImage[0].length;
				int height= dogImage.length;
				////�Ը���dogͼ����ֵ��
				for(int y=0;y<height;y++){
					for(int x=0;x<width;x++){
						if((x>0)&&x<width-1&&y>0&&y<height-1){
							////ͼ��ı�Ե��Ĭ�ϲ��Ǽ�ֵ��
							///�Ƚ����³߶��Լ�8����Ĺ�26����
							double[] values=new double[26];		
							
							double keyValue=dogImage[y][x];///�ؼ����ֵ
							
							values[0]=dogImage[y-1][x-1];
							values[1]=dogImage[y-1][x];
							values[2]=dogImage[y-1][x+1];
							values[3]=dogImage[y][x-1];
							values[4]=dogImage[y][x+1];
							values[5]=dogImage[y+1][x-1];
							values[6]=dogImage[y+1][x];
							values[7]=dogImage[y+1][x+1];
							
							values[8]=dogImageDown[y-1][x-1];///��һ��
							values[9]=dogImageDown[y-1][x];
							values[10]=dogImageDown[y-1][x+1];
							values[11]=dogImageDown[y][x-1];
							values[12]=dogImageDown[y][x];
							values[13]=dogImageDown[y][x+1];
							values[14]=dogImageDown[y+1][x-1];
							values[15]=dogImageDown[y+1][x];
							values[16]=dogImageDown[y+1][x+1];
							
							values[17]=dogImageUp[y-1][x-1];///��һ��
							values[18]=dogImageUp[y-1][x];
							values[19]=dogImageUp[y-1][x+1];
							values[20]=dogImageUp[y][x-1];
							values[21]=dogImageUp[y][x];
							values[22]=dogImageUp[y][x+1];
							values[23]=dogImageUp[y+1][x-1];
							values[24]=dogImageUp[y+1][x];
							values[25]=dogImageUp[y+1][x+1];
							
							boolean isOrNote= Math_Utility.isExtremeVlaue(values, keyValue);
							
							//if(keyValue)
							///����Ǽ�ֵ
							if(true==isOrNote){
								
								MyPoint mp=new MyPoint();
								/*mp.setPreX((int) (x*Math.pow(2, (int)(i/6))));
								mp.setPreY((int) (y*Math.pow(2, (int)(i/6))));
								*/
								mp.setX(x);
								mp.setY(y);
								mp.setOctave(i/6);
								mp.setS(i%6);
								
								mpList.add(mp);
								/////
							/*	if(Math.abs(keyValue)<0.03){
								System.out.println(keyValue);
							}*/
							}
							
						}
					}
				}///һ��ͼ��������
				
				////�����ͼ���Ӧ�������㼯
				resultMap.put(i, mpList);
			
				
			}
			
		}
		return resultMap;
	}
	
	
	
	
	
	/**
	 * ��ԭͼ�ϡ�������������
	 * @param gaussPyramid
	 * @param keyPoints
	 * @return
	 */
	public static BufferedImage drawPoints(HashMap<Integer,double[][]> gaussPyramid,HashMap<Integer, List<MyPoint>> keyPoints){
	
		 HashMap<Integer,double[][]> result=new HashMap<Integer, double[][]>();
			
		 List<MyPoint> testPoint=new ArrayList<MyPoint>();
		 
		 ////��ȡ��˹�����������ͼ��
		Set<Integer> iset=gaussPyramid.keySet();
		int length=iset.size();
		for(int i=0;i<length;i++){
			
			 double[][] tempImage =gaussPyramid.get(i);///��ʱͼ��			
			 List<MyPoint> imagePoint=keyPoints.get(i);
			 
			 if(null!=imagePoint){
				 
				 	///��ȡ�����㣬���Ƚ�				 
					List<MyPoint> vector=getFeatureVector(tempImage, imagePoint,6,1.6,8);
					if(vector==null){
						result.put(i, tempImage);
						continue;
					}
				/*	BufferedImage bimg=getP2P(tempImage, vector, null, null);
					tempImage=Image_Utility.imageToDoubleArray(bimg);*/
					
					testPoint.addAll(vector);
						
					///�����ͼ�������˼�ֵ���ͼ��
					for(MyPoint mp:imagePoint){
						int x=mp.getX();
						int y=mp.getY();
						/*int x=mp.getX();
						int y=mp.getY();*/
						//int index=mp.getOctave()*6+mp.getS();
						//System.out.println(tempImage[y][x]);
						tempImage[y][x]=255;
						tempImage[y+1][x]=255;
						tempImage[y][x+1]=255;
						tempImage[y-1][x]=255;
						tempImage[y][x-1]=255;
						//System.out.println(++n);
						
					}
		
				}
			 
//				System.out.println("\n\n\n");
				
				///�����˹������
				result.put(i, tempImage);				
			}///end of for 
		
			
		BufferedImage bimg=getP2P(result.get(1), testPoint, null, null);
		
		
		return bimg;
	}
	
	/**
	 * ���˹ؼ��㣬�õ������ȶ��������㡪������������������ȥ���Աȶȵͣ�����ͱ�Ե�㣨hessian����ȥ��Ե�㣩
	 * @param dogPyramid
	 * @param keyPoints
	 * @param r  ��Lowe�������У�ȡr��10��ͼ4.2�Ҳ�Ϊ������Ե��Ӧ��Ĺؼ���ֲ�ͼ��
	 * @param contrast �Աȶ���ֵ
	 * @return
	 */
	public static HashMap<Integer, List<MyPoint>>  filterPoints(HashMap<Integer,double[][]> dogPyramid,
			HashMap<Integer, List<MyPoint>> keyPoints,int r,double contrast ){
		
		HashMap<Integer, List<MyPoint>> resultMap=new HashMap<Integer, List<MyPoint>>();         
//		Set<Integer> gaussIndex=gaussPyramid.keySet();
		Set<Integer> pSet=keyPoints.keySet();
//		int length=pSet.size()-1;
		for(int i:pSet){
			List<MyPoint> points=keyPoints.get(i);
			List<MyPoint> resultPoints=new ArrayList<MyPoint>();
			///��ȡ��Ӧ��dogͼ��
			double[][] gaussImage=dogPyramid.get(i);
			for(MyPoint mp:points){
				int x=mp.getX();
				int y=mp.getY();
				////�Լ�ֵ����о�ȷ��λ
				/*mp=adjustLocation(dogPyramid, mp, 6, 6);
				if(null==mp){
					///�����ȷ��λ����null���������õ�
					continue;
				}
				x=mp.getX();
				y=mp.getY();*/
				
				double xy00=gaussImage[y-1][x-1];
				double xy01=gaussImage[y-1][x];
				double xy02=gaussImage[y-1][x+1];
				double xy10=gaussImage[y][x-1];
				double xy11=gaussImage[y][x];
				double xy12=gaussImage[y][x+1];
				double xy20=gaussImage[y+1][x-1];
				double xy21=gaussImage[y+1][x];
				double xy22=gaussImage[y+1][x+1];
				
				double dxx=xy10+xy12-2*xy11;
				double dyy=xy01+xy21-2*xy11;
				double dxy=(xy22-xy20)-(xy02-xy00);
				///hessian����ĶԽ���ֵ������ʽ
				double trH=dxx+dyy;
				double detH=dxx*dyy;//-dxy*dxy;
				
				///����ľ�ֵ
				double avg=(xy00+xy01+xy02+xy10+xy11+xy12+xy20+xy21+xy22)/9;
				///���򷽲�
				double DX=(xy00-avg)*(xy00-avg)+(xy01-avg)*(xy01-avg)+(xy02-avg)*(xy02-avg)+(xy10-avg)*(xy10-avg)+
						(xy11-avg)*(xy11-avg)+(xy12-avg)*(xy12-avg)+(xy20-avg)*(xy20-avg)+(xy21-avg)*(xy21-avg)+(xy22-avg)*(xy22-avg);
				DX=DX/9;
				
				double threshold=(double)(r+1)*(r+1)/r;
				if((detH>0&&(trH*trH)/detH<threshold)&&(DX>=contrast)){
					///������С����ֵ��������Ҫ�޳��ı�Ե��Ӧ��;�������0.03��Ϊ�߶Աȶ�
					resultPoints.add(mp);
				}
				
				//System.out.println(DX);
				
				
			}///end of inner for
			
			
			resultMap.put(i, resultPoints);
		}
		
		
		return resultMap;
	
	}
	
	/**
	 * ��ȷ��λ��ֵ��
	 * @param dogPyramid
	 * @param keyPoint
	 * @return
	 */
	private static  MyPoint adjustLocation(HashMap<Integer,double[][]> dogPyramid,MyPoint kpoint,int MAX_STEP,int nLayer){
		
		int index=kpoint.getOctave()*nLayer+kpoint.getS();
		int octave=kpoint.getOctave();
		int x=kpoint.getX();
		int y=kpoint.getY();
		int layer=kpoint.getS();
		
		double offL=0,offY=0,offX = 0;
		
		
		///��max_step���������
		int i=0;
		for(;i<MAX_STEP;i++){

			double[][] img=dogPyramid.get(index);
			double[][] pre=dogPyramid.get(index-1);
			double[][] next=dogPyramid.get(index+1);
			
			int width=img[0].length;
			int height=img.length;			
			if(pre!=null&&next!=null&&y>1&&x>1&&y<height-2&&x<width-2){	
				///��ά�ռ���ݶ�����
				double[] dvector=new double[3];
				///��ά�ݶȾ���
				double[][] dMatrix=new double[3][3];
				///��ֵ
				dvector[0]=(img[y][x+1]-img[y][x-1])/2;
				dvector[1]=(img[y+1][x]-img[y+1][x])/2;
				dvector[2]=(next[y][x]-pre[y][x])/2;
				/**
				 * dxx, dxy, dxs,  
	               dxy, dyy, dys,  
	               dxs, dys, dss
				 */
				double dxx=img[y][x+1]+img[y][x+1]-2*img[y][x];
				double dxy=(img[y+1][x+1]-img[y+1][x-1]-(img[y-1][x+1]-img[y-1][x-1]))/4;
				double dxs=(next[y][x+1]-next[y][x-1]-(pre[y][x+1]-pre[y][x-1]))/4;
				double dyy=img[y+1][x]+img[y][x+1]-2*img[y][x];
				double dys=(next[y+1][x]-next[y-1][x]-(pre[y+1][x]-pre[y-1][x]))/4;
				double dss=next[y][x]-next[y][x]-2*img[y][x];
				
				dMatrix[0][0]=dxx;
				dMatrix[0][1]=dxy;
				dMatrix[0][2]=dxs;
				dMatrix[1][0]=dxy;
				dMatrix[1][1]=dyy;
				dMatrix[1][2]=dys;
				dMatrix[2][0]=dxs;
				dMatrix[2][1]=dys;
				dMatrix[2][2]=dss;
				///ƫ��������
				double[] offset=new double[3];
				offset=Math_Utility.get3Inv_Vector(dMatrix, dvector);
				
				offL=-offset[2];
				offY=-offset[1];
				offX=-offset[0];
				
				if((Math.abs(offL)<0.5&&Math.abs(offY)<0.5&&Math.abs(offX)<0.5)){
					///����Ѿ�����
					break;
				}
				
				layer=layer+Math.round((float)offL);
				y=y+Math.round((float)offY);
				x=x+Math.round((float)offX);
				
				if(layer<1||layer>nLayer||x<1||x>width-1||y<1||y>height-1){
					///���Խ�磬�򷵻�null
					return null;
				}				
			}
			
		}//end of for 
		
		if(i>=MAX_STEP){
			///�����ͨ��forѭ�������������������������õ㣻
			return null;
		}
		
		///�޸Ĺؼ������ά�߶�����
		kpoint.setPreX((int)(x+offX)*(1<<octave));
		kpoint.setPreY((int)(y+offY)*(1<<octave));
		kpoint.setX(x);
		kpoint.setY(y);
		kpoint.setS(layer);
		
		return kpoint;
	}
	
	
	
	
	
	/**
	 *  *���ظø�˹ͼ���������������:�˴����ùؼ�����Χ�����̾�����Ϊ����Բ�뾶���в���������ֱ�Ϊ2��4��6����,Ȼ����ݶȷ�����24�����ͳ�ƣ�
	 *   ���õ�largestDistance/2x15ά���������������ӣ�Ȼ������ͳ������������*
	 * @param image  ͼ��
	 * @param keyPoints	��ͼ���Ӧ�������㼯��
	 * @param largestDistances ���̾���������� ��8�����
	 * @return
	 */
	private static List<MyPoint> getFeatureVector(double[][] image,List<MyPoint> keyPoints,int nLayer,double baseSigma,int largestDistance){
		List<MyPoint> vectorList=new ArrayList<MyPoint>();///��������������list
		//int keyR=0.6*Math.pow(2, i+(double)j/gaussS);
		if(keyPoints.isEmpty()){
			return null;
		}
		int s=keyPoints.get(0).getS();
		int octave=keyPoints.get(0).getOctave();
		
		
		///int n=0;///n������¼��������
		for(MyPoint mp:keyPoints){
			int x=mp.getX();
			int y=mp.getY();
			
			int width=image[0].length;
			int height=image.length;
			double y2=image[y+1][x];
			double y1=image[y-1][x];
			double x2=image[y][x+1];
			double x1=image[y][x-1];
			
			//�ؼ����ݶ�ģֵ
//			double m=Math.sqrt((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
			//�ؼ����ݶȷ���,תΪ��0~360��֮��ĽǶ�ֵ
			double theta=Math.atan2(y2-y1, x2-x1)*(180/Math.PI)+180;
			
			//ͳ�Ƹ�������һ����Χ�ڵ�36�������ģֵ�ͽǶȷֲ����
			double[] keyTM=new double[45];
			double[] keyAngle=new double[45];
			double[]angleRatio=new double[45];////�ñ���������¼������Χ�ڣ���0~10�������е�ĸ�˹�˵ı�����������keyAngle���Զ�Ӧ�ı����͵õ���������ĸ�˹��Ȩ����


			double max=0;///��¼���ģֵ
			int index = 0;			
			
			
			
			double s_oct=baseSigma*Math.pow(2, (double)mp.getS()/nLayer);
			double sigma=1.5*s_oct;////��˹ģ����
			double[][] gtemplate=getGaussTemplate(sigma);////���ڶ�ģֵ���и�˹��Ȩ
			int radius=(int) (3*sigma);///��������뾶
			int diameter=2*radius;
			
			int gtCenter=gtemplate.length/2;
			if(x>=diameter&&x<width-diameter&&y>=diameter&&y<height-diameter){	
				///sigma=9
				for(int j=0;j<=2*radius;j++ ){
					for(int i=0;i<=2*radius;i++){
						/*if((j==radius)&&(i==radius)){
							continue;
						}*/						
						double ty2=image[y-radius+j+1][x-radius+i];
						double ty1=image[y-radius+j-1][x-radius+i];
						double tx2=image[y-radius+j][x-radius+i+1];
						double tx1=image[y-radius+j][x-radius+i-1];
						//���ݶ�ģֵ
						double tM=Math.sqrt((ty2-ty1)*(ty2-ty1)+(tx2-tx1)*(tx2-tx1));
						//�ݶȷ���תΪ��0~360��֮��ĽǶ�ֵ
						double tTheta=Math.atan2(ty2-ty1, tx2-tx1)*(180/Math.PI)+180;					
						int section=(int) (tTheta/9);		
						if(360-Math.abs(tTheta)<0.0001){
							///����Ƕ�Ϊ360�㣬�����һ�����ڵ�һ��һ������
							section=0;
						}
						keyTM[section]=keyTM[section]+tM*gtemplate[gtCenter-radius+j][gtCenter-radius+i];
						keyAngle[section]=keyAngle[section]+tTheta*gtemplate[gtCenter-radius+j][gtCenter-radius+i];////������������������Ƕȹ���;
						angleRatio[section]+=gtemplate[gtCenter-radius+j][gtCenter-radius+i];
					}				
				}
				
				for(int key=0;key<keyTM.length;key++){
					if(keyTM[key]>max){
						max=keyTM[key];
						index=key;
					}
				}
				theta=keyAngle[index]/angleRatio[index];
			}
			

			
			
			

			///�Թؼ�����ж�������򣬾͸��Ƴɶ���ؼ���
			for(int key=0;key<keyTM.length;key++){
				if(keyTM[key]>max*0.8){
					///�������ֵ��80%����Ϊ������֮һ�����Ƴ�һ���ؼ���
					theta=keyAngle[key]/angleRatio[key];////��ø�����
				//	System.out.println("theta:"+theta);
			
			///����ÿ������Բ�ڵ��ݶȷ���ֲ�
			if(x>=largestDistance+1&&x<width-1-largestDistance&&y>=largestDistance+1&&y<height-1-largestDistance){	
				
				int secNum=15;//ÿ������Բ�ڶ�������
				int secAngle=360/secNum;///���Ľ�Ϊһ������
				double[] grads=new double[secNum*(largestDistance/2)];///�����ά����������
				
				double sum=0;
				for(int j=y-largestDistance;j<=y+largestDistance;j++){
					for(int i=x-largestDistance;i<=x+largestDistance;i++){	
						
						if((j==y)&&(i==x)){
							continue;
						}
						
						double ty2=image[j+1][i];
						double ty1=image[j-1][i];
						double tx2=image[j][i+1];
						double tx1=image[j][i-1];
						
						//tx1=tx1*Math.cos(theta)-txy1
						//�ݶ�ģֵ
						double tM=Math.sqrt((ty2-ty1)*(ty2-ty1)+(tx2-tx1)*(tx2-tx1));
						sum=sum+tM;///���ۼ�ģֵ�����ں����һ��
						//�ݶȷ���תΪ��0~360��֮��ĽǶ�ֵ
						double tTheta=Math.atan2(ty2-ty1, tx2-tx1)*(180/Math.PI)+180;
						///��ȥ�ؼ���ķ���ȡ����Է��򣡣� �˴�Ҫ��Ҫȡ����ֵ��������360-Math.abs(tTheta-theta)�ȣ���
						double absTheta=tTheta-theta;
						///��Ϊ�õ���theta�Ľ����-pi��+pi֮��
						
						int section=(int) (absTheta/secAngle);	
						if(360-Math.abs(absTheta)<0.0001){
							///����Ƕ�Ϊ360�㣬�����һ�����ڵ�һ��һ������
							section=0;
						}
						if(section<0){
							///����Ƕ�Ϊ����Ӧ�ü�һ��secNUm	תΪ����
							section=section+secNum;
						}
						///�������̾���
						int distance=Math.max(Math.abs(y-j),Math.abs(x-i));	
					
						if(distance<=2){
							///����ھ���2�����̾�����
							///�ݶ�ģֵ�ۼӣ���
							grads[section]=grads[section]+tM;							
						}else if(distance<=4){
							///
							///�ݶ�ģֵ�ۼӣ���
							grads[section+1*secNum]=grads[section+1*secNum]+tM;							
						}else if(distance<=6){
							///�ݶ�ģֵ�ۼӣ���
							grads[section+2*secNum]=grads[section+2*secNum]+tM;							
						}else if(distance<=8){
							///�ݶ�ģֵ�ۼӣ���
							grads[section+3*secNum]=grads[section+3*secNum]+tM;							
						}else if(distance<=10){
							///�ݶ�ģֵ�ۼӣ���
							grads[section+4*secNum]=grads[section+4*secNum]+tM;							
						}			
						
					}					
				}//// end  of  outer for  clause			
				///��һ������
				grads=Math_Utility.normalize(grads, sum);
				///�洢��mypoint������
				MyPoint rmp=new MyPoint(x, y, s, octave, theta, grads,false);				
				vectorList.add(rmp);
			
			}
				}}
		} 
		
		return vectorList;
	}
	
	
	
	
	/**
	 * ��ȡĳ�Ÿ�˹ͼ���ͼƬ������������������
	 * @param gaussPyramid
	 * @param keyPoints
	 * @return
	 */
	private static  List<MyPoint> getVectors(HashMap<Integer,double[][]> gaussPyramid,HashMap<Integer, List<MyPoint>> keyPoints){
	
		 List<MyPoint> testPoint=new ArrayList<MyPoint>();		 
		 ////��ȡ��˹�����������ͼ��
		Set<Integer> iset=gaussPyramid.keySet();
		int length=iset.size();
		for(int i=0;i<length;i++){
			
			 double[][] tempImage =gaussPyramid.get(i);///��ʱͼ��			
			 List<MyPoint> imagePoint=keyPoints.get(i);
			 
			 if(null!=imagePoint){
				 
				 	///��ȡ�����㣬���Ƚ�				 
					List<MyPoint> vector=getFeatureVector(tempImage, imagePoint,6,1.6,8);
					if(vector==null){
						continue;
					}			
					testPoint.addAll(vector);
			 }
			 
		}///end of for 
		
		return testPoint;
	}
	
	
	

	
	
	/**
	 * �����������������Ŀ
	 * ����������ļнǺ�ŷʽ����
	 * @param sourceArray
	 * @param vectors1
	 * @param targetArray
	 * @param vectors2
	 * @return
	 */
	public static int getSimilarPointsNum(List<MyPoint> vectors1,List<MyPoint> vectors2){
		
		
		int similarNum=0;
		
		List<MyPoint> vt1=vectors1;
		List<MyPoint> vt2=vectors2;
	
		MyPoint mp1=null;
		int index1=0;
		for(;index1<vt1.size();index1++){			
			mp1=vt1.get(index1);
			if(mp1.isMatched()==true){
				//ƥ����ĵ㲻�ټ���ƥ��
				continue;
			}
			
			double minED=10;///��Сŷʽ����
			double sMinED=0;///��Сŷʽ����
			
			MyPoint mp2=null;
			//double theta2=0;
			int index2=0;
			for(;index2<vt2.size();index2++){

				mp2=vt2.get(index2);
				if(mp2.isMatched()==true){
					//ƥ����ĵ㲻�ټ���ƥ��
					continue;
				}
				
				double tempED=0.0;
				double[] v1=mp1.getGrads();
				double[] v2=mp2.getGrads();
				for(int i=0;i<v1.length;i++){
					tempED=tempED+(v1[i]-v2[i])*(v1[i]-v2[i]);
				}
				tempED=Math.sqrt(tempED);///�����ŷʽ����
				if(tempED<minED){
					sMinED=minED;
					minED=tempED;
				}
			}
			
			/**
			 * ratio=0. 4������׼ȷ��Ҫ��ߵ�ƥ�䣻
			 * ratio=0. 6������ƥ�����ĿҪ��Ƚ϶��ƥ�䣻 
			 * ratio=0. 5��һ������¡�
			 */
			if(minED/sMinED>0.8||minED>0.1){
				////����̫��������
				continue;
			}
			mp1.setMatched(true);
			mp2.setMatched(true);
			
			vt1.set(index1, mp1);
			vt2.set(index2-1, mp2);			
		
			similarNum++;///ƥ���ϵĵ���Ŀ��һ  
		}
		
		
		return similarNum;
	} 
	
	
	
	
	
	
	/**
	 * �ҳ���Ӧ�㣬������
	 * ����������ļнǺ�ŷʽ����
	 * @param sourceArray
	 * @return
	 */
	public static BufferedImage getP2P(double[][] sourceArray,List<MyPoint> vectors1,double[][] targetArray,List<MyPoint> vectors2){
		
		BufferedImage resultImage= Image_Utility.doubleArrayToGreyImage(sourceArray);//new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	 
		List<MyPoint> vt1=vectors1;
		List<MyPoint> vt2=vectors1;
		
		
			
		MyPoint mp1=null;
		int index1=0;
		for(;index1<vt1.size();index1++){
			
			mp1=vt1.get(index1);
			
			mp1.setPreX(mp1.getX()*(int)Math.pow(2, mp1.getOctave()));
			mp1.setPreY(mp1.getY()*(int)Math.pow(2, mp1.getOctave()));
			
			
			if(mp1.getPreX()>sourceArray[0].length*2/3||(mp1.isMatched()==true)){
				continue;
			}
			double minED=10;///��Сŷʽ����
			double sMinED=0;///��Сŷʽ����
			int x2 = 0;
			int y2 = 0;
			MyPoint mp2=null;
			//double theta2=0;
			int index2=0;
			for(;index2<vt2.size();index2++){

				mp2=vt2.get(index2);
				
				mp2.setPreX(mp2.getX()*(int)Math.pow(2, mp2.getOctave()));
				mp2.setPreY(mp2.getY()*(int)Math.pow(2, mp2.getOctave()));
				///�����Լ��Ƚ�
				/*if(mp2.getX()==mp1.getX()||(mp2.isMatched()==true)||(mp2.getX()<sourceArray[0].length/2)){
					continue;
				}*/
				if(mp2.getPreX()==mp1.getPreX()||(mp2.isMatched()==true)||(mp2.getPreX()<sourceArray[0].length*2/3)){
					continue;
				}
				
				double tempED=0.0;
				double[] v1=mp1.getGrads();
				double[] v2=mp2.getGrads();
				for(int i=0;i<v1.length;i++){
					tempED=tempED+(v1[i]-v2[i])*(v1[i]-v2[i]);
				}
				tempED=Math.sqrt(tempED);///�����ŷʽ����
				if(tempED<minED){
					sMinED=minED;
					minED=tempED;
					x2=mp2.getX();
					y2=mp2.getY();
					
					x2=mp2.getPreX();
					y2=mp2.getPreY();
					//theta2=mp2.getTheta();
				}
			}
			
			//System.out.println(minED/sMinED+"������"+minED);
			
			
			/**
			 * ratio=0. 4������׼ȷ��Ҫ��ߵ�ƥ�䣻
			 * ratio=0. 6������ƥ�����ĿҪ��Ƚ϶��ƥ�䣻 
			 * ratio=0. 5��һ������¡�
			 */
			if(minED/sMinED>0.4||minED>0.1){
				////����̫��������
				continue;
			}
//			System.out.println(minED/sMinED+"������"+minED);
			
			
			mp1.setMatched(true);
			mp2.setMatched(true);
			
			vt1.set(index1, mp1);
			vt1.set(index2-1, mp2);
			vt2.set(index1, mp1);
			vt2.set(index2-1, mp2);
			
			Graphics2D g= resultImage.createGraphics();
			g.setColor(Color.black);
			//g.setPaint(Color.magenta);
			//g.setBackground(Color.black);
			g.setStroke(new BasicStroke(4));//����������ϸ
			
			/*g.drawLine(mp1.getPreX(), mp1.getPreY(),(int)( mp1.getPreX()+40*Math.cos(mp1.getTheta())),(int)( mp1.getPreY()+40*Math.sin(mp1.getTheta())));
			g.drawLine(x2, y2,(int)( x2+40*Math.cos(theta2)),(int)( y2+40*Math.sin(theta2)));*/
			g.drawLine(mp1.getPreX(), mp1.getPreY(), x2, y2);
			
			/*g.drawLine((int)(mp1.getX()*Math.pow(2, mp1.getOctave())), 
					(int)(mp1.getY()*Math.pow(2, mp1.getOctave())), 
					(int)(x2*Math.pow(2, mp2.getOctave())),
					(int)(y2*Math.pow(2, mp2.getOctave())));*/
			
			resultImage.setRGB(x2, y2,255<<16);resultImage.setRGB(x2+1, y2+1,255<<16);
			resultImage.setRGB(mp1.getPreX(), mp1.getPreY(),255<<16);resultImage.setRGB(mp1.getPreX()+1, mp1.getPreY()+1,255<<16);
			
			///mp1.set
		}
		
		
		return resultImage;
	}
	
	
	 
	/**
	 * ��ȡ����ͼƬ���������������ļ���
	 * @param img1
	 * @return
	 */
	public static List<MyPoint> getCharacterVectors(BufferedImage img){
		
		double start=System.currentTimeMillis();
		///���ɸ�˹������
		HashMap<Integer,double[][]> result=ImageTransform.getGaussPyramid(Image_Utility.imageToDoubleArray(img), 20, 3,1.6);
		double end=System.currentTimeMillis();
		System.out.println("��˹��������ʱ��"+(end-start));
		
		//����dog������
		HashMap<Integer,double[][]> dog=ImageTransform.gaussToDog(result, 6);
		//������ȡ������
		HashMap<Integer,List<MyPoint>> keyPoints=ImageTransform.getRoughKeyPoint(dog,6);
		///���������
		keyPoints=ImageTransform.filterPoints(dog, keyPoints, 10,0.03);
		
		List<MyPoint> vctors=getVectors(result, keyPoints);
		
		return vctors;
	}
	
	
	
	
	}