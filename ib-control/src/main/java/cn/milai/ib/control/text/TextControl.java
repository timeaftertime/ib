package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.control.Control;

/**
 * 文本组件
 * @author milai
 * @date 2020.02.21
 */
public interface TextControl extends Control {

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

	/**
	 * 设置文本颜色
	 * @param color
	 */
	void setColor(Color color);

	/**
	 * 获取背景颜色
	 * @return
	 */
	Color getBgColor();

	/**
	 * 设置背景颜色
	 * @param color
	 * @return
	 */
	void setBgColor(Color color);

	/**
	 * 获取文字内边距
	 * @return
	 */
	int getPadding();

	/**
	 * 设置文字内边距
	 * @param padding
	 */
	void setPadding(int padding);

}
