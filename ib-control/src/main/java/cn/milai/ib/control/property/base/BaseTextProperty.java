package cn.milai.ib.control.property.base;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.control.property.TextProperty;
import cn.milai.ib.obj.property.BaseProperty;

/**
 * {@link TextProperty} 默认实现
 * @author milai
 * @date 2021.06.26
 */
public class BaseTextProperty extends BaseProperty implements TextProperty {

	private Font font = new Font("楷体", Font.BOLD, 17);
	private Color color = new Color(0xffeeee);

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
	public Color getColor() { return color; }

}
