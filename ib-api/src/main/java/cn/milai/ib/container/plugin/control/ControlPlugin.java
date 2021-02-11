package cn.milai.ib.container.plugin.control;

import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 用于支持与容器交互的插件
 * @author milai
 * @date 2021.02.09
 */
public interface ControlPlugin extends ContainerPlugin {

	/**
	 * 添加一个待处理指令
	 * @param cmd
	 */
	void addCommand(Cmd cmd);

	/**
	 * 清空所有待处理指令
	 */
	void clearCommands();

}
