package cn.milai.ib.plugin.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.plugin.control.cmd.Cmd;
import cn.milai.ib.stage.Stage;

/**
 * {@link Cmd} 分发器
 * @author milai
 * @date 2021.05.15
 */
public interface StageCmdDispatcher extends CmdDispatcher {

	Logger LOG = LoggerFactory.getLogger(StageCmdDispatcher.class);

	@Override
	default boolean dispatch(Cmd cmd) {
		Stage stage = stage();
		if (stage == null) {
			return false;
		}
		if (cmd == null) {
			return false;
		}
		stage.fire(ControlCrew.class, c -> c.addCmd(cmd));
		return true;
	}

	/**
	 * 获取关联 {@link Stage}
	 * @return
	 */
	Stage stage();

}
