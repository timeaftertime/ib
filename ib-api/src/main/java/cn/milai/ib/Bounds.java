package cn.milai.ib;

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
	 * 中心 X 坐标
	 * @return
	 */
	double centerX();

	/**
	 * 中心 Y 坐标
	 * @return
	 */
	double centerY();

}
