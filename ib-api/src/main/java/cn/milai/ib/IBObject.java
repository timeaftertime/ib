package cn.milai.ib;

import java.awt.Graphics;

import cn.milai.ib.container.Container;
import cn.milai.ib.geometry.Point;

/**
 * 所有游戏对象的接口
 * @author milai
 * @date 2020.02.20
 */
public interface IBObject extends Paintable, Bounds {

	/**
	 * 属性 width 的 key
	 */
	String P_WIDTH = "width";

	/**
	 * 属性 height 的 key
	 */
	String P_HEIGHT = "height";

	/**
	 * 当前游戏对象是否包含指定点
	 * @param x
	 * @param y
	 * @return
	 */
	default boolean containsPoint(double x, double y) {
		return x >= getX() && x <= getX() + getW() && y >= getY() && y <= getY() + getH();
	}

	/**
	 * 获取游戏对象的实际边界点坐标
	 * @return 一个长度为 4 的 Point 数组，分别表示游戏对象左上、左下、右下、右上点的坐标
	 */
	default Point[] getRealBoundPoints() {
		return new Point[] {
			new Point(getIntX(), getIntY()),
			new Point(getIntX(), getIntY() + getIntH()),
			new Point(getIntX() + getIntW(), getIntY() + getIntH()),
			new Point(getIntX() + getIntW(), getIntY()),
		};
	}

	@Override
	default void paintWith(Graphics g) {
		g.drawImage(getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), null);
	}

	/**
	 * 获取所属容器
	 * @return
	 */
	Container getContainer();

	/**
	 * 获取对象的 int 类型配置信息
	 * @return
	 */
	int intConf(String key);

	/**
	 * 获取对象的 long 类型配置信息
	 * @return
	 */
	long longConf(String key);

	/**
	 * 获取对象的 double 类型配置信息
	 * @param key
	 * @return
	 */
	double doubleConf(String key);

	/**
	 * 获取对象的 String 类型配置信息
	 * @param key
	 * @return
	 */
	String conf(String key);

}
