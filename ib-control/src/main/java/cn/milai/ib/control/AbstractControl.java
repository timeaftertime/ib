package cn.milai.ib.control;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.ui.Image;

/**
 * {@link Control} 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public abstract class AbstractControl extends AbstractIBObject implements Control {

	/**
	 * 创建一个位置在 (x, y) 且宽度和高度为 0 的组件
	 * @param x
	 * @param y
	 * @param container
	 */
	public AbstractControl(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void setImage(Image img) {
		throw new UnsupportedOperationException();
	}
}
