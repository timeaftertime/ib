package cn.milai.ib.container.form;

import java.awt.Graphics;
import java.awt.Image;

/**
 * 使用双缓存刷新的窗口容器
 * @author milai
 */
public abstract class DoubleBufferFormContainer extends AbstactFormContainer {

	private static final long serialVersionUID = 1L;

	@Override
	public final void paint(Graphics g) {
		g.drawImage(getBufferedImage(), 0, 0, null);
	}
	
	/**
	 * 获取当前对象需要显示的 Image
	 * @return 需要被刷新到界面上的 Image 
	 */
	protected abstract Image getBufferedImage();
	
}
