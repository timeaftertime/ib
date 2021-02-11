package cn.milai.ib.component;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.ui.Image;

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
}
