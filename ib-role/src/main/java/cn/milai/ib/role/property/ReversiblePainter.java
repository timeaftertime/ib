package cn.milai.ib.role.property;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.property.Painter;

/**
 * 可翻转的 {@link Painter} 实现
 * @author milai
 * @date 2021.07.14
 */
public class ReversiblePainter extends RolePainter {

	private Map<BufferedImage, BufferedImage> xReversed = new ConcurrentHashMap<>();
	private Map<BufferedImage, BufferedImage> yReversed = new ConcurrentHashMap<>();

	private boolean xReversible;
	private boolean yReversible;

	public ReversiblePainter(boolean xReversible, boolean yReversible) {
		this.xReversible = xReversible;
		this.yReversible = yReversible;
	}

	public ReversiblePainter() {
		this(false, false);
	}

	public boolean isxReversible() {
		return xReversible;
	}

	public void setxReversible(boolean xReversible) {
		this.xReversible = xReversible;
	}

	public boolean isyReversible() {
		return yReversible;
	}

	public void setyReversible(boolean yReversible) {
		this.yReversible = yReversible;
	}

	@Override
	public BufferedImage getNowImage() {
		BufferedImage nowImage = super.getNowImage();
		if (xReversible) {
			if (needXReverse()) {
				nowImage = xReversed.computeIfAbsent(nowImage, i -> Images.horizontalFlip(i));
			}
		}
		if (yReversible) {
			if (needYReverse()) {
				nowImage = yReversed.computeIfAbsent(nowImage, i -> Images.verticalFlip(i));
			}
		}
		return nowImage;
	}

	protected boolean needXReverse() {
		return owner().getDirection() < 0;
	}

	protected boolean needYReverse() {
		return Math.abs(owner().getDirection()) > Math.PI / 2;
	}

}
