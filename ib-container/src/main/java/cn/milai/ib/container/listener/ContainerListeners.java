package cn.milai.ib.container.listener;

import java.util.List;
import java.util.stream.Collectors;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.pluginable.PluginListener;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.item.Item;
import cn.milai.ib.role.Role;

/**
 * {@link ContainerListener} 工具类
 * @author milai
 * @date 2021.03.09
 */
public class ContainerListeners {

	/**
	 * 根据 {@code listener} 所实现的接口类型，分别调用对应的 add 方法添加到 {@code container} 中
	 * @param container
	 * @param listener
	 */
	public static void add(Container container, ContainerListener listener) {
		if (listener instanceof ObjectListener) {
			container.addObjectListener((ObjectListener) listener);
		}
		if ((container instanceof LifecycleContainer) && (listener instanceof LifecycleListener)) {
			((LifecycleContainer) container).addLifecycleListener((LifecycleListener) listener);
		}
		if ((container instanceof PluginableContainer) && (listener instanceof PluginListener)) {
			((PluginableContainer) container).addPluginListener((PluginListener) listener);
		}
	}

	/**
	 * 根据 {@code listener} 所实现的接口类型，分别调用对应的 remove 方法从 {@code container} 中移除
	 * @param container
	 * @param listener
	 */
	public static void remove(Container container, ContainerListener listener) {
		if (listener instanceof ObjectListener) {
			container.removeObjectListener((ObjectListener) listener);
		}
		if ((container instanceof LifecycleContainer) && (listener instanceof LifecycleListener)) {
			((LifecycleContainer) container).removeLifecycleListener((LifecycleListener) listener);
		}
		if ((container instanceof PluginableContainer) && (listener instanceof PluginListener)) {
			((PluginableContainer) container).removePluginListener((PluginListener) listener);
		}
	}

	/**
	 * 获取一个监听指定 {@link Item} 移除的 {@link ObjectListener}。
	 * 指定 {@link Item} 被移除时将用其调用 {@link ObjectCallback#callback(Container, Item)}
	 * @param callback
	 * @param removed
	 * @return
	 */
	public static ObjectListener removedListener(ObjectCallback<Item> callback, Item... removed) {
		return new ObjectListener() {
			@Override
			public void onObjectRemoved(Container container, List<Item> objs) {
				for (Item o : removed) {
					if (objs.contains(o)) {
						callback.callback(container, o);
					}
				}
			}
		};
	}

	/**
	 * 获取一个通过指定回调方法实现的 {@link ObjectListener}
	 * @param onAdded {@link ObjectListener#onObjectAdded(Container, Item)} 的实现
	 * @param onRemoved {@link ObjectListener#onObjectRemoved(Container, List)} 的实现
	 * @return
	 */
	public static ObjectListener objectListener(ObjectCallback<Item> onAdded, ObjectsCallback<Item> onRemoved) {
		return new ObjectListener() {
			@Override
			public void onObjectAdded(Container container, Item obj) {
				if (onAdded != null) {
					onAdded.callback(container, obj);
				}
			}

			@Override
			public void onObjectRemoved(Container container, List<Item> objs) {
				if (onRemoved != null) {
					onRemoved.callback(container, objs);
				}
			}
		};
	}

	public static ObjectListener roleListener(ObjectCallback<Role> onAdded, ObjectsCallback<Role> onRemoved) {
		return objectListener(
			onAdded == null ? null : (c, o) -> {
				if (o instanceof Role) {
					onAdded.callback(c, (Role) o);
				}
			},
			onRemoved == null ? null : (c, os) -> {
				onRemoved.callback(
					c, os.stream().filter(o -> (o instanceof Role)).map(o -> (Role) o).collect(Collectors.toList())
				);
			}
		);
	}

	public static LifecycleListener refreshListener(ContainerCallback afterRefreshed) {
		return new LifecycleListener() {
			@Override
			public void onRefresh(LifecycleContainer container) {
				afterRefreshed.callback(container);
			}
		};
	}

	public static interface ObjectCallback<T extends Item> {
		void callback(Container container, T obj);
	}

	public static interface ObjectsCallback<T extends Item> {
		void callback(Container container, List<T> obj);
	}

	public static interface ContainerCallback {
		void callback(LifecycleContainer container);
	}

}
