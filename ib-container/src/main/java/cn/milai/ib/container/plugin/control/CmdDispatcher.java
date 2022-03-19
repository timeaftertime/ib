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
	 * 分发指定指令到指定 {@link PluginableContainer}
	 * @param container 目标容器，若为 null，将不分发
	 * @param cmd 目标指令，若为 null，将不分发
	 */
	default void dispatch(PluginableContainer container, Cmd cmd) {
		if (container == null) {
			return;
		}
		if (cmd == null) {
			return;
		}
		try {
			container.fire(ControlPlugin.class, c -> c.addCmd(cmd));
		} catch (ContainerClosedException e) {
			LOG.info("容器已关闭: {}", ExceptionUtils.getStackTrace(e));
		}
	}

}
