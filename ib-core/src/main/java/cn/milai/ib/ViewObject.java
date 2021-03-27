package cn.milai.ib;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.container.Container;
import cn.milai.ib.role.Role;
import cn.milai.ib.util.ImageUtil;

/**
 * 仅用于显示的游戏对象
 * @author milai
 * @date 2020.03.27
 */
public class ViewObject extends AbstractIBObject {

	private static final double MIN_RADIAN = 0.01;
	private double rotateRadian;
	private boolean horizontalFlipped = false;

	private Map<BufferedImage, BufferedImage> flipped = new ConcurrentHashMap<>();

	/**
	 * 创建一个指定中心坐标 (centerX, centerY) 、指定容器，使用 configClass 配置的视图对象
	 * @param centerX
	 * @param centerY
	 * @param container
	 * @param configClass
	 */
	public ViewObject(int centerX, int centerY, Container container, Class<? extends Role> configClass) {
		super(centerX, centerY, container, configClass);
		rotateRadian = 0;
	}

	/**
	 * 以指定 x 轴速度移动一次
	 * @param speed
	 */
	public void moveX(double speed) {
		setX(speed + getX());
	}

	/**
	 * 以指定 y 轴速度移动一次
	 * @param speed
	 */
	public void moveY(double speed) {
		setY(getY() + speed);
	}

	/**
	 * 以指定 x 和 y 轴速度移动一次 
	 * @param speedX
	 * @param speedY
	 */
	public void move(double speedX, double speedY) {
		moveX(speedX);
		moveY(speedY);
	}

	/**
	 * 将图像绕中心顺时针旋转指定弧度
	 * @param radian
	 */
	public void rotate(double radian) {
		this.rotateRadian += radian;
		if (rotateRadian > 2 * Math.PI) {
			rotateRadian -= 2 * Math.PI * (int) (rotateRadian / 2 / Math.PI);
		}
		while (rotateRadian < -2 * Math.PI) {
			rotateRadian += 2 * Math.PI * (int) (rotateRadian / 2 / Math.PI);
		}
	}

	/**
	 * 设置图像旋转弧度
	 * @param radian
	 */
	public void setRotateRadian(double radian) {
		this.rotateRadian = 0;
		rotate(radian);
	}

	@Override
	public void paintWith(Graphics g) {
		if (Math.abs(this.rotateRadian) < MIN_RADIAN) {
			g.drawImage(getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), null);
			return;
		}
		ImageUtil.paint((Graphics2D) g, getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), rotateRadian);
	}

	@Override
	public BufferedImage getNowImage() {
		BufferedImage img = super.getNowImage();
		if (horizontalFlipped) {
			img = flipped.computeIfAbsent(img, i -> ImageUtil.horizontalFlip(i));
		}
		return img;
	}

	@Override
	public void setStatus(String status) {
		super.setStatus(status);
	}

	/**
	 * 开启/关闭水平翻转
	 */
	public void horzontalFlip() {
		horizontalFlipped = !horizontalFlipped;
	}

}
