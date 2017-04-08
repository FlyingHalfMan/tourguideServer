package cn.programingmonkey.Utils.Sift.utility;

/**
 * ��ѧ������
 * @author Administrator
 *
 */
public  class Math_Utility {

	/**
	 * ��С���˷�������� 	y=a0+a1*x
	 * @param x 
	 * @param y
	 * @param �� x ,y Ϊһ�������ݶԣ������ά������ĸ������x��yֵ
	 * @return int[] ab,������Ͻ���Ĳ���a��b
	 */
	public  static double[] leastSquares(int[] x,int[] y){
		double[] ab=new double[2];
		int n=x.length;
		
		int sumY=0;
		int sumX=0;
		int sumXY=0;
		int sumX2=0;
		
		for(int i=0;i<n;i++){
			sumY=sumY+y[i];
			sumX+=x[i];
			sumXY+=x[i]*y[i];
			sumX2+=x[i]*x[i];
		}
		
		double a1=n*sumXY-(sumX*sumY)/(n*sumX2-sumX*sumX);
		double a0=(sumY-a1*sumX)/n;
		
		return ab;
	}
	
	
	/**
	 * �������ϵ��
	 * @param x
	 * @param y
	 * @return 
	 */
	public static double correlationIndex(int[]x ,int[] y){
		double correlation=0.0;
		int n=x.length;
		
		int sumX=0;
		int sumY=0;
		int sumXY=0;
		int sumX2=0;
		int sumY2=0;
		
		for(int i=0;i<n;i++){
			sumY=sumY+y[i];
			sumX+=x[i];
			sumXY+=x[i]*y[i];
			sumX2+=x[i]*x[i];
			sumY2+=y[i]*y[i];
		}
		/*double tempX=n*sumX2-sumX*sumX;
		double tempY=n*sumY2-sumY*sumY;
		correlation=(n*sumXY-sumX*sumY)/Math.sqrt(tempX*tempY);
		///������㷽��Ϊʲô�Ǵ�ģ�����
		*/
		
		double Lxy=sumXY-(sumX*sumY)/n;
		double Lxx=sumX2-(sumX*sumX)/n;
		double Lyy=sumY2-(sumY*sumY)/n;
		
		correlation=Lyy/(Math.sqrt(Lxx*Lxy));
		return correlation;
	}
	
	
	/**
	 * ��ȡ3ά����������
	 * @return
	 */
	public static double[][] get33Inv(double[][] m){
		double[][] result=new double[3][3];
		
		result[0][0]=m[1][1]*m[2][2]-m[2][1]*m[1][2];
		result[0][1]=m[2][1]*m[0][2]-m[0][1]*m[2][2];
		result[0][2]=m[0][1]*m[1][2]-m[0][2]*m[1][1];
		result[1][0]=m[1][2]*m[2][0]-m[2][2]*m[1][0];
		result[1][1]=m[2][2]*m[0][0]-m[2][0]*m[0][2];
		result[1][2]=m[0][2]*m[1][0]-m[0][0]*m[1][2];
		result[2][0]=m[1][0]*m[2][1]-m[2][0]*m[1][0];
		result[2][1]=m[2][0]*m[0][1]-m[0][0]*m[2][1];
		result[2][2]=m[0][0]*m[1][1]-m[0][1]*m[1][0];
				
		return result;
	}
	
	/**
	 * ��ȡ3ά����������    ��   ����������˵Ľ��
	 * @return
	 */
	public static double[] get3Inv_Vector(double[][] m,double[] v){
		double[] result=new double[3];
		//�Ƚ���ת�ô���
		m=get33Inv(m);
		
		result[0]=m[0][0]*v[0]+m[0][1]*v[1]+m[0][2]*v[2];
		result[0]=m[1][0]*v[0]+m[1][1]*v[1]+m[1][2]*v[2];
		result[0]=m[2][0]*v[0]+m[2][1]*v[1]+m[2][2]*v[2];
		
				
		return result;
	}
	
	
	
	
	/**
	 * ��һ������
	 * @param source
	 * @return
	 */
	public static double[] normalize(double[] source,double sum){
		double[] result=new double[source.length];
		for(int i=0;i<source.length;i++){
			result[i]=source[i]/sum;
			if(result[i]<0.0001){
				result[i]=0;
			}
		}
		
		
		return result;
	}
	
	
	/**
	 * �ж�keyValue�Ƿ���values���������ڼ�ֵ
	 * @param values
	 * @param keyValue
	 * @return
	 */
	public static boolean isExtremeVlaue(double[] values,double keyValue){
	
		if(keyValue>values[0]+0.001){
			///�˴���ʾֻ�����Ǽ���ֵ
			for(double v:values){
				///�˴�������Ƚϣ���ֹdouble�������ֵĲ���ȷ��
				if(keyValue<=v+0.001){
					return false;///
				}
			}
			
			return true;
			
		}else if(keyValue<values[0]-0.001){
			//�˴���ʾֻ�����Ǽ�Сֵ
			for(double v:values){
				///�˴�������Ƚϣ���ֹdouble�������ֵĲ���ȷ��
				if(keyValue>=v-0.001){
					return false;///
				}
			}
			return true;
			
		}else{
			return false;
		}		
		
	}
	
	
	
}
