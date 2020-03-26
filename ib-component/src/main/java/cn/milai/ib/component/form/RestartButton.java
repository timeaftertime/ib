package cn.milai.ib.component.form;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.Command;
import cn.milai.ib.obj.Controllable;

/**
 * 显示 “重新开始”图片字样的按钮
 * 2019.11.30
 * @author milai
 */
public class RestartButton extends AbstractFormComponent implements Controllable {

	private Runnable afterPressed;

	/**
	 * 创建一个显示“重新开始“的按钮
	 * @param x
	 * @param y
	 * @param container
	 * @param afterPressed 按钮被点击时调用
	 */
	public RestartButton(int x, int y, Container container, Runnable afterPressed) {
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
