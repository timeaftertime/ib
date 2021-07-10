package cn.milai.ib.control.property;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.item.property.Property;

/**
 * 文本 {@link Property}
 * @author milai
 * @date 2021.06.26
 */
public interface TextProperty extends Property {

	String NAME = "text";

	/**
	 * 获取字体名
	 * @return
	 */
	String getFontName();

	/**
	 * 设置字体名
	 * @param fontName
	 */
	void setFontName(String fontName);

	/**
	 * 获取字体大小
	 * @return
	 */
	int getSize();

	/**
	 * 设置字体大小
	 * @param size
	 */
	void setSize(int size);

	/**
	 * 获取字体 style {@link Font#getStyle()}
	 * @return
	 */
	int getStyle();

	/**
	 * 设置字体 style {@link Font#getStyle()}
	 * @param style
	 */
	void setStyle(int style);

	/**
	 * 获取字体
	 * @return
	 */
	Font getFont();

	/**
	 * 获取文本颜色
	 * @return
	 */
	Color getColor();
}
