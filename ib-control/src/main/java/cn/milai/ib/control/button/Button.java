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
		if (!(cmd instanceof PointCmd)) {
			return true;
		}
		PointCmd c = (PointCmd) cmd;
		if (c.getType() == Cmd.CLICK) {
			if (inCmdPoint(c)) {
				afterPressed.run();
				return false;
			}
			return true;
		}
		if (c.getType() == Cmd.OVER) {
			if (inCmdPoint(c)) {
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
		}
		return true;
	}

	private boolean inCmdPoint(PointCmd cmd) {
		return containsPoint(cmd.getX(), cmd.getY());
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
