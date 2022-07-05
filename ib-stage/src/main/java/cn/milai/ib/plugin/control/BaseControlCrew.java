package cn.milai.ib.plugin.control;

import java.util.List;

import cn.milai.ib.actor.Controllable;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * {@link ControlCrew} 默认实现
 * @author milai
 * @date 2020.12.12
 */
public class BaseControlCrew extends AbstractControlCrew {

	private CmdQueue cmdQueue;

	public BaseControlCrew() {
		cmdQueue = new CmdQueue();
	}

	@Override
	protected boolean dispatchCmd(List<Controllable> controllables) {
		return cmdQueue.runNext(controllables);
	}

	@Override
	public void addCmd(Cmd cmd) {
		if (stage().isPaused()) {
			if (cmd.getType() == Cmd.PAUSE) {
				cmdQueue.clear();
				cmdQueue.add(cmd);
				cmdQueue.runNext(sortedControllables(stage()));
			}
			return;
		}
		cmdQueue.add(cmd);
	}

	@Override
	public void clearCmds() {
		cmdQueue.clear();
	}

}
