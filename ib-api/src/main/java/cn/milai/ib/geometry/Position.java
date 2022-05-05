package cn.milai.ib.geometry;

/**
 * 位置
 * @author milai
 * @date 2022.01.25
 */
public interface Position {

	/**
	 * 获取 X 坐标
	 * @return
	 */
	double getX();

	/**
	 * 设置 X 坐标
	 * @param x
	 */
	default void setX(double x) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取 Y 坐标
	 * @return
	 */
	double getY();

	/**
	 * 设置 Y 坐标
	 * @param y
	 */
	default void setY(double y) {
		throw new UnsupportedOperationException();
	}
}
