package cn.milai.ib.container.listener;

import java.util.List;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.item.Item;

/**
 * {@link LifecycleContainer} 对象事件监听器
 * @author milai
 * @date 2021.02.17
 */
public interface ItemListener extends ContainerListener {

	/**
	 * 游戏对象被加入后调用
	 * @param container
	 * @param item
	 */
	default void onAdded(Container container, Item item) {}

	/**
	 * 游戏对象被移除后被调用
	 * @param container
	 * @param items 被移除的对象
	 */
	default void onRemoved(Container container, List<Item> items) {}

}
