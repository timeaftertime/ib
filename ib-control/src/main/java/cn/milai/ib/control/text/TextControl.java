package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.control.Control;

/**
 * 与文本相关的窗口组件
 * @author milai
 * @date 2020.02.21
 */
public interface TextControl extends Control {

	String P_TEXT_SIZE = "textSize";
	String P_TEXT_FONT = "textFont";
	String P_TEXT_COLOR = "textColor";

	/**
	 * 获取文字字体
	 * @return
	 */
	Font getTextFont();

	/**
	 * 获取文字颜色
	 * @return
	 */
	Color getTextColor();
}