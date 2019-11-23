package cn.milai.ib.obj;

import cn.milai.ib.GameObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Paintable;

public abstract class GameTip extends GameObject implements Paintable {

	public GameTip(int x, int y, int width, int height, Container container) {
		super(x, y, width, height, container);
	}

	@Override
	public int getPaintLayer() {
		return Paintable.GAME_TIP_LAYER;
	}

}
