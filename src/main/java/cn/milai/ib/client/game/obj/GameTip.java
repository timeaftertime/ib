package cn.milai.ib.client.game.obj;

import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.Paintable;

public abstract class GameTip extends GameObject implements Paintable {

	public GameTip(int x, int y, int width, int height, Image img, GameForm container) {
		super(x, y, width, height, img, container);
	}

	@Override
	public int getPaintLayer() {
		return Paintable.GAME_TIP_LAYER;
	}

}
