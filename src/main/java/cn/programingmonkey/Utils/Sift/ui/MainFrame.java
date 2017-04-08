package cn.programingmonkey.Utils.Sift.ui;

import cn.programingmonkey.Utils.Sift.ImageTransform;
import cn.programingmonkey.Utils.Sift.MyPoint;
import cn.programingmonkey.Utils.Sift.utility.Image_Utility;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainFrame extends JFrame{
	
	
	public MainFrame(String sourcePath,String targetPath) {
		// TODO Auto-generated constructor stub
		this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-200,
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200);
		this.setLocation(100, 100);

		JPanel p=new JPanel();

		JLabel l1=new JLabel("1");
		JLabel l2=new JLabel("2");
		JLabel l3=new JLabel("3");
		JLabel l4=new JLabel("4");

		l1.setSize(30,50);
		l2.setSize(30,50);
		l3.setSize(30,50);
		l4.setSize(30,50);

		l1.setSize(30,50);
		JLabel l5=new JLabel("5");
		l1.setSize(30,50);
		JLabel l6=new JLabel("6");


		///�Ҷ�ͼ��
		BufferedImage sourceImage=grayTran(sourcePath);
		BufferedImage targetImage=grayTran(targetPath);
		//sourceImage=targetImage;

		////��˹ģ������ʾ
		//sourceImage=Image_Utility.doubleArrayToGreyImage(ImageTransform.gaussTran(Image_Utility.imageToDoubleArray(sourceImage), 2));

		HashMap<Integer,double[][]> result= ImageTransform.getGaussPyramid(Image_Utility.imageToDoubleArray(sourceImage), 20, 3,1.6);


		HashMap<Integer,double[][]> dog=ImageTransform.gaussToDog(result, 6);
		HashMap<Integer,List<MyPoint>> keyPoints=ImageTransform.getRoughKeyPoint(dog,6);
		keyPoints=ImageTransform.filterPoints(dog, keyPoints, 10,0.03);
		sourceImage=ImageTransform.drawPoints(result,keyPoints);


        HashMap<Integer,double[][]> result1= ImageTransform.getGaussPyramid(Image_Utility.imageToDoubleArray(targetImage), 20, 3,1.6);

        HashMap<Integer,double[][]> dog1=ImageTransform.gaussToDog(result, 6);
        HashMap<Integer,List<MyPoint>> keyPoints1=ImageTransform.getRoughKeyPoint(dog1,6);
        keyPoints1=ImageTransform.filterPoints(dog, keyPoints1, 10,0.03);
        sourceImage=ImageTransform.drawPoints(result1,keyPoints1);

		 List<MyPoint> v1=ImageTransform.getCharacterVectors(sourceImage);
		 List<MyPoint> v2=ImageTransform.getCharacterVectors(targetImage);


		 int num=ImageTransform.getSimilarPointsNum(v1, v2);
		 System.out.println("���������ֱ�Ϊ��"+v1.size()+"&"+v2.size()+"  ���Ƶ���Ϊ��"+num);

		sourceImage=Image_Utility.arrayToGreyImage(Image_Utility.open(Image_Utility.imageToArray(sourceImage),20));
		targetImage=Image_Utility.arrayToGreyImage(Image_Utility.open(Image_Utility.imageToArray(targetImage),60));

		 sourceImage=Image_Utility.doubleArrayToGreyImage(dog.get(1));
		 sourceImage=Image_Utility.arrayToGreyImage(Image_Utility.dilate(Image_Utility.imageToArray(sourceImage),20));
	 	//sourceImage=Image_Utility.guassFilter(sourceImage);

		sourceImage=Image_Utility.sobleTran(sourceImage,100);
		sourceImage=Image_Utility.xyTran(sourceImage,12);
		//sourceImage=shrinkToSize(sourceImage, 200, 200);
		sourceImage=Image_Utility.arrayToGreyImage(Image_Utility.open(Image_Utility.imageToArray(sourceImage),20));
		//targetImage=Image_Utility.sobleTran(sourceImage,100);
		//targetImage=shrinkToSize(targetImage, 200, 200);
		//15907133678
		//start(greyHistogram(sourceImage), greyHistogram(targetImage));
		//sourceImage=getBlock(targetImage, sourceImage, 12);

		sourceImage=Image_Utility.arrayToGreyImage(Image_Utility.dilate(Image_Utility.imageToArray(sourceImage),20));
		sourceImage=Image_Utility.arrayToGreyImage(Image_Utility.correde(Image_Utility.imageToArray(sourceImage),20));

		///��ʾ
		l1.setIcon(new ImageIcon(sourceImage));
		//l2.setIcon(new ImageIcon(targetImage));

		l3.setIcon(new ImageIcon(Image_Utility.doubleArrayToGreyImage(result.get(1))));
		l4.setIcon(new ImageIcon(Image_Utility.doubleArrayToGreyImage(result.get(2))));
		l5.setIcon(new ImageIcon(Image_Utility.doubleArrayToGreyImage(result.get(3))));
		l6.setIcon(new ImageIcon(Image_Utility.doubleArrayToGreyImage(result.get(8))));
		p.add(l1);
		p.add(l2);
		p.add(l3);
		p.add(l4);
		p.add(l5);
		p.add(l6);

		this.add(p);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);////�������ٰ�
	}


	public BufferedImage getBlock(BufferedImage source,BufferedImage edge,int threshold){
		int width=source.getWidth();
		int height=source.getHeight();
		BufferedImage resultImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for(int j=0;j<height;j++){
			for(int i=0;i<width;i++){
				int rgb=edge.getRGB(i, j);
				int grey=(rgb>>16)&0xFF;
				if(grey>threshold){
					resultImage.setRGB(i, j, 0);
				}else{
					resultImage.setRGB(i, j, source.getRGB(i, j));
				}
//				System.out.println(grey);


			}
		}


		return resultImage;
	}



	/**
	 * �Ҷȴ�����ȡ�Ҷ�ͼ��
	 * @param imagePath
	 * @return
	 */
	public BufferedImage grayTran(String imagePath){

		BufferedImage bimg=null;
		try {
			bimg = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int width=bimg.getWidth();
		int height=bimg.getHeight();
	/*	double sx = (double) width / bimg.getWidth();///�˴�����Ϊdouble ������õ���sxΪ0�����½��Ϊȫ��ͼ
		double sy = (double) height / bimg.getHeight();

	 	int type = bimg.getType();*/
		BufferedImage targetImage = null;

		/*if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = bimg.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,
					height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			targetImage = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else*/
			targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//BufferedImage.TYPE_BYTE_BINARY);
		/*Graphics2D g = targetImage.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(bimg, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		*/


		for(int j=0;j<height;j++){
			for(int i=0;i<width;i++){
				int rgb=bimg.getRGB(i, j);
				/*if(i>=1&&j>=1){
				rgb=bimg.getRGB(i-1, j-1)+bimg.getRGB(i-1, j)+bimg.getRGB(i, j-1);
				rgb=rgb/4;}*/
				int c_red=(rgb>>16)&0xFF;
				int c_green=(rgb>>8)&0xFF;
				int c_blue=rgb&0xFF;
				int grayRGB=(int) (0.3 * c_red + 0.59 * c_green + 0.11 * c_blue);////�ҶȻ�



				rgb=(255<<24)|(grayRGB<<16)|(grayRGB<<8)|grayRGB;///�ҶȻ��ָ�

				targetImage.setRGB(i, j, rgb);

				/*if(grayRGB<180){
					grayRGB=0;
				}else{
					grayRGB=255;
				}/////��ֵ��ͼ��
				rgb=(255<<24)|(grayRGB<<16)|(grayRGB<<8)|grayRGB;///�ҶȻ�����

				targetImage.setRGB(i, j, rgb);*/

			/*	if(i>0&&j>0&&j<height-1&&(Math.abs(bimg.getRGB(i, j)-bimg.getRGB(i, j-1))+Math.abs(bimg.getRGB(i, j)-bimg.getRGB(i, j+1))>20000)){
					rgb=rgb+20000;
				}

				*/
				/*if(i>0&&j>0&&j<height-1&i<width-1){
					grayRGB=Math.abs(bimg.getRGB(i, j)-bimg.getRGB(i, j-1))+Math.abs(bimg.getRGB(i, j)-bimg.getRGB(i+1, j));
					if(grayRGB+120<255){
						if(grayRGB>=125){
							grayRGB+=125;
						}
					}else{
						grayRGB=0xFFFFFF;
					}
				}
				rgb=(grayRGB<<16)|(grayRGB<<8)|grayRGB;///��ֵ������
*/
//				rgb=255-rgb;////��ɫ

			}
		}


		//Canvas



		/* for (int i=0;i<targetImage.getWidth();i++){
	    	  for(int j=0;j<targetImage.getHeight();j++){
	    		  System.out.print(""+targetImage.getRGB(i,j));
	    	  }

	      }
		 */

		return targetImage;

	}




	/**
	 * ����ԴͼƬ��Ŀ��ͼƬ��С
	 * @param sourceImage
	 * @param width Ŀ��ͼ���
	 * @param height Ŀ��ͼ���
	 * @return  ��С���ͼ��
	 */
	public BufferedImage shrinkToSize(BufferedImage sourceImage,int width,int height){

		BufferedImage targetImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		double widthStep=(double)sourceImage.getWidth()/width;///���������
		double heightStep=(double)sourceImage.getHeight()/height;//�߱�������


		for(int j=0;j<height;j++){//��
			for(int i=0;i<width;i++){//��
				///���и��㲽���������������ڴ������ŵ�Դͼ���o.5����1��֮���Ҫ��
				int x=(int)(i*widthStep);
				int y=(int)(j*heightStep);
				/*
				if(widthStep-x>0.3){
					x=x+1;
				}
				if(heightStep-y>=0.3){
					y=y+1;
				}*/

				int rgb=sourceImage.getRGB(x, y);////+sourceImage.getRGB(x,y+1)+sourceImage.getRGB(x+1,y);
//				rgb=rgb/3;
				targetImage.setRGB(i, j, rgb);
			}
		}

		return targetImage;
	}


	/**
	 * ��ȡ�Ҷ���������:����ÿ���Ҷ�ֵ�����ص�
	 *
	 * @param bufferedImage
	 * @return
	 */
	public int[] greyHistogram(BufferedImage bufferedImage){

		int width=bufferedImage.getWidth();
		int height=bufferedImage.getHeight();

		int featureArray[]=new int[256];
		for(int j=0;j<height;j++){
			for(int i=0;i<width;i++){
				int rgb=bufferedImage.getRGB(i, j);
				int grey=(rgb>>16)&0xFF;
//				System.out.println(grey);
				featureArray[grey]=featureArray[grey]+1;

			}
		}
		 return featureArray;
	}


	/**
	 *
	 * @param source
	 * @param targets
	 */
	public void start(int[] source,int[]targets){

		int length=targets.length;///���Ƚ�ͼ����Ŀ
//		for(int i=0;i<length;i++){
			double similarity=calSimilarity(source, targets);
			System.out.println(similarity);
//		}

	}

	/**
	 * �Ƚ����ƶȣ����ü���ƽ��ֵ��С��
	 * @param source
	 * @param target
	 */
	public double calSimilarity(int[] source,int[] target){
		int length=source.length;
		double min=0,max=0;
		for(int i=0;i<length;i++){
			if(source[i]>target[i]){
				max=max+Math.sqrt(source[i]*target[i]);
				min=min+target[i];
			}else{
				max=max+Math.sqrt(source[i]*target[i]);
				min=min+source[i];
			}
		}

		return (double)min/max;

//		return Math_Utility.correlationIndex(source, target);

	}



}
