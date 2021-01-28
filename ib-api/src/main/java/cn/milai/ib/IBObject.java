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
	default boolean containsPoint(int x, int y) {
		return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
	}

	/**
	 * 获取游戏对象的实际边界点坐标
	 * @return 一个长度为 4 的 Point 数组，分别表示游戏对象左上、左下、右下、右上点的坐标
	 */
	default Point[] getRealBoundPoints() {
		return new Point[] {
			new Point(getX(), getY()),
			new Point(getX(), getY() + getHeight()),
			new Point(getX() + getWidth(), getY() + getHeight()),
			new Point(getX() + getWidth(), getY()),
		};
	}

	@Override
	default void paintWith(Graphics g) {
		g.drawImage(getNowImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

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

	/**
	 * 获取所属容器
	 * @return
	 */
	Container getContainer();

}
