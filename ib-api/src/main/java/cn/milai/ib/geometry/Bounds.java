package cn.milai.ib.geometry;

/**
 * 限制范围
 * 2019.11.29
 * @author milai
 */
public interface Bounds {

	/**
	 * 获取 X 坐标
	 * @return
	 */
	double getX();

	/**
	 * 获取 X 坐标四舍五入后的 int 值
	 * @return
	 */
	int getIntX();

	/**
	 * 设置 X 坐标
	 * @param x
	 */
	void setX(double x);

	/**
	 * 获取 Y 坐标
	 * @return
	 */
	double getY();

	/**
	 * 获取 Y 坐标四舍五入后的 int 值
	 * @return
	 */
	int getIntY();

	/**
	 * 设置 Y 坐标
	 * @param y
	 */
	void setY(double y);

	/**
	 * 获取宽度
	 * @return
	 */
	double getW();

	/**
	 * 获取宽度四舍五入后的 int 值
	 * @return
	 */
	int getIntW();

	/**
	 * 设置宽度
	 * @param w
	 */
	void setW(double w);

	/**
	 * 获取高度
	 * @return
	 */
	double getH();

	/**
	 * 获取高度四舍五入后的 int 值
	 * @return
	 */
	int getIntH();

	/**
	 * 设置高度
	 * @param h
	 */
	void setH(double h);

	/**
	 * 重新设置边界
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	default void resetBounds(double x, double y, double w, double h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
	}

	/**
	 * 中心 X 坐标
	 * @return
	 */
	double centerX();

	/**
	 * 中心 Y 坐标
	 * @return
	 */
	double centerY();

	/**
	 * 获取四个边界点，顺序为左上、左下、右下、右上点的坐标
	 * @return
	 */
	default Point[] toPoints() {
		long x = (long) getX();
		long y = (long) getY();
		long w = (long) getW();
		long h = (long) getH();
		return new Point[] { new Point(x, y), new Point(x, y + h), new Point(x + w, y + h), new Point(x + w, y), };
	}

	/**
	 * 当前 {@link Bounds} 是否包含指定点
	 * @param x
	 * @param y
	 * @return
	 */
	default boolean containsPoint(double x, double y) {
		return x >= getX() && x <= getX() + getW() && y >= getY() && y <= getY() + getH();
	}

	/**
	 * 设置 x, y 为指定值并返回原对象
	 * @param <T>
	 * @param t
	 * @param x
	 * @param y
	 * @return
	 */
	static <T extends Bounds> T xy(T t, double x, double y) {
		t.setX(x);
		t.setY(y);
		return t;
	}

	/**
	 * 设置中间 x, y 为指定值并返回原对象
	 * @param <T>
	 * @param t
	 * @param x
	 * @param y
	 * @return
	 */
	static <T extends Bounds> T centerXY(T t, double x, double y) {
		t.setX(x - t.getW() / 2);
		t.setY(y - t.getH() / 2);
		return t;
	}

}
