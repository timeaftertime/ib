package cn.milai.ib.plugin.actor;

import java.util.function.Consumer;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.config.IBConfig;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.plugin.SharableCrew;
import cn.milai.ib.stage.event.AddActorEvent;
import io.micrometer.core.instrument.Timer;

/**
 * {@link DresserCrew} 默认实现
 * @author milai
 * @date 2022.05.23
 */
public class BaseDresserCrew extends SharableCrew implements DresserCrew {

	private static final Timer REFRESH_DELAY = Timer.builder("crew.dresser.delay")
		.publishPercentiles(0.5, 0.90, 0.95, 0.99)
		.register(IBMetrics.registry());

	@Override
	public Consumer<AddActorEvent> createOnAddActor() {
		return e -> {
			Actor actor = e.actor();
			if (actor.isMakeup()) {
				return;
			}
			REFRESH_DELAY.record(() -> makeUp(actor));
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Actor> T makeUp(T actor) {
		return (T) IBConfig.apply(actor).makeup();
	}
}
