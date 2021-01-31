package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

/**
 * 直接返回原 {@link BufferedImage} 即显示容器全貌的 {@link Camera } 默认实现
 * @author milai
 * @date 2020.11.29
 */
public class BaseCamera implements Camera {

	@Override
	public BufferedImage reflect(BufferedImage img) {
		return img;
	}

	@Override
	public double toRealX(UIContainer c, double x) {
		return 1.0 * c.getW() / c.getUIW() * x;
	}

	@Override
	public double toRealY(UIContainer c, double y) {
		return 1.0 * c.getH() / c.getUIH() * y;
	}
}
