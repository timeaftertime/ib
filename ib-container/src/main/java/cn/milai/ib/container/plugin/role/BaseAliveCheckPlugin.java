package cn.milai.ib.container.plugin.role;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.TypeMonitorPlugin;
import cn.milai.ib.role.Role;

/**
 * {@link AliveCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckPlugin extends TypeMonitorPlugin<Role> implements AliveCheckPlugin {

	public BaseAliveCheckPlugin() {
		super(Role.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(new LifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused()) {
					return;
				}

				long start = System.currentTimeMillis();

				for (Role role : getAll()) {
					if (!role.isAlive()) {
						role.onDead();
						getContainer().removeObject(role);
					}
				}

				metric(KEY_DELAY, System.currentTimeMillis() - start);
			}
		});
	}

}
