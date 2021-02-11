package cn.milai.ib.component;

import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.control.cmd.ClickCmd;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.plugin.control.cmd.CmdType;

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
	public boolean exec(Cmd cmd) {
		if (cmd.getType() != CmdType.CLICKED) {
			return true;
		}
		afterPressed.run();
		return false;
	}

	@Override
	public boolean accept(Cmd cmd) {
		if (cmd.getType() != CmdType.CLICKED || (!(cmd instanceof ClickCmd))) {
			return false;
		}
		ClickCmd c = (ClickCmd) cmd;
		return containsPoint(c.getX(), c.getY());
	}

}
