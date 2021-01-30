package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.BaseBounds;

/**
 * 用于等比例显示局部区域的 {@link Camera} 实现
 * @author milai
 * @date 2020.12.05
 */
public class LocalAreaCamera extends BaseBounds implements Camera {

	/**
	 * 超出范围时使用的默认颜色 RGB
	 */
	private static final int DEF_RGB = 0;

	private int rgbOutOfBounds;

	public LocalAreaCamera(double x, double y, double width, double height) {
		this(x, y, width, height, DEF_RGB);
	}

	public LocalAreaCamera(double x, double y, double width, double height, int rgbOutOfBounds) {
		super(x, y, width, height);
		this.rgbOutOfBounds = rgbOutOfBounds;
	}

	@Override
	public BufferedImage reflect(BufferedImage img) {
		BufferedImage view = new BufferedImage(getIntW(), getIntH(), img.getType());
		for (int i = 0; i < view.getWidth(); i++) {
			for (int j = 0; j < view.getHeight(); i++) {
				int x = getIntX() + i;
				int y = getIntY() + j;
				view.setRGB(i, j, inBounds(x, y, img) ? img.getRGB(x, y) : rgbOutOfBounds);
			}
		}
		return view;
	}

	private boolean inBounds(double x, double y, BufferedImage img) {
		return x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight();
	}

	@Override
	public double toRealX(UIContainer c, double x) {
		return getIntX() + x * (1.0 * getIntW() / c.getUIWidth());
	}

	@Override
	public double toRealY(UIContainer c, double y) {
		return getIntY() + y * (1.0 * getIntH() / c.getUIHeight());
	}

}
