package cn.milai.ib.container.plugin.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.SharableContainerPlugin;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.role.property.Health;
import io.micrometer.core.instrument.Timer;

/**
 * {@link AliveCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckPlugin extends SharableContainerPlugin implements AliveCheckPlugin {

	private static final Timer REFRESH_DELAY = Timer.builder("containerplugin.alivecheck.delay").register(IBMetrics.registry());

	@Override
	public void onRefresh(LifecycleContainer c) {
		if (c.isPaused()) {
			return;
		}
		REFRESH_DELAY.record(() -> {
			for (Health h : c.getProps(Health.class)) {
				if (!h.isAlive()) {
					c.removeObject(h.owner());
				}
			}
		});
	}

}
