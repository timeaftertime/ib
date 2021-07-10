package cn.milai.ib.geometry;

/**
 * 点
 * @author milai
 * @date 2020.04.01
 */
public class Point {

	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() { return x; }

	public double getY() { return y; }

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
		return new Point(x2, y2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != Point.class) {
			return false;
		}
		Point p = (Point) obj;
		return p.x == x && p.y == y;
	}

	@Override
	public int hashCode() {
		return (x + " " + y).hashCode();
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", x, y);
	}
}
