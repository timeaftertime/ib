package cn.milai.ib.control;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;

/**
 * 可设置透明度的 {@link Control}
 * @author milai
 * @date 2021.03.20
 */
public class OpacityControl extends BaseControl {

	private int opacity;

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

	@Override
	protected Painter createPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				BufferedImage img = super.getNowImage();
				if (isOvered()) {
					return img;
				}
				return transparents.computeIfAbsent(img, i -> Images.transparent(Images.copy(img), opacity));
			}
		};
	}

}
