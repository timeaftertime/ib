package cn.milai.ib.control;

import cn.milai.ib.container.plugin.control.cmd.PointCmd;

/**
 * 所有事件监听顺序通知的 {@link Control}
 * @author milai
 * @date 2022.03.12
 */
public class SyncControl extends BaseControl {

	@Override
	protected synchronized void notifyEnter(PointCmd c) {
		super.notifyEnter(c);
	}

	@Override
	protected synchronized void notifyExit(PointCmd c) {
		super.notifyExit(c);
	}

	@Override
	protected synchronized void notifyPressDown(PointCmd c) {
		super.notifyPressDown(c);
	}

	@Override
	protected synchronized void notifyPressUp(PointCmd c) {
		super.notifyPressUp(c);
	}
}
