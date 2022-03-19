package cn.milai.ib.role.property;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cn.milai.ib.geometry.Point;
import cn.milai.ib.geometry.Rect;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.property.Painter;
import cn.milai.ib.role.Role;

/**
 * 改变朝向时需要同时旋转图片和实体框
 * @author milai
 * @date 2021.03.29
 */
public interface Rotatable extends RoleProperty {

	String NAME = "rotatable";

	/**
	 * 指定 {@link Role} 是否指定点
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
		Rect rect = new Rect(role);
		if (!role.hasProperty(Rotatable.class)) {
			return rect;
		}
		Point[] points = rect.getPoints();
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
		if (!r.getHealth().isAlive()) {
			return;
		}
		Painter p = r.getProperty(Painter.class);
		if (p == null) {
			return;
		}
		Images.paint(
			(Graphics2D) g, p.getNowImage(), r.getIntX(), r.getIntY(), r.getIntW(), r.getIntH(), r.getDirection()
		);
	}
}
