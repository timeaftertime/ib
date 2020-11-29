package cn.milai.ib;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Image;

/**
 * 可绘制的
 * @author milai
 */
public interface Paintable {

	int DEFAULT_Z = 0;

	/**
	 * 获取 Z 坐标(目前只用于显示)，值越大越显示在上层
	 * @return
	 */
	default int getZ() {
		return DEFAULT_Z;
	}

	/**
	 * 使用指定画板进行绘制
	 * 该方法应该只在接口中被实现
	 * @param g
	 */
	void paintWith(Graphics g);

	/**
	 * 获取当前对象对应的图像
	 * @return
	 */
	BufferedImage getNowImage();

	/**
	 * 设置当前对象对应的图像
	 * @param img
	 */
	void setImage(Image img);
}
