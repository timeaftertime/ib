package cn.milai.ib.role;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.nature.holder.PainterHolder;
import cn.milai.ib.geometry.Point;
import cn.milai.ib.geometry.Rect;
import cn.milai.ib.role.nature.holder.HealthHolder;

/**
 * 参与到游戏中的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Role extends Actor, HealthHolder, PainterHolder {

	/**
	 * 获取当前角色所属阵营
	 * @return
	 */
	default int getCamp() { return Camp.NO_CAMP; }

	/**
	 * 获取当前游戏角色的朝向与 y 轴负方向的夹角弧度
	 * 顺时针为正
	 * @return
	 */
	double getDirection();

	/**
	 * 设置当前游戏角色的朝向
	 * 弧度将转化为 (-PI/2, PI/2] 之间
	 * @param radian 新朝向与 y 轴负方向的夹角弧度
	 * @return
	 */
	void setDirection(double radian);

	/**
	 * 获取图像和判断框是否会随着 {@link #getDirection()} 变化而绕中心旋转
	 * @return
	 */
	boolean isFixedBox();

	/**
	 * 设置图像和判断框是否会随着 {@link #getDirection()} 变化而绕中心旋转
	 * @param fixedBox
	 */
	void setFixedBox(boolean fixedBox);

	/**
	 * 获取当前 {@link Role} 的边框
	 * @param role
	 * @return
	 */
	default Rect getBoundRect() {
		Rect rect = new Rect(this);
		if (isFixedBox()) {
			return rect;
		}
		Point[] points = rect.getPoints();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].rotate(centerX(), centerY(), getDirection());
		}
		return new Rect(points);
	}

	/**
	 * 判断当前 {@link Role} 是否包含指定点
	 * @param x
	 * @param y
	 * @return
	 */
	default boolean containsPoint(double x, double y) {
		return getBoundRect().containsPoint(Math.round(x), Math.round(y));
	}

}
