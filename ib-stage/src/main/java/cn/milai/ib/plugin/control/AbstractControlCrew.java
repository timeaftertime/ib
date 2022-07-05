package cn.milai.ib.plugin.control;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import cn.milai.ib.Roster;
import cn.milai.ib.actor.Controllable;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.plugin.BaseExclusiveCrew;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Timer;

/**
 * {@link ControlCrew} 抽象实现
 * @author milai
 * @date 2021.06.04
 */
public abstract class AbstractControlCrew extends BaseExclusiveCrew implements ControlCrew {

	/**
	 * 默认每帧最多执行多少条指令
	 */
	private static final int PER_FRAME_CMD = 8;

	private static final Timer REFRESH_DELAY = Timer.builder("crew.control.delay")
		.publishPercentiles(0.5, 0.90, 0.95, 0.99)
		.register(IBMetrics.registry());
	private static final DistributionSummary DISPATCH_NUM = DistributionSummary.builder("crew.control.dispatchCnt")
		.publishPercentiles(0.5, 0.90, 0.95, 0.99)
		.register(IBMetrics.registry());

	private int perFrameCmd = PER_FRAME_CMD;

	/**
	 * 获取关联 {@link Roster}中按 {@link Order} 排序后的 {@link Controllable} 列表
	 * @return
	 */
	protected List<Controllable> sortedControllables(Stage stage) {
		List<Controllable> all = new ArrayList<>(stage.getAll(Controllable.class));
		AnnotationAwareOrderComparator.sort(all);
		return all;
	}

	@Override
	public Consumer<StageRefreshedEvent> createOnRefreshed() {
		return e -> {
			REFRESH_DELAY.record(() -> {
				List<Controllable> controllables = sortedControllables(e.stage());
				int cmdCnt = 0;
				while (cmdCnt < perFrameCmd) {
					if (!dispatchCmd(controllables)) {
						break;
					}
					cmdCnt++;
				}
				DISPATCH_NUM.record(cmdCnt);
			});
		};
	}

	/**
	 * 向指定 {@link Controllable} 列表分发一条指令
	 * @param controllables
	 * @return 是否实际分发了指令
	 */
	protected abstract boolean dispatchCmd(List<Controllable> controllables);

	public void setPerFrameCmd(int perFrameCmd) { this.perFrameCmd = perFrameCmd; }

}
