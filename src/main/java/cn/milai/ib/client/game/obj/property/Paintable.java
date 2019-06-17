package cn.milai.ib.client.game.obj.property;

import java.awt.Graphics;

public interface Paintable extends Comparable<Paintable> {

	int NORMAL_LAYER = 0;
	int GAME_TIP_LAYER = 1;

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
