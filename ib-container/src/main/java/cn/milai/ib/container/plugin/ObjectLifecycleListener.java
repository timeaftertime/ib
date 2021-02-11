package cn.milai.ib.container.plugin;

import cn.milai.ib.container.lifecycle.ContainerEventListener;

/**
 * {@link BaseObjectPlugin} 持有的 {@link ContainerEventListener}
 * @author milai
 * @date 2021.02.11
 */
public interface ObjectLifecycleListener extends ContainerEventListener {

	@Override
	default boolean acrossEpoch() {
		return true;
	}
}
