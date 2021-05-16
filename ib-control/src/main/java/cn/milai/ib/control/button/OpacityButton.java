package cn.milai.ib.control.button;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.container.Container;
import cn.milai.ib.graphics.Images;

/**
 * (鼠标)离开时会减少透明度的 {@link Button}
 * @author milai
 * @date 2021.03.20
 */
public class OpacityButton extends Button {

	private static final int TRANSPARENCY = 60;

	private Map<BufferedImage, BufferedImage> transparents = new HashMap<>();

	public OpacityButton(int x, int y, Container container, Runnable afterPressed) {
		super(x, y, container, afterPressed);
	}

	@Override
	public BufferedImage getNowImage() {
		BufferedImage img = super.getNowImage();
		if (isOvered()) {
			return img;
		}
		return transparents.computeIfAbsent(img, i -> Images.transparent(Images.copy(img), TRANSPARENCY));
	}

}
