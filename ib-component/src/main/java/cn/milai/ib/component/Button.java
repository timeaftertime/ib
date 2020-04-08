package cn.milai.ib.component;

import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.Command;

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
	public boolean onReceive(Command command) {
		if (command != Command.PRESSED) {
			return true;
		}
		afterPressed.run();
		return false;
	}

	@Override
	public boolean onCancel(Command command) {
		return false;
	}

}
