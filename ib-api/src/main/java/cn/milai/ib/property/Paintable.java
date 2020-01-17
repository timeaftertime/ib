package cn.milai.ib.property;

import java.awt.Graphics;
import java.awt.Image;

public interface Paintable extends Comparable<Paintable> {

	int BULLET_LAYER = -1;
	int GAME_CHARACTER_LAYER = 0;
	int BOMB_LAYER = 1;
	int GAME_COMPONENT_LAYER = 100;

	/**
	 * 使用给定的 Graphics 实例绘制当前对象
	 * @param g
	 */
	void paintWith(Graphics g);

	/**
	 * 绘画层级，层数越高越显示在上层
	 * @return
	 */
	default int getPaintLayer() {
		return GAME_CHARACTER_LAYER;
	}

	@Override
	default int compareTo(Paintable o) {
		return getPaintLayer();
	}

	/**
	 * 获取当前对象对应的图像
	 * @return
	 */
	Image getImage();
	
	/**
	 * 设置当前对象对应的图像
	 * @param img
	 */
	void setImage(Image img);
}
