package cn.milai.ib.actor.prop.text;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cn.milai.ib.actor.nature.BasePainter;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.graphics.Images;

/**
 * 标签组件
 * @author milai
 * @date 2022.03.06
 */
public class Label extends AbstractTextProp {

	public static final String WRAP = "\n";

	private String text = "";
	private BufferedImage img;
	private boolean wrap = false;

	@Override
	public void setFontName(String fontName) {
		super.setFontName(fontName);
		refreshUI();
	}

	@Override
	public void setBgColor(Color bgColor) {
		super.setBgColor(bgColor);
		refreshUI();
	}

	@Override
	public void setStyle(int style) {
		super.setStyle(style);
		refreshUI();
	}

	@Override
	public void setSize(int size) {
		super.setSize(size);
		refreshUI();
	}

	protected void refreshUI() {
		String[] lines = wrap ? text.split(WRAP) : new String[] { text };
		img = Images.newTextImage(getFont(), getColor(), getBgColor(), getPadding(), 0, lines);
	}

	public String getText() { return text; }

	public void setText(String text) {
		this.text = text;
		refreshUI();
	}

	@Override
	protected final Painter createPainter() {
		return new BasePainter(this) {
			@Override
			public BufferedImage getNowImage() { return getLabelImg(img); }
		};
	}

	/**
	 * 将当前 {@link BufferedImage} 转换为要显示的 {@link BufferedImage}
	 * @param img
	 * @return
	 */
	protected BufferedImage getLabelImg(BufferedImage img) {
		return img;
	}

	/**
	 * 设置是否根据 <code>\n</code> 换行
	 * @param wrap
	 */
	public void setWrap(boolean wrap) { this.wrap = wrap; }

	/**
	 * 是否根据 <code>\n</code> 换行
	 * @return
	 */
	public boolean isWrap() { return wrap; }

	@Override
	public double getW() { return img == null ? 0 : img.getWidth(); }

	@Override
	public double getH() { return img == null ? 0 : img.getHeight(); }

}
