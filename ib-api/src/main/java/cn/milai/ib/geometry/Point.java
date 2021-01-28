package cn.milai.ib.geometry;

/**
 * 点
 * @author milai
 * @date 2020.04.01
 */
public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 获取当前点绕点 p 旋转指定弧度后的点
	 * @param p 围绕的点
	 * @param radian 需要旋转的弧度，顺时针方向为正
	 * @return
	 */
	public Point rotate(Point p, double radian) {
		return rotate(p.x, p.y, radian);
	}

	/**
	 * 获取当前点绕指定点 (x, y) 旋转指定弧度后的点
	 * @param x
	 * @param y
	 * @param radian 旋转的弧度，顺时针方向为正
	 * @return
	 */
	public Point rotate(double x, double y, double radian) {
		double x2 = x + (this.x - x) * Math.cos(radian) + (this.y - y) * -Math.sin(radian);
		double y2 = y + (this.y - y) * Math.cos(radian) + (x - this.x) * -Math.sin(radian);
		// 四舍五入
		x2 = x2 > 0 ? x2 + 0.5 : x2 - 0.5;
		y2 = y2 > 0 ? y2 + 0.5 : y2 - 0.5;
		return new Point((int) x2, (int) y2);
	}
}
