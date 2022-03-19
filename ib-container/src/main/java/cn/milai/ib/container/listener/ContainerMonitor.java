package cn.milai.ib.container.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import cn.milai.beginning.collection.Filter;
import cn.milai.ib.container.Container;
import cn.milai.ib.item.Item;

/**
 * {@link Container} 对象监听器
 * @author milai
 * @date 2021.03.28
 */
public class ContainerMonitor {

	private Set<Item> monitored = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private Container container;
	private ItemListener listener;

	/**
	 * 创建一个监听指定中期中所有满足指定 {@link Predicate} 的对象的 {@link ContainerMonitor} 并开始监听
	 * @param container
	 * @param filter
	 */
	public ContainerMonitor(Container container, Predicate<Item> filter) {
		this.container = container;
		listener = ContainerListeners.objectListener((c, o) -> {
			if (filter.test(o)) {
				monitored.add(o);
			}
		}, (c, os) -> monitored.removeAll(os));
		container.addItemListener(listener);
		monitored.addAll(Filter.list(container.getAll(Item.class), o -> filter.test(o)));
	}

	/**
	 * 获取当前监听到的所有 {@link Item}
	 * @return
	 */
	protected List<? extends Item> getAll() { return new ArrayList<>(monitored); }

	/**
	 * 停止监听
	 */
	public void stop() {
		container.removeItemListener(listener);
	}

}
