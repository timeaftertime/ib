package cn.milai.ib.control.button;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;

/**
 * (鼠标)离开时会减少透明度的 {@link Button}
 * @author milai
 * @date 2021.03.20
 */
public class OpacityButton extends Button {

	private static final int TRANSPARENCY = 60;

	private Map<BufferedImage, BufferedImage> transparents = new HashMap<>();

	public OpacityButton(Runnable afterPressed) {
		super(afterPressed);
	}

	@Override
	protected Painter initPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				BufferedImage img = super.getNowImage();
				if (isOvered()) {
					return img;
				}
				return transparents.computeIfAbsent(img, i -> Images.transparent(Images.copy(img), TRANSPARENCY));
			}
		};
	}

}
