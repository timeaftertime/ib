package cn.milai.ib.role.property;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cn.milai.ib.geometry.Point;
import cn.milai.ib.geometry.Rect;
import cn.milai.ib.role.Role;
import cn.milai.ib.util.ImageUtil;

/**
 * 改变朝向时需要同时旋转图片和实体框
 * @author milai
 * @date 2021.03.29
 */
public interface Rotatable extends Property {

	default boolean containsPoint(double x, double y) {
		return new Rect(getRealBoundPoints()).containsPoint(Math.round(x), Math.round(y));
	}

	default Point[] getRealBoundPoints() {
		Role role = getRole();
		Point[] points = role.getRealBoundPoints();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].rotate(role.centerX(), role.centerY(), role.getDirection());
		}
		return points;
	}

	/**
	 * 实现 {@link Rotatable} 接口的类不能出现覆盖了 {@link #paintWith(Graphics)} 的父类
	 */
	static void paintWith(Graphics g, Role role) {
		if (!role.isAlive()) {
			return;
		}
		ImageUtil.paint(
			(Graphics2D) g, role.getNowImage(), role.getIntX(), role.getIntY(), role.getIntW(), role.getIntH(), role
				.getDirection()
		);
	}
}
