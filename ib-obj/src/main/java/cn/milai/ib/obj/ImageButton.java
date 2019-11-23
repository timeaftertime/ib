package cn.milai.ib.obj;

import java.awt.Image;
import java.awt.event.MouseListener;

import cn.milai.ib.container.Container;

public class ImageButton extends GameTip {

	public ImageButton(int x, int y, int width, int height, Image img, Container container) {
		super(x, y, width, height, container);
		setImage(img);
	}

	public void addMouseListener(MouseListener listener) {
		getContainer().notifyOnce(this, listener);
	}

}
