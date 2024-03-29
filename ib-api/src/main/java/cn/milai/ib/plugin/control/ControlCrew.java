package cn.milai.ib.plugin.control;

import cn.milai.ib.plugin.Crew;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 用于支持与容器交互的插件
 * @author milai
 * @date 2021.02.09
 */
public interface ControlCrew extends Crew {

	/**
	 * 添加一个待处理指令
	 * @param cmd
	 */
	void addCmd(Cmd cmd);

	/**
	 * 清空所有待处理指令
	 */
	void clearCmds();

}
