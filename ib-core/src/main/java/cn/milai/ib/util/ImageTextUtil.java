package cn.milai.ib.util;

import java.awt.Graphics;
import java.awt.Image;

/**
 * 图片上的文字相关工具类
 * @author milai
 * @date 2020.02.21
 */
public class ImageTextUtil {

	private static final String SAMPLE_STR = "字";

	/**
	 * 获取指定文字若被 g 绘制，将显示的宽度
	 * @param str
	 * @param g
	 * @return
	 */
	public static int getTextWidth(String str, Graphics g) {
		return (int) g.getFontMetrics().getStringBounds(str, g).getWidth() + 1;
	}

	/**
	 * 获取文字若被 g 绘制，将显示的高度
	 * @param g
	 * @return
	 */
	public static int getTextHeight(Graphics g) {
		return (int) g.getFontMetrics().getStringBounds(SAMPLE_STR, g).getHeight() + 1;
	}

	/**
	 * 获取文字若被绘制到 img 的 graphics 绘制，将显示的高度
	 * @param str
	 * @param img
	 * @return
	 */
	public static int getTextWidth(String str, Image img) {
		return getTextWidth(str, img.getGraphics());
	}

}
