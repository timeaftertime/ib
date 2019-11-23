package cn.milai.ib.form;

import java.awt.Graphics;
import java.awt.Image;

public abstract class DoubleBufferForm extends GameForm {

	private static final long serialVersionUID = 1L;

	@Override
	public final void paint(Graphics g) {
		g.drawImage(getBufferedImage(), 0, 0, null);
	}
	
	/**
	 * 
	 * @return 需要被刷新到界面上的 Image 
	 */
	protected abstract Image getBufferedImage();
	
}
