package cn.milai.ib.role;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.config.ConfigAware;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;
import cn.milai.ib.obj.property.holder.PainterHolder;

/**
 * 仅用于展示的 {@link Role}
 * @author milai
 * @date 2021.06.25
 */
public class ViewRole extends BaseRole implements ConfigAware, PainterHolder {

	private static final double MIN_RADIAN = 0.01;

	private Class<? extends Role> configClass;
	private boolean horizontalFlipped = false;

	private Map<BufferedImage, BufferedImage> flipped = new ConcurrentHashMap<>();

	/**
	 * 创建一个使用 configClass 配置的视图对象
	 * @param centerX
	 * @param centerY
	 * @param container
	 * @param configClass
	 */
	public ViewRole(Class<? extends Role> configClass) {
		this.configClass = configClass;
	}

	@Override
	protected Painter initPainter() {
		return new BasePainter() {
			@Override
			public void paintWith(Graphics g) {
				if (Math.abs(getDirection()) < MIN_RADIAN) {
					g.drawImage(getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), null);
					return;
				}
				Images.paint((Graphics2D) g, getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), getDirection());
			}

			@Override
			public BufferedImage getNowImage() {
				BufferedImage img = super.getNowImage();
				if (horizontalFlipped) {
					img = flipped.computeIfAbsent(img, i -> Images.horizontalFlip(i));
				}
				return img;
			}
		};
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
		double direction = getDirection() + radian;
		if (direction > 2 * Math.PI) {
			direction -= 2 * Math.PI * (int) (direction / 2 / Math.PI);
		}
		while (direction < -2 * Math.PI) {
			direction += 2 * Math.PI * (int) (direction / 2 / Math.PI);
		}
		setDirection(direction);
	}

	/**
	 * 开启/关闭水平翻转
	 */
	public void horzontalFlip() {
		horizontalFlipped = !horizontalFlipped;
	}

	@Override
	public String getConfigCode() { return configClass.getName(); }

}
