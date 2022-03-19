package cn.milai.ib.control;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.plugin.control.cmd.PointCmd;
import cn.milai.ib.item.BaseItem;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.Layers;
import cn.milai.ib.item.property.Painter;

/**
 * {@link Control} 的默认基类
 * @author milai
 * @date 2020.02.20
 */
public class BaseControl extends BaseItem implements Control {

	/**
	 * 当前是否处于(鼠标)光标下
	 */
	private boolean overed = false;

	private List<ControlListener> controlListeners = new ArrayList<>();

	public BaseControl() {
		setPainter(createPainter());
	}

	protected Painter createPainter() {
		return new BasePainter();
	}

	@Override
	public boolean exec(Cmd cmd) {
		if (controlListeners.isEmpty()) {
			return true;
		}
		if (!(cmd instanceof PointCmd)) {
			return true;
		}
		PointCmd c = (PointCmd) cmd;
		switch (c.getType()) {
			case Cmd.CLICK : {
				if (inCmdPoint(c)) {
					notifyPressDown(c);
					notifyPressUp(c);
					return false;
				}
				return true;
			}

			case Cmd.MOVE : {
				if (inCmdPoint(c)) {
					if (!isOvered()) {
						overed = true;
						notifyEnter(c);
					}
				} else {
					if (isOvered()) {
						overed = false;
						notifyExit(c);
					}
				}
				return true;
			}

			default:
				return true;
		}
	}

	private boolean inCmdPoint(PointCmd cmd) {
		return contains(cmd.getX(), cmd.getY());
	}

	private ControlEvent newEvent(PointCmd c, boolean isPressed) {
		return new ControlEvent(this, c.getX(), c.getY(), isPressed);
	}

	/**
	 * 是否处于进入状态
	 * @return
	 */
	protected boolean isOvered() { return overed; }

	@Override
	public boolean addControlListener(ControlListener listener) {
		return controlListeners.add(listener);
	}

	@Override
	public boolean removeControlListener(ControlListener listener) {
		return controlListeners.remove(listener);
	}

	/**
	 * 发生按下事件时调用
	 * @param c
	 */
	protected void notifyPressDown(PointCmd c) {
		notifyControlListeners(listener -> listener.onPressDown(newEvent(c, true)));
	}

	/**
	 * 发生松开事件时调用
	 * @param c
	 */
	protected void notifyPressUp(PointCmd c) {
		notifyControlListeners(listener -> listener.onPressUp(newEvent(c, true)));
	}

	/**
	 * 发生进入事件时调用
	 * @param c
	 */
	protected void notifyEnter(PointCmd c) {
		notifyControlListeners(listener -> listener.onEnter(newEvent(c, overed)));
	}

	/**
	 * 发生离开事件时调用
	 * @param c
	 */
	protected void notifyExit(PointCmd c) {
		notifyControlListeners(listener -> listener.onExit(newEvent(c, overed)));
	}

	private void notifyControlListeners(Consumer<ControlListener> consumer) {
		for (ControlListener listener : controlListeners) {
			consumer.accept(listener);
		}
	}

	@Override
	public int getZ() { return Layers.CONTROL.getZ(); }

}
