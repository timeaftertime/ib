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
public interface ContainerCmdDispatcher extends CmdDispatcher {

	Logger LOG = LoggerFactory.getLogger(ContainerCmdDispatcher.class);

	@Override
	default boolean dispatch(Cmd cmd) {
		PluginableContainer container = container();
		if (container == null) {
			return false;
		}
		if (cmd == null) {
			return false;
		}
		try {
			container.fire(ControlPlugin.class, c -> c.addCmd(cmd));
			return true;
		} catch (ContainerClosedException e) {
			LOG.info("容器已关闭: {}", ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	/**
	 * 获取关联 {@link PluginableContainer}
	 * @return
	 */
	PluginableContainer container();

}
