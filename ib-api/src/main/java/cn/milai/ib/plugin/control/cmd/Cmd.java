package cn.milai.ib.plugin.control.cmd;

/**
 * 指令
 * @author milai
 * @date 2021.02.10
 */
public interface Cmd {

	/**
	 * 预定义指令类型，无操作
	 */
	int NOOP = 0;

	/**
	 * 预定义指令类型，暂停
	 */
	int PAUSE = -1;

	/**
	 * 预定义指令类型，悬浮
	 */
	int MOVE = -2;

	/**
	 * 预定义指令类型，点击
	 */
	int CLICK = -3;

	/**
	 * 获取指令类型，自定义类型应该大于 0
	 * @return
	 */
	default int getType() { return NOOP; }

}
