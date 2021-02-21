package cn.milai.ib.container;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ObjectListener;

/**
 * {@link Container} 某类型对象的监听器，容器关闭后监听到的列表不会清空
 * @author milai
 * @date 2021.02.10
 */
public class ContainerMonitor<T extends IBObject> {

	private Container container;
	private Set<T> monitored = Sets.newConcurrentHashSet();
	private ObjectListener listener;

	private ContainerMonitor(Container container, Class<T> clazz) {
		this.container = container;
		listener = new ObjectListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onObjectAdded(IBObject obj) {
				if (clazz.isInstance(obj)) {
					monitored.add((T) obj);
				}
			}

			@Override
			public void onObjectRemoved(List<IBObject> objs) {
				monitored.removeAll(objs);
			}

		};
		container.addObjectListener(listener);
		monitored.addAll(container.getAll(clazz));
	}

	public static <T extends IBObject> ContainerMonitor<T> monitor(LifecycleContainer container, Class<T> clazz) {
		return new ContainerMonitor<>(container, clazz);
	}

	/**
	 * 获取监听器监听到的关联容器中指令类型对象的列表
	 * @return
	 */
	public List<T> getAll() { return Lists.newArrayList(monitored); }

	/**
	 * 停止监听
	 */
	public void stop() {
		container.removeObjectListener(listener);
	}
}
