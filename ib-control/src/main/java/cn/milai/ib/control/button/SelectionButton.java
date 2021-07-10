package cn.milai.ib.control.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.control.text.Selections;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;

/**
 * 选项按钮
 * @author milai
 * @date 2021.06.12
 */
public class SelectionButton extends Button {

	private Selections selections;
	private String text;
	private BufferedImage img;
	private Color bgColor = Color.BLACK;
	private int padding;
	private int margin;

	public Color getBGColor() { return bgColor; }

	public void setBGColor(Color bgColor) { this.bgColor = bgColor; }

	public int getPadding() { return padding; }

	public void setPadding(int padding) { this.padding = padding; }

	public int getMargin() { return margin; }

	public void setMargin(int margin) { this.margin = margin; }

	public SelectionButton(Selections selections, int value, String text) {
		super(() -> {
			selections.select(value);
		});
		this.selections = selections;
		this.text = text;
	}

	@Override
	protected Painter createPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				if (img != null) {
					return img;
				}
				int width = getIntW();
				int height = getIntH();
				img = Images.newImage(getBGColor(), width, height);
				Graphics g = img.createGraphics();
				g.drawRect(0, 0, width - 1, height - 1);
				g.setFont(selections.getTextProperty().getFont());
				Texts.resizeToFit(text, g, width - 2 * getPadding(), height - 2 * getPadding());
				g.drawString(
					text,
					(width - Texts.getTextWidth(text, g)) / 2,
					(height + Texts.getTextHeight(g)) / 2
				);
				return img;
			};
		};
	}

}