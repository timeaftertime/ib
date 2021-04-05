package cn.milai.ib.control.button;

import cn.milai.ib.Controllable;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.plugin.control.cmd.PointCmd;
import cn.milai.ib.control.AbstractControl;

/**
 * 按钮
 * @author milai
 * @date 2020.04.07
 */
public abstract class Button extends AbstractControl implements Controllable {

	/**
	 * 当前是否处于(鼠标)光标下
	 */
	private boolean overed = false;

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
		switch (cmd.getType()) {
			case CLICKED :
				afterPressed.run();
				return false;
			case OVER :
				if (inCmdPoint(cmd)) {
					if (!isOvered()) {
						overed = true;
						onEntered();
					}
				} else {
					if (isOvered()) {
						overed = false;
						onExited();
					}
				}
				return true;
			default:
				return true;
		}
	}

	@Override
	public boolean accept(Cmd cmd) {
		switch (cmd.getType()) {
			case CLICKED :
				return inCmdPoint(cmd);
			case OVER :
				return true;
			default:
				return false;
		}
	}

	private boolean inCmdPoint(Cmd cmd) {
		PointCmd c = (PointCmd) cmd;
		return containsPoint(c.getX(), c.getY());
	}

	/**
	 * 是否处于进入状态
	 * @return
	 */
	protected boolean isOvered() { return overed; }

	/**
	 * (鼠标)进入按钮时调用
	 */
	protected void onEntered() {};

	/**
	 * (鼠标)离开按钮时调用
	 */
	protected void onExited() {};

}
