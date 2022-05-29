package cn.milai.ib.plugin.control;

import java.util.List;
import java.util.function.Consumer;

import cn.milai.ib.actor.Controllable;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.plugin.control.cmd.Cmd;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import io.micrometer.core.instrument.Timer;

/**
 * {@link ControlCrew} 默认实现
 * @author milai
 * @date 2020.12.12
 */
public class BaseControlCrew extends AbstractControlCrew {

	private static final Timer REFRESH_DELAY = Timer.builder("crew.control.delay")
		.register(IBMetrics.registry());

	/**
	 * 默认每帧最多执行多少条指令
	 */
	private static final int PER_FRAME_CMD = 5;

	private int perFrameCmd;

	private CmdQueue cmdQueue;

	public BaseControlCrew() {
		this.perFrameCmd = PER_FRAME_CMD;
		cmdQueue = new CmdQueue();
	}

	public void setPerFrameCmd(int num) { this.perFrameCmd = num; }

	@Override
	public Consumer<StageRefreshedEvent> createOnRefreshed() {
		return e -> {
			REFRESH_DELAY.record(() -> {
				List<Controllable> controllables = sortedControllables(e.stage());
				for (int i = 0; i < perFrameCmd; i++) {
					cmdQueue.runNext(controllables);
				}
			});
		};
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
