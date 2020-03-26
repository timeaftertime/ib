package cn.milai.ib.obj;

import cn.milai.ib.container.listener.Command;

/**
 * 可以响应指令的游戏对象
 * @author milai
 * @date 2020.03.23
 */
public interface Controllable extends IBObject {

	/**
	 * 接受启用指令时调用
	 * @param command 接收的指令
	 * @return 是否需要继续调用下一个监听器
	 */
	boolean onReceive(Command command);

	/**
	 * 接受取消指令时调用
	 * @param command
	 * @return 是否需要继续调用下一个监听器
	 */
	boolean onCancel(Command command);
}
