package cn.milai.ib.actor.prop;

import cn.milai.ib.actor.BaseActor;
import cn.milai.ib.actor.Layers;
import cn.milai.ib.actor.nature.BasePainter;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.actor.prop.event.PropEvent;
import cn.milai.ib.plugin.control.cmd.Cmd;
import cn.milai.ib.plugin.control.cmd.PointCmd;
import cn.milai.ib.publisher.BasePublisher;
import cn.milai.ib.publisher.Publisher;

/**
 * {@link Prop} 的默认基类
 * @author milai
 * @date 2020.02.20
 */
public class BaseProp extends BaseActor implements Prop {

	/**
	 * 当前是否处于光标下
	 */
	private boolean overed = false;

	private Publisher<PropEvent> onPressDown = new BasePublisher<>();
	private Publisher<PropEvent> onPressUp = new BasePublisher<>();
	private Publisher<PropEvent> onEnter = new BasePublisher<>();
	private Publisher<PropEvent> onExit = new BasePublisher<>();

	public BaseProp() {
		putNature(createPainter());
	}

	protected Painter createPainter() {
		return new BasePainter(this);
	}

	@Override
	public boolean exec(Cmd cmd) {
		if (!(cmd instanceof PointCmd)) {
			return true;
		}
		PointCmd c = (PointCmd) cmd;
		switch (c.getType()) {
			case Cmd.CLICK : {
				if (inCmdPoint(c)) {
					onPressDown.publish(newEvent(c));
					onPressUp.publish(newEvent(c));
					return false;
				}
				return true;
			}

			case Cmd.MOVE : {
				if (inCmdPoint(c)) {
					if (!isOvered()) {
						overed = true;
						onEnter.publish(newEvent(c));
					}
				} else {
					if (isOvered()) {
						overed = false;
						onExit().publish(newEvent(c));
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

	protected PropEvent newEvent(PointCmd c) {
		return new PropEvent(this, c.getX(), c.getY());
	}

	/**
	 * 是否处于进入状态
	 * @return
	 */
	protected boolean isOvered() { return overed; }

	@Override
	public final Publisher<PropEvent> onPressDown() {
		return onPressDown;
	}

	@Override
	public final Publisher<PropEvent> onPressUp() {
		return onPressUp;
	}

	@Override
	public final Publisher<PropEvent> onEnter() {
		return onEnter;
	}

	@Override
	public final Publisher<PropEvent> onExit() {
		return onExit;
	}

	@Override
	public int getZ() { return Layers.CONTROL.getZ(); }

}
