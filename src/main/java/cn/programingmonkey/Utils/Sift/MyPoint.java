package cn.programingmonkey.Utils.Sift;


/**
 * ��������
 * @author Administrator
 *
 */
public class MyPoint {
	
	private int preX;
	private int preY;
	
	private int x;
	private int y;
	private int s;//��˹���������ڵ�s��
	private int octave;///���ڵڼ��飨�ڼ����������
	private double theta;///�ؼ���ķ���
	
	private double[] grads;//���������������������ݶȷֲ�ͳ��
	
	private boolean isMatched;///�Ƿ�ƥ�����
	
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param s
	 * @param octave
	 * @param theta
	 * @param grads
	 */
	public MyPoint(int x,int y, int s,int octave,double theta,double[] grads,boolean isMatch) {
		this.x=x;
		this.y=y;
		this.s=s;
		this.octave=octave;
		this.grads=grads;
		this.theta=theta;
		this.isMatched=isMatch;
		// TODO Auto-generated constructor stub
	}
	
	///Ĭ�Ϲ��캯��
	public MyPoint() {
		// TODO Auto-generated constructor stub
	}




	public double[] getGrads() {
		return grads;
	}
	public void setGrads(double[] grads) {
		this.grads = grads;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getOctave() {
		return octave;
	}
	public void setOctave(int octave) {
		this.octave = octave;
	}
	public int getS() {
		return s;
	}
	public void setS(int s) {
		this.s = s;
	}




	public double getTheta() {
		return theta;
	}




	public void setTheta(double theta) {
		this.theta = theta;
	}

	public boolean isMatched() {
		return isMatched;
	}

	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}

	public int getPreX() {
		return preX;
	}

	public void setPreX(int preX) {
		this.preX = preX;
	}

	public int getPreY() {
		return preY;
	}

	public void setPreY(int preY) {
		this.preY = preY;
	}

	@Override
	public String toString()
	{
		return "{"+this.getX() +","+this.getY() +"}";
	}

}
