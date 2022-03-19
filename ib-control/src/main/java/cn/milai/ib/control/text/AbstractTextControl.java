package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.control.BaseControl;

/**
 * {@link TextControl} 抽象实现
 * @author milai
 * @date 2020.02.21
 */
public abstract class AbstractTextControl extends BaseControl implements TextControl {

	private Font font = new Font("楷体", Font.BOLD, 17);
	private Color color = new Color(0xffeeee);
	private Color bgColor = Color.BLACK;
	private int padding = 5;

	@Override
	public String getFontName() { return font.getFontName(); }

	@Override
	public void setFontName(String fontName) { font = new Font(fontName, font.getStyle(), font.getSize()); }

	@Override
	public int getSize() { return font.getSize(); }

	@Override
	public void setSize(int size) { font = new Font(font.getFontName(), font.getStyle(), size); }

	@Override
	public int getStyle() { return font.getStyle(); }

	@Override
	public void setStyle(int style) { font = new Font(font.getFontName(), style, font.getSize()); }

	@Override
	public Font getFont() { return font; }

	@Override
	public void setColor(Color color) { this.color = color; }

	@Override
	public Color getColor() { return color; }

	@Override
	public Color getBgColor() { return bgColor; }

	@Override
	public void setBgColor(Color bgColor) { this.bgColor = bgColor; }

	@Override
	public int getPadding() { return padding; }

	@Override
	public void setPadding(int padding) { this.padding = padding; }

}
