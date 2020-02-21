package cn.milai.ib.obj;

/**
 * 可定位的
 * 2019.11.29
 * @author milai
 */
public interface Locable {

	/**
	 * 获取 X 坐标
	 * @return
	 */
	int getX();

	/**
	 * 设置 X 坐标
	 * @param x
	 */
	void setX(int x);

	/**
	 * 获取 Y 坐标
	 * @return
	 */
	int getY();

	/**
	 * 设置 Y 坐标
	 * @param y
	 */
	void setY(int y);

	/**
	 * 宽度
	 * @return
	 */
	int getWidth();

	/**
	 * 设置宽度
	 * @param width
	 */
	void setWidth(int width);

	/**
	 * 获取高度
	 * @return
	 */
	int getHeight();

	/**
	 * 设置高度
	 * @param height
	 */
	void setHeight(int height);

	/**
	 * 中心 X 坐标
	 * @return
	 */
	double getCenterX();

	/**
	 * 中心 Y 坐标
	 * @return
	 */
	double getCenterY();

}
