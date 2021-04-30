package cn.milai.ib.graphics;

import java.awt.Color;
import java.awt.Font;

/**
 * 文本的设置
 * @author milai
 * @date 2021.04.26
 */
public class TextConfig {

	private static final Font DEFAULT_FONT = new Font("楷体", Font.PLAIN, 18);

	private Font font = DEFAULT_FONT;

	private Color color = Color.BLACK;

	private Color bgColor = Color.WHITE;

	private float strokeWidth = 0.1f;

	public Font getFont() { return font; }

	public Color getColor() { return color; }

	public Color getBgColor() { return bgColor; }

	public float getStrokeWidth() { return strokeWidth; }

	public TextConfig setFont(Font font) {
		this.font = font;
		return this;
	}

	public TextConfig setSize(int size) {
		font = new Font(font.getFontName(), font.getStyle(), size);
		return this;
	}

	public TextConfig setStyle(int style) {
		font = new Font(font.getFontName(), style, font.getSize());
		return this;
	}

	public TextConfig setColor(Color color) {
		this.color = color;
		return this;
	}

	public TextConfig setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		return this;
	}

	public TextConfig setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
		return this;
	}

}
