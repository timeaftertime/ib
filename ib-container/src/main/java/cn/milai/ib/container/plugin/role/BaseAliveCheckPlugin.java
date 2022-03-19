package cn.milai.ib.container.plugin.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.SharableContainerPlugin;
import cn.milai.ib.role.property.Health;

/**
 * {@link AliveCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckPlugin extends SharableContainerPlugin implements AliveCheckPlugin {

	@Override
	public void onRefresh(LifecycleContainer c) {
		if (c.isPaused()) {
			return;
		}
		long start = System.currentTimeMillis();
		for (Health h : c.getProps(Health.class)) {
			if (!h.isAlive()) {
				c.removeObject(h.owner());
			}
		}
		metric(KEY_DELAY, System.currentTimeMillis() - start);
	}

}
