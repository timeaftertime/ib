package cn.milai.ib.container.plugin.control;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link Cmd} 分发器
 * @author milai
 * @date 2021.05.15
 */
public interface CmdDispatcher {

	Logger LOG = LoggerFactory.getLogger(CmdDispatcher.class);

	/**
	 * 分发指定指令到 
	 * @param cmd
	 */
	default void dispatch(Cmd cmd) {
		if (cmd == null) {
			return;
		}
		try {
			getTargetContainer().fire(ControlPlugin.class, c -> c.addCmd(cmd));
		} catch (ContainerClosedException e) {
			LOG.info("容器已关闭: {}", ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * 获取分发目标容器
	 * @return
	 */
	PluginableContainer getTargetContainer();
}
