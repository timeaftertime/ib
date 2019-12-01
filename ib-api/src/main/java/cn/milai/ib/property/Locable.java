package cn.milai.ib.property;

/**
 * 可定位的
 *
 * 2019.11.29
 *
 * @author milai
 */
public interface Locable {

	/**
	 * X 坐标
	 * 
	 * @return
	 */
	int getX();

	/**
	 * Y 坐标
	 * 
	 * @return
	 */
	int getY();

	/**
	 * 宽度
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * 高度
	 * 
	 * @return
	 */
	int getHeight();

	/**
	 * 中心 X 坐标
	 * 
	 * @return
	 */
	int getCenterX();

	/**
	 * 中心 Y 坐标
	 * 
	 * @return
	 */
	int getCenterY();

}
