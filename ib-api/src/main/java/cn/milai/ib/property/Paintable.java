package cn.milai.ib.property;

import java.awt.Graphics;

public interface Paintable extends Comparable<Paintable> {

	int NORMAL_LAYER = 0;
	int BOMB_LAYER = 1;
	int GAME_COMPONENT_LAYER = 100;

	void paintWith(Graphics g);

	/**
	 * 绘画层级，层数越高越显示在上层
	 * @return
	 */
	default int getPaintLayer() {
		return NORMAL_LAYER;
	}

	@Override
	default int compareTo(Paintable o) {
		return getPaintLayer();
	}

}
