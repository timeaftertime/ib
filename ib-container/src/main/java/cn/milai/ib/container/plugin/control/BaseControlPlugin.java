package cn.milai.ib.container.plugin.control;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.item.Controllable;

/**
 * {@link ControlPlugin} 默认实现
 * @author milai
 * @date 2020.12.12
 */
public class BaseControlPlugin extends AbstractControlPlugin implements ControlPlugin {

	/**
	 * 默认每帧最多执行多少条指令
	 */
	private static final int PER_FRAME_CMD = 5;

	private int perFrameCmd;

	private CmdQueue cmdQueue;

	public BaseControlPlugin() {
		this.perFrameCmd = PER_FRAME_CMD;
		cmdQueue = new CmdQueue();
	}

	public void setPerFrameCmd(int num) { this.perFrameCmd = num; }

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(ContainerListeners.refreshListener(c -> {
			long start = System.currentTimeMillis();
			List<Controllable> controllables = sortedControllables();
			for (int i = 0; i < perFrameCmd; i++) {
				cmdQueue.runNext(controllables);
			}
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

	@Override
	public void addCmd(Cmd cmd) {
		if (container().isPaused()) {
			if (cmd.getType() == Cmd.PAUSE) {
				cmdQueue.clear();
				cmdQueue.add(cmd);
				cmdQueue.runNext(sortedControllables());
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
