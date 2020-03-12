package cn.milai.ib.character;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import cn.milai.ib.character.property.Rotatable;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.util.Quadrangle;

/**
 * Rotatable 的抽象基类
 * @author milai
 * @date 2020.03.11
 */
public abstract class RotatableIBCharacter extends MovableIBCharacter implements Rotatable {

	public RotatableIBCharacter(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public boolean intersects(IBObject obj) {
		Point[] points1 = Rotatable.getRealBoundPoints(this);
		Point[] points2 = Rotatable.getRealBoundPoints(obj);
		return new Quadrangle(points1).intersects(new Quadrangle(points2));
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return new Quadrangle(Rotatable.getRealBoundPoints(this)).containsPoint(x, y);
	}

	@Override
	public void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		Image image = getImage();
		if (image == null) {
			throw new IllegalStateException("image 尚未初始化");
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(getCenterX(), getCenterY());
		// 速度角为逆时针方向，旋转为顺时针，所以需要使用负号
		g2d.rotate(-speedRadian());
		g2d.drawImage(image, -getWidth() / 2, -getHeight() / 2, getWidth(), getHeight(), null);
		g2d.rotate(speedRadian());
		g2d.translate(-getCenterX(), -getCenterY());
	}
}
