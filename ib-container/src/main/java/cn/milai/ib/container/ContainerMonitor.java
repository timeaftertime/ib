package cn.milai.ib.container;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.ContainerEventListener;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * {@link Container} 某类型对象的监听器，容器关闭后监听到的列表不会清空
 * @author milai
 * @date 2021.02.10
 */
public class ContainerMonitor<T extends IBObject> {

	private LifecycleContainer container;
	private Set<T> objs = Sets.newConcurrentHashSet();
	private ContainerEventListener listener;

	private ContainerMonitor(LifecycleContainer container, Class<T> clazz) {
		this.container = container;
		listener = new ContainerEventListener() {

			@Override
			public boolean acrossEpoch() {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onObjectAdded(IBObject obj) {
				if (clazz.isInstance(obj)) {
					objs.add((T) obj);
				}
			}

			@Override
			public void onObjectRemoved(IBObject obj) {
				objs.remove(obj);
			}

			@Override
			public void afterEpochChanged(LifecycleContainer container) {
				objs.clear();
			}
		};
		container.addEventListener(listener);
		objs.addAll(container.getAll(clazz));
	}

	public static <T extends IBObject> ContainerMonitor<T> monitor(LifecycleContainer container, Class<T> clazz) {
		return new ContainerMonitor<>(container, clazz);
	}

	/**
	 * 获取监听器监听到的关联容器中指令类型对象的列表
	 * @return
	 */
	public List<T> getAll() { return Lists.newArrayList(objs); }

	/**
	 * 停止监听
	 */
	public void stop() {
		container.removeEventListener(listener);
	}
}
