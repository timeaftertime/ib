package cn.milai.ib.obj.component;

import java.awt.Image;
import java.awt.event.MouseListener;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.property.Paintable;

/**
 * 不会影响到游戏中的组件，如按钮、提示等
 *
 * 2019.11.30
 *
 * @author milai
 */
public abstract class IBComponent extends IBObject implements Paintable {

	private Image img;

	@Override
	public Image getImage() {
		return img;
	}

	@Override
	public void setImage(Image img) {
		this.img = img;
	}

	public IBComponent(int x, int y, Image img, Container container) {
		super(x, y, container);
		this.img = img;
	}

	/**
	 * 给当前组件添加只响应一次的鼠标监听器
	 * 
	 * @param listener
	 */
	public void addOnceMouseListener(MouseListener listener) {
		getContainer().notifyOnce(this, listener);
	}

	@Override
	public int getPaintLayer() {
		return Paintable.GAME_COMPONENT_LAYER;
	}

}
