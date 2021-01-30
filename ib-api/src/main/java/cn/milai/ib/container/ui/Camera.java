package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

/**
 * 照相机，用于实现实际容器与屏幕显示的映射
 * @author milai
 * @date 2020.11.29
 */
public interface Camera {

	/**
	 * 获取 img 通过当前照相机映射后的结果
	 * 该方法确保不会修改参数中的 {@link BufferedImage} 对象
	 * 但不保证返回的是副本
	 * @param img
	 * @return
	 */
	BufferedImage reflect(BufferedImage img);

	/**
	 * 获取指定显示 x 在容器 c 中对应的实际 x 坐标
	 * @param c
	 * @param x
	 * @return
	 */
	double toRealX(UIContainer c, double x);

	/**
	 * 获取指定显示 y 在容器 c 中对应的实际 y 坐标
	 * @param c
	 * @param y
	 * @return
	 */
	double toRealY(UIContainer c, double y);
}
