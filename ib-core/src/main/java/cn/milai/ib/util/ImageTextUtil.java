package cn.milai.ib.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * 图片上的文字相关工具类
 * @author milai
 * @date 2020.02.21
 */
public class ImageTextUtil {

	private static final String SAMPLE_STR = "A_《字！……";

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
	 * 获取普通图片使用指定字体的绘制文字的高度
	 * @param font
	 * @return
	 */
	public static int getTextHeight(Font font) {
		Graphics2D g = ImageUtil.newImage(1, 1).createGraphics();
		g.setFont(font);
		return getTextHeight(g);
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

	/**
	 * 修改 g 的字体大小，直到指定文字被绘制后的宽度和高度小于等于指定值
	 * 每次将字体减少到 2/3
	 * 若字体减小到 1 仍然不能满足宽度高度小于指定值，也将返回
	 * @param str
	 * @param g
	 * @param width
	 * @param height
	 */
	public static void resizeToFit(String str, Graphics g, int width, int height) {
		Font font = g.getFont();
		while (getTextWidth(str, g) > width || getTextHeight(g) > height) {
			int newSize = font.getSize() * 2 / 3;
			if (newSize < 0) {
				return;
			}
			font = new Font(font.getName(), font.getStyle(), newSize);
			g.setFont(font);
		}
	}
}
