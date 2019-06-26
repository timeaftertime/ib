package cn.milai.ib.client.game.obj;

import java.awt.Image;
import java.awt.event.MouseListener;

import cn.milai.ib.client.game.form.GameForm;

public class ImageButton extends GameTip {

	public ImageButton(int x, int y, int width, int height, Image img, GameForm container) {
		super(x, y, width, height, img, container);
	}

	public void addMouseListener(MouseListener listener) {
		getContainer().addOneTimeMouseListener(this, listener);
	}

}
