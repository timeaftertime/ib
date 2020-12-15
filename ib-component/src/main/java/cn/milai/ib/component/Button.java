package cn.milai.ib.component;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.control.CommandType;
import cn.milai.ib.container.control.Controllable;

/**
 * 按钮
 * @author milai
 * @date 2020.04.07
 */
public abstract class Button extends AbstractComponent implements Controllable {

	private Runnable afterPressed;

	/**
	 * 创建一个按钮
	 * @param x
	 * @param y
	 * @param container
	 * @param afterPressed 按钮被点击时调用
	 */
	public Button(int x, int y, Container container, Runnable afterPressed) {
		super(x, y, container);
		this.afterPressed = afterPressed;
	}

	@Override
	public boolean exec(CommandType command) {
		if (command != CommandType.CLICKED) {
			return true;
		}
		afterPressed.run();
		return false;
	}

}
