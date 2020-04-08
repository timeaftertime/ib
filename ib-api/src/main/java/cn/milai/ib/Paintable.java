package cn.milai.ib;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Image;

public interface Paintable extends Comparable<Paintable> {

	int BULLET_LAYER = -1;
	int GAME_CHARACTER_LAYER = 0;
	int BOMB_LAYER = 1;
	int GAME_COMPONENT_LAYER = 100;

	/**
	 * 绘画层级，层数越高越显示在上层
	 * @return
	 */
	default int getPaintLayer() {
		return GAME_CHARACTER_LAYER;
	}

	@Override
	default int compareTo(Paintable o) {
		return getPaintLayer() - o.getPaintLayer();
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
