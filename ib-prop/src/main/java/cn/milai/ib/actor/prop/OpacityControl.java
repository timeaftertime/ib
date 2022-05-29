package cn.milai.ib.actor.prop;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.actor.nature.BasePainter;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.graphics.Images;

/**
 * 可设置透明度的 {@link Prop}
 * @author milai
 * @date 2021.03.20
 */
public class OpacityControl extends BaseProp {

	private int opacity;
	private Color overBackColor;

	private Map<BufferedImage, BufferedImage> transparents = new HashMap<>();

	public OpacityControl(int opacity) {
		this.opacity = opacity;
	}

	public OpacityControl() {
		this(255);
	}

	/**
	 * 获取当前不透明度
	 * @return
	 */
	public int getOpacity() { return opacity; }

	/**
	 * 设置当前不透明度
	 * @param opacity
	 */
	public void setOpacity(int opacity) { this.opacity = opacity; }

	/**
	 * 设置 over 时背景颜色
	 * @param overBackColor
	 */
	public void setOverBackColor(Color overBackColor) { this.overBackColor = overBackColor; }

	/**
	 * 获取 over 时背景颜色
	 * @return
	 */
	public Color getOverBackColor() { return overBackColor; }

	@Override
	protected Painter createPainter() {
		return new BasePainter(this) {
			@Override
			public BufferedImage getNowImage() {
				BufferedImage img = super.getNowImage();
				if (isOvered()) {
					if (overBackColor == null) {
						return img;
					}
					BufferedImage newImage = Images.newImage(overBackColor, img.getWidth(), img.getHeight());
					newImage.getGraphics().drawImage(img, 0, 0, null);
					return newImage;
				}
				return transparents.computeIfAbsent(img, i -> Images.transparent(Images.copy(img), opacity));
			}
		};
	}

}
