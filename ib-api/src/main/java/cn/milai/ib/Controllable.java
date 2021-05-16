package cn.milai.ib;

import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 可以响应指令的游戏对象
 * @author milai
 * @date 2020.03.23
 */
public interface Controllable extends IBObject {

	/**
	 * 执行指定指令
	 * @param cmd 指令
	 * @return 是否需要继续调用下一个{@code Controllable}
	 */
	boolean exec(Cmd cmd);

}
