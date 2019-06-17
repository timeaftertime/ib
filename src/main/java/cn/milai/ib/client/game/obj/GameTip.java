package cn.milai.ib.client.game.obj;

import java.awt.Graphics;
import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.Paintable;

public abstract class GameTip extends GameObject implements Paintable {

	private Image img;

	public GameTip(int x, int y, int width, int height, Image img, GameForm container) {
		super(x, y, width, height, container);
		this.img = img;
	}

	@Override
	public void paintWith(Graphics g) {
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public int getPaintLayer() {
		return Paintable.GAME_TIP_LAYER;
	}

}
