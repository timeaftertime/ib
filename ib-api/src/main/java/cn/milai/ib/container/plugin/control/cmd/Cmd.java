package cn.milai.ib.container.plugin.control.cmd;

/**
 * 指令
 * @author milai
 * @date 2021.02.10
 */
public interface Cmd {

	/**
	 * 获取指令类型
	 * @return
	 */
	CmdType getType();

	/**
	 * 获取发出指令者的 id
	 * @return
	 */
	int getFromId();
	
}
