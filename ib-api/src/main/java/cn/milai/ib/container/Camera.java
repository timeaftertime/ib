package cn.milai.ib.container;

import java.awt.image.BufferedImage;

import cn.milai.ib.geometry.Point;

/**
 * 照相机，用于实现实际容器与屏幕显示的映射
 * @author milai
 * @date 2020.11.29
 */
public interface Camera {

	/**
	 * 获取 img 通过当前照相机映射后的结果
	 * @param img
	 * @return
	 */
	BufferedImage reflect(BufferedImage img);

	/**
	 * 获取指定点在指定容器中对应的实际点
	 * @param view
	 * @return
	 */
	Point toReal(UIContainer c, Point view);

	/**
	 * 获取指定显示 x 在容器 c 中对应的实际 x 坐标
	 * @param c
	 * @param x
	 * @return
	 */
	int toRealX(UIContainer c, int x);

	/**
	 * 获取指定显示 y 在容器 c 中对应的实际 y 坐标
	 * @param c
	 * @param y
	 * @return
	 */
	int toRealY(UIContainer c, int y);
}