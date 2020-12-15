package cn.milai.ib.container.control;

import cn.milai.ib.IBObject;

/**
 * 可以响应指令的游戏对象
 * @author milai
 * @date 2020.03.23
 */
public interface Controllable extends IBObject {

	/**
	 * 是否接收指定 commanderId 的指令
	 * @param fromId
	 * @return
	 */
	default boolean accept(int fromId) {
		return true;
	}

	/**
	 * 执行指定类型的指令
	 * @param type 指令类型
	 * @return 是否需要继续调用下一个{@code Controllable}
	 */
	boolean exec(CommandType type);

}
