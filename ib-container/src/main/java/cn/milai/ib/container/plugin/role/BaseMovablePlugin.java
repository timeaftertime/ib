package cn.milai.ib.container.plugin.role;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.BaseMonitorPlugin;
import cn.milai.ib.role.property.Movable;

/**
 * {@link MovablePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseMovablePlugin extends BaseMonitorPlugin<Movable> implements MovablePlugin {

	public BaseMovablePlugin() {
		super(Movable.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(new LifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused() || container.isPined()) {
					return;
				}

				long start = System.currentTimeMillis();

				for (Movable m : getAll()) {
					m.move();
				}

				metric(KEY_DELAY, System.currentTimeMillis() - start);
			}
		});
	}

}
