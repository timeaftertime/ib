package cn.milai.ib.container.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import cn.milai.common.base.Collects;
import cn.milai.ib.IBObject;
import cn.milai.ib.container.Container;

/**
 * {@link Container} 对象监听器
 * @author milai
 * @date 2021.03.28
 */
public class ContainerMonitor {

	private Set<IBObject> monitored = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private Container container;
	private ObjectListener listener;

	/**
	 * 创建一个监听指定中期中所有满足指定 {@link Predicate} 的对象的 {@link ContainerMonitor} 并开始监听
	 * @param container
	 * @param filter
	 */
	public ContainerMonitor(Container container, Predicate<IBObject> filter) {
		this.container = container;
		listener = Listeners.objectListener((c, o) -> {
			if (filter.test(o)) {
				monitored.add(o);
			}
		}, (c, os) -> monitored.removeAll(os));
		container.addObjectListener(listener);
		monitored.addAll(Collects.filterList(container.getAll(IBObject.class), o -> filter.test(o)));
	}

	/**
	 * 获取当前监听到的所有 {@link IBObject}
	 * @return
	 */
	protected List<? extends IBObject> getAll() { return new ArrayList<>(monitored); }

	/**
	 * 停止监听
	 */
	public void stop() {
		container.removeObjectListener(listener);
	}

}
