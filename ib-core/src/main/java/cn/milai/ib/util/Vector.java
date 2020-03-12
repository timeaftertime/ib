package cn.milai.ib.util;

import java.awt.Point;

/**
	 * 二位向量
	 * @author milai
	 * @date 2020.03.11
	 */
public class Vector {
	int x, y;

	/**
	 * 创建从 p1 指向 p2 的向量
	 * @param p1
	 * @param p2
	 */
	public Vector(Point p1, Point p2) {
		x = p2.x - p1.x;
		y = p2.y - p1.y;
	}

	/**
	 * 当前向量与 v 的叉积
	 * @param v
	 * @return
	 */
	public long product(Vector v) {
		return x * v.y - y * v.x;
	}
}