package cn.milai.ib.container.listener.event;

import cn.milai.ib.container.Container;

/**
 * {@link Container} 的 {@link Event}
 * @author milai
 * @date 2022.05.14
 */
public class ContainerEvent implements Event {

	private Container container;

	public ContainerEvent(Container container) {
		this.container = container;
	}

	/**
	 * 获取关联的 {@link Container}
	 * @return
	 */
	public Container container() {
		return container;
	}

}
