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

	/**
	 * 指定 {@link Role} 是否制定点
	 * @param role
	 * @param x
	 * @param y
	 * @return
	 */
	static boolean containsPoint(Role role, double x, double y) {
		return getBoundRect(role).containsPoint(Math.round(x), Math.round(y));
	}

	/**
	 * 获取 {@link Role} 实际边界 {@link Rect}
	 * @param role
	 * @return
	 */
	static Rect getBoundRect(Role role) {
		if (!role.hasProperty(Rotatable.class)) {
			return new Rect(role);
		}
		Point[] points = role.toPoints();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].rotate(role.centerX(), role.centerY(), role.getDirection());
		}
		return new Rect(points);
	}

	/**
	 * 使用指定 {@link Graphics} 绘制指定 {@link Role}
	 * @param g
	 * @param r
	 */
	static void paintWith(Graphics g, Role r) {
		if (!r.isAlive()) {
			return;
		}
		ImageUtil.paint(
			(Graphics2D) g, r.getNowImage(), r.getIntX(), r.getIntY(), r.getIntW(), r.getIntH(), r.getDirection()
		);
	}
}
