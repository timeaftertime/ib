package cn.milai.ib.container.control;

import cn.milai.ib.container.Container;

/**
 * 可交互的 {@link Container}
 * @author milai
 * @date 2020.12.12
 */
public interface ControllableContainer extends Container {

	/**
	 * 添加一个待处理指令
	 * @param c
	 */
	void addCommand(Command c);
	
}
