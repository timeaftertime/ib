package cn.milai.ib.character.property;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cn.milai.ib.Point;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.util.ImageUtil;
import cn.milai.ib.util.geometry.Quadrangle;

/**
 * 改变朝向时需要同时旋转图片和实体框的角色
 * @author milai
 * @date 2020.03.10
 */
public interface Rotatable extends IBCharacter {

	@Override
	default boolean containsPoint(int x, int y) {
		return new Quadrangle(getRealBoundPoints()).containsPoint(x, y);
	}

	@Override
	default Point[] getRealBoundPoints() {
		Point[] points = IBCharacter.super.getRealBoundPoints();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].rotate(getCenterX(), getCenterY(), getDirection());
		}
		return points;
	}

	/**
	 * 实现 {@link Rotatable} 接口的类不能出现覆盖了 {@link #paintWith(Graphics)} 的父类
	 */
	@Override
	default void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		ImageUtil.paint((Graphics2D) g, getNowImage(), getX(), getY(), getWidth(), getHeight(), getDirection());
	}
}
