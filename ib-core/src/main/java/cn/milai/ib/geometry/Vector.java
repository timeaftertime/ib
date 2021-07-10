package cn.milai.ib.geometry;

/**
 * 二位向量
 * @author milai
 * @date 2020.03.11
 */
public class Vector {
	private double x;
	private double y;

	/**
	 * 创建从 p1 指向 p2 的向量
	 * @param p1
	 * @param p2
	 */
	public Vector(Point p1, Point p2) {
		x = p2.getX() - p1.getX();
		y = p2.getY() - p1.getY();
	}

	/**
	 * 当前向量与 v 的叉积
	 * @param v
	 * @return
	 */
	public double product(Vector v) {
		return x * v.y - y * v.x;
	}
}