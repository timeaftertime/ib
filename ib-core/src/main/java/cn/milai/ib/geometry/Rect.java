package cn.milai.ib.geometry;

import java.util.Arrays;

/**
 * 可旋转的矩形
 * @author milai
 * @date 2020.03.12
 */
public class Rect {

	private static final int SIZE = 4;

	private Point[] points;

	/**
	 * 使用指定的点构造一个矩形，从左上角开始逆时针顺序
	 * @param points
	 * @throws IllegalArgumentException points 的长度不等于 4
	 */
	public Rect(Point[] points) throws IllegalArgumentException {
		if (points.length != SIZE) {
			throw new IllegalArgumentException("points 的长度必须为 4：" + points.length);
		}
		this.points = points;
	}

	/**
	 * 使用指定 {@code x}, {@codey} 坐标及宽带 {@code w}、高度 {@code h} 构造一个不旋转的矩形
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Rect(Bounds bounds) {
		this(bounds.toPoints());
	}

	/**
	 * 判断当前矩形是否与指定矩形有交点
	 * @param rect
	 * @return
	 */
	public boolean intersects(Rect rect) {
		// 若包含四个顶点中任意一个，则一定有交点
		for (Point p : points) {
			if (rect.containsPoint(p.getX(), p.getY())) {
				return true;
			}
		}
		for (Point p : rect.points) {
			if (containsPoint(p.getX(), p.getY())) {
				return true;
			}
		}
		// 否则相交时，边对应的线段一定有交点且一个四边形的顶点与另一个的边不重合
		for (int i = 0; i < SIZE; i++) {
			Point a = points[i % SIZE];
			Point b = points[(i + 1) % SIZE];
			for (int j = 0; j < SIZE; j++) {
				Point c = rect.points[j % SIZE];
				Point d = rect.points[(j + 1) % SIZE];
				Vector dc = new Vector(d, c);
				Vector db = new Vector(d, b);
				Vector da = new Vector(d, a);
				Vector ab = new Vector(a, b);
				Vector ad = new Vector(a, d);
				Vector ac = new Vector(a, c);
				if (dc.product(db) * dc.product(da) < 0 && ab.product(ad) * ab.product(ac) < 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 当前矩形范围是否包含指定点
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean containsPoint(double x, double y) {
		Point p = new Point(x, y);
		Vector ap = new Vector(points[0], p);
		Vector ab = new Vector(points[0], points[1]);
		Vector cd = new Vector(points[2], points[3]);
		Vector cp = new Vector(points[2], p);
		Vector cb = new Vector(points[2], points[1]);
		Vector ad = new Vector(points[0], points[3]);
		return ad.product(ap) * cb.product(cp) >= 0 && ap.product(ab) * cp.product(cd) >= 0;
	}

	/**
	 * 获取 {@link Rect} 的四个顶点
	 * @return
	 */
	public Point[] getPoints() { return Arrays.copyOf(points, points.length); }
}
