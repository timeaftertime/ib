package cn.milai.ib.component;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.ui.Image;
import cn.milai.ib.container.ui.UIContainer;

/**
 * IBComponent 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public abstract class AbstractComponent extends AbstractIBObject implements IBComponent {

	/**
	 * 创建一个位置在 (x, y) 且宽度和高度为 0 的组件
	 * @param x
	 * @param y
	 * @param container
	 */
	public AbstractComponent(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void setImage(Image img) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UIContainer getContainer() { return (UIContainer) super.getContainer(); }

}
