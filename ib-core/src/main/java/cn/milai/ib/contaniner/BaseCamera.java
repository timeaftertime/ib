package cn.milai.ib.contaniner;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.Camera;
import cn.milai.ib.container.UIContainer;
import cn.milai.ib.geometry.Point;

/**
 * 直接返回原 {@link BufferedImage} 的 {@link Camera } 默认实现
 * @author milai
 * @date 2020.11.29
 */
public class BaseCamera implements Camera {

	@Override
	public BufferedImage reflect(BufferedImage img) {
		return img;
	}

	@Override
	public Point toReal(UIContainer c, Point view) {
		return new Point(toRealX(c, view.getX()), toRealY(c, view.getY()));
	}

	@Override
	public int toRealX(UIContainer c, int x) {
		return (int) (1.0 * c.getWidth() / c.getUIWidth() * x);
	}

	@Override
	public int toRealY(UIContainer c, int y) {
		return (int) (1.0 * c.getHeight() / c.getUIHeight() * y);
	}
}