package cn.milai.ib.plugin.actor;

import java.util.function.Consumer;

import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.plugin.SharableCrew;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import io.micrometer.core.instrument.Timer;

/**
 * {@link AliveCheckCrew} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckCrew extends SharableCrew implements AliveCheckCrew {

	private static final Timer REFRESH_DELAY = Timer.builder("crew.alivecheck.delay").register(IBMetrics.registry());

	@Override
	public Consumer<StageRefreshedEvent> createOnRefreshed() {
		return e -> {
			if (e.stage().isPaused()) {
				return;
			}
			REFRESH_DELAY.record(() -> {
				for (Nature n : e.stage().getNatures(Health.NAME)) {
					Health h = (Health) n;
					if (!h.isAlive()) {
						e.stage().removeActor(h.owner());
					}
				}
			});
		};
	}

}
