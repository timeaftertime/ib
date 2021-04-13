package cn.milai.ib.geometry;

/**
 * 点
 * @author milai
 * @date 2020.04.01
 */
public class Point {

	private long x;
	private long y;

	public Point(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public long getX() { return x; }

	public long getY() { return y; }

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
		return new Point(Math.round(x2), Math.round(y2));
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
}
